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
                (int)Double.parseDouble(getCell(AutoCADAttribute.ANGLE)),
                new double[]{
                    Double.parseDouble(getCell(AutoCADAttribute.START_X)),
                    Double.parseDouble(getCell(AutoCADAttribute.START_Y)),
                    Double.parseDouble(getCell(AutoCADAttribute.START_Z))
                },
                new double[]{
                    Double.parseDouble(getCell(AutoCADAttribute.END_X)),
                    Double.parseDouble(getCell(AutoCADAttribute.END_Y)),
                    Double.parseDouble(getCell(AutoCADAttribute.END_Z))
                },
                new double[]{
                    Double.parseDouble(getCell(AutoCADAttribute.DELTA_X)),
                    Double.parseDouble(getCell(AutoCADAttribute.DELTA_Y)),
                    Double.parseDouble(getCell(AutoCADAttribute.DELTA_Z))
                },
                Double.parseDouble(getCell(AutoCADAttribute.LENGTH)),
                Double.parseDouble(getCell(AutoCADAttribute.THICKNESS))
            );
        } catch (Exception ex){
            Logger.logError(String.format("Error while parsing line %s", currRow.toString()));
            Logger.logError(ex);
            
        }
        return ret;
    }
    
    private AutoCADPolyline extractPolyline(){
        return new AutoCADPolyline(
            Double.parseDouble(getCell(AutoCADAttribute.LENGTH)),
            Double.parseDouble(getCell(AutoCADAttribute.THICKNESS)),
            Double.parseDouble(getCell(AutoCADAttribute.AREA)),
            (int)Double.parseDouble(getCell(AutoCADAttribute.CLOSED)),
            Double.parseDouble(getCell(AutoCADAttribute.GLOBAL_WIDTH))
        );
    }
    
    private AutoCADText extractText(){
        return new AutoCADText(
            getCell(AutoCADAttribute.CONTENTS),
            getCell(AutoCADAttribute.CONTENTS_RTF),
            new double[]{
                Double.parseDouble(getCell(AutoCADAttribute.POSITION_X)),
                Double.parseDouble(getCell(AutoCADAttribute.POSITION_Y)),
                Double.parseDouble(getCell(AutoCADAttribute.POSITION_Z))
            },
            (int)Double.parseDouble(getCell(AutoCADAttribute.ANGLE)),
            (int)Double.parseDouble(getCell(AutoCADAttribute.SHOW_BORDERS)),
            Double.parseDouble(getCell(AutoCADAttribute.WIDTH))
        );
    }
    
    private AutoCADDimension extractDim(){
        return new AutoCADDimension(
            getCell(AutoCADAttribute.DIM_STYLE),
            (int)Double.parseDouble(getCell(AutoCADAttribute.DYNAMIC_DIMENSION)),
            getCell(AutoCADAttribute.TEXT_DEFINED_SIZE)
        );
    }
    
    /**
     * Interprets the current row as
 an AutoCADElement, and returns it.
     * 
     * @return the current row, as an AutoCADElement. 
     */
    private AutoCADElement extractRow(){
        return new AutoCADElement();
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
     * @throws RuntimeException if the given column is not found
     */
    private String getCell(AutoCADAttribute col){
        int colIdx = headerToCol.get(col);
        if(colIdx == -1){
            throw new RuntimeException(String.format("Missing column: %s", col.getHeader()));
        }
        return currRow.getCell(colIdx).toString(); // returns the contents of the cell as text 
    }
    
    public final AutoCADExport parse() throws IOException {
        InputStream in = new FileInputStream(fileName);
        //                                                new Excel format       old Excel format
        Workbook workbook = (fileName.endsWith("xlsx")) ? new XSSFWorkbook(in) : new HSSFWorkbook(in);
        Sheet sheet = workbook.getSheetAt(0);
        AutoCADExport containedTherein = new AutoCADExport(fileName);
        locateColumns(sheet.getRow(0));
        int max = sheet.getLastRowNum();
        AutoCADElement data = null;
        //               skip headers
        for(int rowNum = 1; rowNum < max; rowNum++){
            currRow = sheet.getRow(rowNum);
            try {
                if(getCell(AutoCADAttribute.NAME).equals("Line")){
                    data = extractLine();
                } else if(getCell(AutoCADAttribute.NAME).equals("Polyline")){
                    data = extractPolyline();
                } else if(getCell(AutoCADAttribute.NAME).equals("MText")){
                    data = extractText();
                } else if(getCell(AutoCADAttribute.NAME).equals("Rotated Dimension")){
                    data = extractDim();
                }else {
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
                // sets these attributes for every element
                // which do we need?
                //data.setCount((int)(Double.parseDouble(getCell(AutoCADAttribute.COUNT)))); // for some reason getCell returns 1.0 instead of 1
                //data.setName(getCell(AutoCADAttribute.NAME));
                //data.setColor(getCell(AutoCADAttribute.COLOR));
                data.setLayer(getCell(AutoCADAttribute.LAYER));
                //data.setLineType(getCell(AutoCADAttribute.LINE_TYPE));
                //data.setLineTypeScale(Double.parseDouble(getCell(AutoCADAttribute.LINE_TYPE_SCALE)));
                //data.setLineWeight(getCell(AutoCADAttribute.LINE_WEIGTH));
                //data.setPlot(getCell(AutoCADAttribute.PLOT_STYLE));
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
