package autocadDrawingChecker.autocadData;

import autocadDrawingChecker.logging.Logger;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
                getCellInt(AutoCADAttribute.ANGLE),
                new double[]{
                    getCellDouble(AutoCADAttribute.START_X),
                    getCellDouble(AutoCADAttribute.START_Y),
                    getCellDouble(AutoCADAttribute.START_Z)
                },
                new double[]{
                    getCellDouble(AutoCADAttribute.END_X),
                    getCellDouble(AutoCADAttribute.END_Y),
                    getCellDouble(AutoCADAttribute.END_Z)
                },
                new double[]{
                    getCellDouble(AutoCADAttribute.DELTA_X),
                    getCellDouble(AutoCADAttribute.DELTA_Y),
                    getCellDouble(AutoCADAttribute.DELTA_Z)
                },
                getCellDouble(AutoCADAttribute.LENGTH),
                getCellDouble(AutoCADAttribute.THICKNESS)
            );
        } catch (Exception ex){
            Logger.logError(String.format("Error while parsing line %s", currRow.toString()));
            Logger.logError(ex);
            
        }
        return ret;
    }
    
    private AutoCADPolyline extractPolyline(){
        return new AutoCADPolyline(
            getCellDouble(AutoCADAttribute.LENGTH),
            getCellDouble(AutoCADAttribute.THICKNESS),
            getCellDouble(AutoCADAttribute.AREA),
            getCellInt(AutoCADAttribute.CLOSED),
            getCellDouble(AutoCADAttribute.GLOBAL_WIDTH)
        );
    }
    
    private AutoCADText extractText(){
        return new AutoCADText(
            getCellString(AutoCADAttribute.CONTENTS),
            getCellString(AutoCADAttribute.CONTENTS_RTF),
            new double[]{
                getCellDouble(AutoCADAttribute.POSITION_X),
                getCellDouble(AutoCADAttribute.POSITION_Y),
                getCellDouble(AutoCADAttribute.POSITION_Z)
            },
            getCellInt(AutoCADAttribute.ROTATION),
            getCellInt(AutoCADAttribute.SHOW_BORDERS),
            getCellDouble(AutoCADAttribute.WIDTH)
        );
    }
    
    private AutoCADDimension extractDim(){
        return new AutoCADDimension(
            getCellString(AutoCADAttribute.DIM_STYLE),
            getCellInt(AutoCADAttribute.DYNAMIC_DIMENSION),
            getCellString(AutoCADAttribute.TEXT_DEFINED_SIZE)
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
     * 
     * @param sheet
     * @return the number of rows containing data in the given sheet 
     */
    private int getRowCount(Sheet sheet){
        int ret = 0;
        Iterator<Row> iter = sheet.rowIterator();
        while(iter.hasNext()){
            if(iter.next().getLastCellNum() == -1){
                break;
            } else {
                ret++;
            }
        }
        return ret;
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
        int max = getRowCount(sheet);
        AutoCADElement data = null;
        //               skip headers
        for(int rowNum = 1; rowNum < max; rowNum++){
            currRow = sheet.getRow(rowNum);
            try {
                if(getCellString(AutoCADAttribute.NAME).equals("Line")){
                    data = extractLine();
                } else if(getCellString(AutoCADAttribute.NAME).equals("Polyline")){
                    data = extractPolyline();
                } else if(getCellString(AutoCADAttribute.NAME).equals("MText")){
                    data = extractText();
                } else if(getCellString(AutoCADAttribute.NAME).equals("Rotated Dimension")){
                    data = extractDim();
                }else {
                    data = extractRow();
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
                    data.setCount(getCellInt(AutoCADAttribute.COUNT)); 
                    data.setName(getCellString(AutoCADAttribute.NAME));
                    data.setColor(getCellString(AutoCADAttribute.COLOR));
                    data.setLineType(getCellString(AutoCADAttribute.LINE_TYPE));
                    data.setLineTypeScale(getCellDouble(AutoCADAttribute.LINE_TYPE_SCALE));
                    data.setLineWeight(getCellString(AutoCADAttribute.LINE_WEIGTH));
                    data.setPlot(getCellString(AutoCADAttribute.PLOT_STYLE));
                } catch(Exception ex){
                    Logger.logError(String.format("Error while parsing row: %s", currRowToString()));
                    Logger.logError(ex);
                }
                containedTherein.add(data);
                data = null;
            }
        }
        //Logger.log("In AutoCADExcelParser.parse...\n" + containedTherein.toString());
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
