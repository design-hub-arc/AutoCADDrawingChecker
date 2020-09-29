package autocadDrawingChecker.data;

import autocadDrawingChecker.data.extractors.AutoCADAttribute;
import autocadDrawingChecker.data.elements.AutoCADExport;
import autocadDrawingChecker.data.elements.AutoCADElement;
import autocadDrawingChecker.data.elements.Record;
import autocadDrawingChecker.data.extractors.AbstractAutoCADElementExtractor;
import autocadDrawingChecker.data.extractors.RecordExtractor;
import autocadDrawingChecker.logging.Logger;
import autocadDrawingChecker.start.Application;
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
    private final HashMap<AutoCADAttribute, Integer> headerToCol;
    private final HashMap<String, AbstractAutoCADElementExtractor<?>> extractors;
    private Row currRow;
    
    /**
     * Use this instead of the static parse method if you want to use a custom set of extractors
     * @param fileToParse a path to the Excel file this should parse.
     * @param extractors the extractors to use for converting rows to autoCAD elements
     */
    public AutoCADExcelParser(String fileToParse, AbstractAutoCADElementExtractor<?>... extractors){
        fileName = fileToParse;
        headerToCol = new HashMap<>();
        this.extractors = new HashMap<>();
        for(AbstractAutoCADElementExtractor<?> extractor : extractors){
            this.extractors.put(extractor.getName(), extractor);
        }
        currRow = null;
    }
    
    /**
     * Locates required headers within the given
     * row, and populates headerToCol appropriately.
     * The header row is expected to contain each header
     * detailed in AutoCADAttribute.
     * 
     * @see AutoCADAttribute
     * 
     * @param headerRow the first row of the spreadsheet,
     * containing headers.
     */
    private void locateColumns(Row headerRow){
        headerToCol.clear();
        ArrayList<String> headers = new ArrayList<>();
        headerRow.cellIterator().forEachRemaining((Cell c)->{
            headers.add(c.getStringCellValue().toUpperCase());
        });
        for(AutoCADAttribute reqAttr : AutoCADAttribute.values()){
            headerToCol.put(reqAttr, headers.indexOf(reqAttr.getHeader().toUpperCase()));
            // may be -1. Not sure how we should handle missing headers
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
    private String getCellString(AutoCADAttribute col){
        int colIdx = headerToCol.get(col);
        if(colIdx == -1){
            throw new RuntimeException(String.format("Missing column: %s", col.getHeader()));
        }
        return currRow.getCell(colIdx).toString(); // returns the contents of the cell as text 
    }
    
    private double getCellDouble(AutoCADAttribute col){
        String data = getCellString(col);
        double ret = 0.0;
        try {
            ret = Double.parseDouble(data);
        } catch(NumberFormatException ex){
            Logger.logError(String.format("Cannot convert \"%s\" to a double", data));
            throw ex;
        } 
        return ret;
    }
    
    private int getCellInt(AutoCADAttribute col){
        return (int)getCellDouble(col);
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
        
        
        
        LinkedList<Record> recordList = new LinkedList<>();
        RecordExtractor newExtr = new RecordExtractor();
        HashMap<String, Integer> strToCol = new HashMap<>();
        this.headerToCol.forEach((attr, col)->strToCol.put(attr.getHeader(), col));
        
        
        
        
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
            
            
            
            rec = newExtr.extract(strToCol, currRow);
            if(rec != null){
                recordList.add(rec);
            }
            
            
            try {
                currName = getCellString(AutoCADAttribute.NAME);
                if(extractors.containsKey(currName.toUpperCase())){ // extractor names are all uppercase
                    data = extractors.get(currName.toUpperCase()).extract(headerToCol, currRow);
                } else {
                    data = new AutoCADElement(); // Doesn't have an extractor for it
                    Logger.logError(String.format("This AutoCADExcelParser has no extractor for name \"%s\"", currName));
                }
            } catch(Exception ex){
                Logger.logError(String.format("Error while parsing row: %s", currRowToString()));
                Logger.logError(ex);
            }
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
            }
        }
        Logger.log("In AutoCADExcelParser.parse...\n" + containedTherein.toString());
        //Logger.log("Records:");
        //recordList.forEach(Logger::log);
        
        workbook.close();
        return containedTherein;
    }
    
    /**
     * Reads the Excel file with the
     * given complete file path, and
     * returns its contents as an AutoCADExport.
     * 
     * This automatically uses the extractors set in the Application class.
     * 
     * @param fileName the complete path to an Excel file.
     * @return the contents of the first sheet of the given Excel file , as 
     * an AutoCADExport.
     * @throws IOException if the fileName given does not point to an Excel file
     */
    public static AutoCADExport parse(String fileName) throws IOException{
        return new AutoCADExcelParser(
            fileName, 
            Application.getInstance().getExtractors()
        ).parse();
    }
}
