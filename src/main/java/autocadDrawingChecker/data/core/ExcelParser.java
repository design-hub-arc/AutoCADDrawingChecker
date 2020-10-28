package autocadDrawingChecker.data.core;

import autocadDrawingChecker.logging.Logger;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

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
public class ExcelParser {
    private final String fileName;
    private Row currRow;
    
    /**
     * Creates a new parser, ready to convert the given file.
     * Note that you must still invoke the parseFirstSheet method to read
 and convert the file to a DataSet.
     * 
     * @param fileName the complete path to the file this should parseFirstSheet. 
     */
    public ExcelParser(String fileName){
        this.fileName = fileName;
        this.currRow = null;
    }
    
    /**
     * 
     * @return the complete path to the file this should parseFirstSheet.
     */
    protected final String getFileName(){
        return fileName;
    }
    
    /**
     * Creates the DataSet which will hold the contents
     * of the file this is parsing. Subclasses will always
     * override this method to return a DataSet for their
     * own record type.
     * 
     * @return the DataSet this will store the parsed Excel file contents in 
     */
    protected DataSet createExtractionHolder(){
        return new DataSet(this.fileName);
    }
    
    /**
     * Creates the RecordExtractor which will be used to convert rows in the Excel
     * spreadsheet to Records in the output DataSet. Subclasses should always override
     * this method to return an extractor for their own Record type.
     * 
     * I may move RecordExtractor behavior back into this class at a later time, 
     * but I'll leave them separate for now.
     * 
     * @param columns a mapping of header name to column index, found from the 
     * header row of the sheet this is parsing.
     * 
     * @return a RecordExtractor which will read rows from the Excel file, and
     * output Records from it.
     */
    protected RecordExtractor createExtractor(HashMap<String, Integer> columns){
        return new RecordExtractor(columns);
    }
    
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
    protected boolean isValidRow(Row row){
        return row != null && row.getLastCellNum() != -1 && !isRowEmpty(row);
    }
    
    private String sanitize(String s){
        return s.trim(); // may not want to uppercase
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
    private String currRowToString(){
        StringBuilder b = new StringBuilder();
        b.append("[");
        Iterator<Cell> iter = currRow.cellIterator();
        while(iter.hasNext()){
            b.append(iter.next().toString());
            if(iter.hasNext()){
                b.append(", ");
            }
        }
        b.append("]");
        return b.toString();
    }
    
    private DataSet parseSheet(Sheet sheet){
        DataSet containedTherein = createExtractionHolder();
        
        Row headerRow = locateHeaderRow(sheet);
        
        
        RecordExtractor recExtr = createExtractor(locateColumns(headerRow));
        
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
        
        Record rec = null;
        
        //               skip headers
        for(int rowNum = headerRow.getRowNum() + 1; rowNum < numRows; rowNum++){
            currRow = sheet.getRow(rowNum);
            if(isValidRow(currRow) && recExtr.canExtractRow(currRow)){
                try {
                    rec = recExtr.extract(currRow);
                    containedTherein.add(rec);
                } catch(Exception ex){
                    Logger.logError(String.format("Error while parsing row: %s", currRowToString()));
                    Logger.logError(ex);
                }
            }
        }
        
        
        
        return containedTherein;
    }
    
    /**
     * This method ties all the methods of this class together. Attempts to read and parseFirstSheet the Excel file
 located at the file path provided to the constructor, and returns its contents as extracted by the
 RecordExtractor. Subclasses should override other methods to customize the behavior of how this parses
 the data provided.
     * 
     * @return the contents of the Excel file passed to the constructor, converted to a DataSet so the program
     * can more easily use it.
     * 
     * @throws IOException if bad things happen when reading the Excel file. 
     */
    public final DataSet parseFirstSheet() throws IOException {
        InputStream in = new FileInputStream(fileName);
        //                                                new Excel format       old Excel format
        Workbook workbook = (fileName.endsWith("xlsx")) ? new XSSFWorkbook(in) : new HSSFWorkbook(in);
        Sheet sheet = workbook.getSheetAt(0);
        
        DataSet containedTherein = parseSheet(sheet);
        
        workbook.close();
        
        return containedTherein;
    }
    
    public final List<DataSet> parseAllSheets() throws IOException {
        LinkedList<DataSet> allDataSets = new LinkedList<>();
        
        InputStream in = new FileInputStream(fileName);
        //                                                new Excel format       old Excel format
        Workbook workbook = (fileName.endsWith("xlsx")) ? new XSSFWorkbook(in) : new HSSFWorkbook(in);
        workbook.sheetIterator().forEachRemaining((Sheet sheet)->{
            try {
                DataSet set = parseSheet(sheet);
                if(set != null){
                    allDataSets.add(set);
                }
            } catch(Exception ex){
                Logger.logError(ex);
            }
        });
        
        return allDataSets;
    }
}
