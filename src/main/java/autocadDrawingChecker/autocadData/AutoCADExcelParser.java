package autocadDrawingChecker.autocadData;

import autocadDrawingChecker.logging.Logger;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
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
    private Row currRow;
    
    public AutoCADExcelParser(String fileToParse){
        fileName = fileToParse;
        headerToCol = new HashMap<>();
        currRow = null;
    }
    
    /**
     * Interprets the current row of the
     * spreadsheet as an AutoCADLine, and
     * returns it.
     * 
     * @return the current row, as an AutoCADLine 
     */
    private AutoCADLine extractLine(){
        AutoCADLine ret = null;
        try {
            ret = new AutoCADLine(
                getCell(AutoCADAttribute.LAYER),
                new double[]{
                    Double.parseDouble(getCell(AutoCADAttribute.START_X)),
                    Double.parseDouble(getCell(AutoCADAttribute.START_Y)),
                    Double.parseDouble(getCell(AutoCADAttribute.START_Z))
                },
                new double[]{
                    Double.parseDouble(getCell(AutoCADAttribute.END_X)),
                    Double.parseDouble(getCell(AutoCADAttribute.END_Y)),
                    Double.parseDouble(getCell(AutoCADAttribute.END_Z))
                }
            );
        } catch (Exception ex){
            Logger.logError(String.format("Error while parsing line %s", currRow.toString()));
            Logger.logError(ex);
            
        }
        return ret;
    }
    
    /**
     * Interprets the current row as
     * an AutoCADRow, and returns it.
     * 
     * @return the current row, as an AutoCADRow. 
     */
    private AutoCADRow extractRow(){
        return new AutoCADRow(
            getCell(AutoCADAttribute.LAYER)
        );
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
    
    /**
     * Gets the string value of the cell in the current
     * row, in the given column.
     * 
     * @param col the column to get the cell value for
     * @return the string value of the cell.
     */
    private String getCell(AutoCADAttribute col){
        return currRow.getCell(headerToCol.get(col)).toString(); // returns the contents of the cell as text 
    }
    
    public final AutoCADExport parse() throws IOException {
        InputStream in = new FileInputStream(fileName);
        //                                                new Excel format       old Excel format
        Workbook workbook = (fileName.endsWith("xlsx")) ? new XSSFWorkbook(in) : new HSSFWorkbook(in);
        Sheet sheet = workbook.getSheetAt(0);
        AutoCADExport containedTherein = new AutoCADExport(fileName);
        locateColumns(sheet.getRow(0));
        int max = sheet.getLastRowNum();
        AutoCADRow data = null;
        //               skip headers
        for(int rowNum = 1; rowNum < max; rowNum++){
            currRow = sheet.getRow(rowNum);
            try {
                if(getCell(AutoCADAttribute.NAME).equals("Line")){
                    data = extractLine();
                } else {
                    data = extractRow();
                }
            } catch(NullPointerException ex){
                // there is no way to find the last row with data as far as I know
                /*
                From the documentation:
                "Gets the last row on the sheet 
                Note: rows which had content before 
                and were set to empty later 
                might still be counted as rows 
                by Excel and Apache POI, 
                so the result of this method 
                will include such rows and 
                thus the returned value 
                might be higher than expected!"
                */
                //Logger.logError(ex);
            }
            if(data != null){
                containedTherein.add(data);
                data = null;
            }
        }
        System.out.println(containedTherein);
        workbook.close();
        return containedTherein;
    }
    
    /**
     * Reads the Excel file with the
     * given complete file path, and
     * returns its contents as an AutoCADExport.
     * 
     * This is a shorthand for
     * <pre><code>
     * new AutoCADExcelParser(fileName).parse();
     * </code></pre>
     * 
     * @param fileName the complete path to an Excel file.
     * @return the contents of the first sheet of the given Excel file , as 
     * an AutoCADExport.
     * @throws IOException if the fileName given does not point to an Excel file
     */
    public static AutoCADExport parse(String fileName) throws IOException{
        return new AutoCADExcelParser(fileName).parse();
    }
}
