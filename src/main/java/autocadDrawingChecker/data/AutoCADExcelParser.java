package autocadDrawingChecker.data;

import autocadDrawingChecker.data.elements.AutoCADExport;
import autocadDrawingChecker.data.elements.AutoCADElement;
import autocadDrawingChecker.data.elements.Record;
import autocadDrawingChecker.data.extractors.RecordExtractor;
import autocadDrawingChecker.logging.Logger;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * The AutoCADExcelParser is used to 
 * read an Excel spreadsheet,
 * extracting the AutoCAD date within as an
 * AutoCADExport object.
 * 
 * @author Matt Crow
 */
public class AutoCADExcelParser {
    private final String fileName;
    private final HashMap<String, Integer> headerToCol;
    private Row currRow;
    
    /**
     * Use this instead of the static parse method if you want to use a custom set of extractors
     * @param fileToParse a path to the Excel file this should parse.
     */
    public AutoCADExcelParser(String fileToParse){
        fileName = fileToParse;
        headerToCol = new HashMap<>();
        currRow = null;
    }
    
    /**
     * Locates headers within the given
     * row, and populates headerToCol appropriately.
     * 
     * @param headerRow the first row of the spreadsheet,
     * containing headers.
     */
    private synchronized void locateColumns(Row headerRow){
        headerToCol.clear();
        ArrayList<String> headers = new ArrayList<>();
        headerRow.cellIterator().forEachRemaining((Cell c)->{
            headers.add(c.toString().toUpperCase());
        });
        for(int i = 0; i < headers.size(); i++){
            headerToCol.put(headers.get(i), i);
        }
    }
    
    private boolean isRowEmpty(Row row){
        boolean empty = false;
        Iterator<Cell> cellIter = row.cellIterator();
        while(cellIter.hasNext() && !empty){
            if(cellIter.next().toString().equals("")){
                empty = true;
            }
        }
        return empty;
    }
    private boolean isValidRow(Row row){
        return row != null && row.getLastCellNum() != -1 && !isRowEmpty(row);
    }
    
    /**
     * Gets the string value of the cell in the current
     * row, in the given column.
     * 
     * @param col the column to get the cell value for
     * @return the string value of the cell.
     * @throws RuntimeException if the given column is not found
     */
    private String getCellString(String col){
        int colIdx = headerToCol.get(col.toUpperCase());
        if(colIdx == -1){
            throw new RuntimeException(String.format("Missing column: %s", col));
        }
        return currRow.getCell(colIdx).toString(); // returns the contents of the cell as text 
    }
    
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
    
    public final AutoCADExport parse() throws IOException {
        InputStream in = new FileInputStream(fileName);
        //                                                new Excel format       old Excel format
        Workbook workbook = (fileName.endsWith("xlsx")) ? new XSSFWorkbook(in) : new HSSFWorkbook(in);
        Sheet sheet = workbook.getSheetAt(0);
        AutoCADExport containedTherein = new AutoCADExport(fileName);
        locateColumns(sheet.getRow(0));
        
        
        RecordExtractor recExtr = new RecordExtractor();
        LinkedList<Record> recordList = new LinkedList<>();
        
        
        
        
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
        
        String currName = null;
        AutoCADElement data = null;
        Record rec = null;
        
        //               skip headers
        for(int rowNum = 1; rowNum < numRows; rowNum++){
            currRow = sheet.getRow(rowNum);
            if(!isValidRow(currRow)){
                continue;
            }    
            
            try {
                rec = recExtr.extract(headerToCol, currRow);
                recordList.add(rec);
            } catch(Exception ex){
                Logger.logError(String.format("Error while parsing row: %s", currRowToString()));
                Logger.logError(ex);
            }
            /*
            if(data != null){
                // sets these attributes for every element
                // which do we need?
                try {
                    data.setLayer(getCellString(AutoCADAttribute.LAYER));
                    data.setName(getCellString(AutoCADAttribute.NAME));
                } catch(Exception ex){
                    Logger.logError(String.format("Error while parsing row: %s", currRowToString()));
                    Logger.logError(ex);
                }
                containedTherein.add(data);
                data = null;
            }*/
        }
        //Logger.log("In AutoCADExcelParser.parse...\n" + containedTherein.toString());
        Logger.log("Record List:\n");
        recordList.forEach(Logger::log);
        
        workbook.close();
        return containedTherein;
    }
    
    /**
     * Reads the Excel file with the
     * given complete file path, and
     * returns its contents as an AutoCADExport.
     * 
     * @param fileName the complete path to an Excel file.
     * @return the contents of the first sheet of the given Excel file , as 
     * an AutoCADExport.
     * @throws IOException if the fileName given does not point to an Excel file
     */
    public static AutoCADExport parse(String fileName) throws IOException{
        return new AutoCADExcelParser(
            fileName
        ).parse();
    }
}
