package autocadDrawingChecker.data.excel;

import autocadDrawingChecker.data.core.AbstractRecordConverter;
import autocadDrawingChecker.data.core.AbstractTableParser;
import autocadDrawingChecker.data.core.Record;
import autocadDrawingChecker.logging.Logger;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * The ExcelParser is used to convert Excel files into DataSets usable by the program.
 * Each GradableDataType should have an associated subclass of ExcelParser which accounts for any unique formatting the file may have.
 * This class contains many methods which subclasses may wish to override to customize this class' behavior without having to rewrite
 * an entirely new class:
 * <ul>
 * <li><b>createExtractionHolder</b> will always be overridden to return a different subclass of DataSet</li>
 * <li><b>createExtractor</b> will always be overridden to return a different subclass of RecordExtractor</li>
 * <li><b>locateHeaderRow</b> should be overridden iff the Excel file has headers anywhere except the first row</li>
 * <li><b>isValidRow</b> should be overridden if you need to validate that the row contains specific formatting, such as requiring certain columns always contain a value</li>
 * </ul>
 * 
 * @author Matt Crow
 */
public class ExcelParser extends AbstractTableParser<Sheet, Row> {    
    /**
     * Returns the row containing headers in the given sheet.
     * By default, this method returns the first row of the
     * given sheet. Subclasses should override this method iff
     * their data is expected to have headers in another row.
     * 
     * @param sheet the sheet which this is currently parsing.
     * @return the row of the given sheet that contains headers.
     */
    protected synchronized Row locateHeaderRow(Sheet sheet){
        return sheet.getRow(0);
    }
    
    /**
     * Returns whether or not the given row is valid, and the program should
     * attempt to convert it to a Record. By default, this method checks if
 the given row is empty of otherwise un-parseFirstSheet-able, so if you do override
 this method, it would be wise to include a call to {@code super.isValidRow(...)}
     * in your subclass' method.
     * 
     * @param row the row to validate.
     * @return whether or not the row is valid.
     */
    @Override
    protected boolean isValidRow(Row row){
        return row != null && row.getLastCellNum() != -1 && !isRowEmpty(row);
    }
    
    /**
     * Locates headers within the given row
     * 
     * @param headerRow the first row of the spreadsheet,
     * containing headers.
     * @return a HashMap of header names (converted to upper case) to the index of their column.
     */
    private synchronized HashMap<String, Integer> locateColumns(Row headerRow){
        HashMap<String, Integer> headerToCol = new HashMap<>();
        
        ArrayList<String> headers = new ArrayList<>();
        headerRow.cellIterator().forEachRemaining((Cell c)->{
            headers.add(sanitize(c.toString()).toUpperCase());
        });
        for(int i = 0; i < headers.size(); i++){
            headerToCol.put(headers.get(i), i);
        }
        return headerToCol;
    }
    
    /**
     * 
     * @param row the row to check
     * @return whether or not the given row is empty
     */
    private boolean isRowEmpty(Row row){
        boolean couldBeEmpty = true;
        Iterator<Cell> cellIter = row.cellIterator();
        while(cellIter.hasNext() && couldBeEmpty){
            if(!cellIter.next().getCellType().equals(CellType.BLANK)){
                couldBeEmpty = false;
            }
        }
        return couldBeEmpty;
    }
    
    /**
     * Converts the current row this is parsing to a string.
     * This method is very helpful for debugging.
     * 
     * @return the current row as a string, formatted to look like an array.
     */
    private String rowToString(Row row){
        StringBuilder b = new StringBuilder();
        b.append("[");
        Iterator<Cell> iter = row.cellIterator();
        while(iter.hasNext()){
            b.append(iter.next().toString());
            if(iter.hasNext()){
                b.append(", ");
            }
        }
        b.append("]");
        return b.toString();
    }
    
    @Override
    protected Map<String, Integer> getHeadersFrom(Sheet sheet) {
        Row headerRow = locateHeaderRow(sheet);
        return this.locateColumns(headerRow);
    }
    
    @Override
    protected void forEachRowIn(Sheet sheet, Consumer<Row> doThis) {
        Row headerRow = this.locateHeaderRow(sheet);        
        int numRows = sheet.getLastRowNum() + 1; // need the + 1, otherwise it sometimes doesn't get the last row
        /*
        Note that numRows will be greater than or
        equal to the last row with data, but not
        every row is guaranteed to contain data.
        
        From the Apache POI javadoc:
        """
        Gets the last row on the sheet 
        Note: rows which had content before and were set to empty later might still be counted as rows by Excel and Apache POI, 
        so the result of this method will include such rows and thus the returned value might be higher than expected!
        """
        */
        
        Row currRow = null;
        //               skip headers
        for(int rowNum = headerRow.getRowNum() + 1; rowNum < numRows; rowNum++){
            currRow = sheet.getRow(rowNum);
            if(isValidRow(currRow)){// && recExtr.canExtractRow(currRow)){
                try {
                    doThis.accept(currRow);
                } catch(Exception ex){
                    Logger.logError(String.format("Error while parsing row: %s", rowToString(currRow)));
                    Logger.logError(ex);
                }
            }
        }
    }
    
    @Override
    protected List<Sheet> extractSheets(String path) throws IOException {
        List<Sheet> sheets = new LinkedList<>();
        try (Workbook workbook = WorkbookFactory.create(new FileInputStream(path))) {
            workbook.sheetIterator().forEachRemaining(sheets::add);
        } catch(Exception ex){
            Logger.logError(ex);
        }
        return sheets;
    }

    @Override
    protected String getSheetName(Sheet sheet) {
        return sheet.getSheetName();
    }
    
    @Override
    protected boolean doesRowHaveCell(Row currRow, int idx) {
        Cell c = currRow.getCell(idx);
        return c != null && c.getCellType() != CellType.BLANK;
        // getCell doesn't throw an exception if it doesn't have a cell for the given column: it just returns null
    }
    
    @Override
    protected Object doGetCell(Row currRow, int idx){
        Object ret = null;
        Cell c = currRow.getCell(idx);
        switch(c.getCellType()){
            case BOOLEAN:
                ret = c.getBooleanCellValue();
                break;
            case NUMERIC:
                ret = (Double)c.getNumericCellValue();
                break;
            case STRING:
                ret = c.getStringCellValue();
                break;
            default:
                Logger.logError(String.format("ExcelRecordExtractor encountered cell with type %s", c.getCellType().name()));
                //ret = c.toString();
                break;
        }
        return ret;
    }
}
