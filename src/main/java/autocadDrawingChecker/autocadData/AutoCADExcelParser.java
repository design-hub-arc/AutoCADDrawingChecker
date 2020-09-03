package autocadDrawingChecker.autocadData;

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
 * The AutoCADExcelParser is a static
 * class used to read an Excel spreadsheet,
 * extracting the AutoCAD date within as an
 * AutoCADExport object.
 * 
 * @author Matt Crow
 */
public class AutoCADExcelParser {
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
        InputStream in = new FileInputStream(fileName);
        //                                                new Excel format       old Excel format
        Workbook workbook = (fileName.endsWith("xlsx")) ? new XSSFWorkbook(in) : new HSSFWorkbook(in);
        Sheet sheet = workbook.getSheetAt(0);
        AutoCADExport containedTherein = new AutoCADExport(fileName);
        HashMap<AutoCADAttribute, Integer> headerToCol = locateColumns(sheet.getRow(0));
        int max = sheet.getLastRowNum();
        Row currRow = null;
        //               skip headers
        for(int rowNum = 1; rowNum < max; rowNum++){
            currRow = sheet.getRow(rowNum);
            try {
                containedTherein.add(new AutoCADRow(
                    currRow.getCell(headerToCol.get(AutoCADAttribute.LAYER)).getStringCellValue()
                ));
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
        }
        
        return containedTherein;
    }
    
    private static HashMap<AutoCADAttribute, Integer> locateColumns(Row headerRow){
        HashMap<AutoCADAttribute, Integer> attributes = new HashMap<>();
        ArrayList<String> headers = new ArrayList<>();
        headerRow.cellIterator().forEachRemaining((Cell c)->{
            headers.add(c.getStringCellValue().toUpperCase());
        });
        for(AutoCADAttribute reqAttr : AutoCADAttribute.values()){
            attributes.put(reqAttr, headers.indexOf(reqAttr.getHeader().toUpperCase()));
            // may be -1. Not sure how we should handle missing headers
        }
        return attributes;
    }    
}
