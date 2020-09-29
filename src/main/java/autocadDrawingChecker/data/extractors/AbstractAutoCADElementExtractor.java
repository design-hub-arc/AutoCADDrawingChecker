package autocadDrawingChecker.data.extractors;

import autocadDrawingChecker.data.elements.AutoCADElement;
import autocadDrawingChecker.logging.Logger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.apache.poi.ss.usermodel.Row;

/**
 * This class is used to convert spreadsheet data
 * into an AutoCADElement. These are used by AutoCADExcel parser to identify and
 * extract elements from records in an Excel file. 
 * 
 * @author Matt Crow
 * @param <T> the type of AutoCADElement this extracts
 */
public abstract class AbstractAutoCADElementExtractor<T extends AutoCADElement> extends RecordExtractor<T> {
    private final String name;
    private final List<AutoCADAttribute> requiredColumns;
    private Row currentRow;
    
    
    /**
     * 
     * @param name if this name shows up in the "name" column of an AutoCAD export,
     * this will attempt to extract that line. <b>Note that this name is converted to upper case</b>
     * @param requiredColumns the columns this requires.
     */
    public AbstractAutoCADElementExtractor(String name, AutoCADAttribute... requiredColumns){
        this.name = name.toUpperCase();
        this.requiredColumns = Arrays.asList(requiredColumns);
    }
    
    /**
     * 
     * @return a value in the name column in an AutoCAD export.
     * When the name of a record in said export matches this method,
     * that means this should extract from that record.
     */
    public final String getName(){
        return name;
    }
    
    /**
     * Gets the string value of the cell in the current
     * row, in the given column.
     * 
     * @param col the column to get the cell value for
     * @return the string value of the cell.
     * @throws RuntimeException if the given column is not found
     */
    protected String getCellString(AutoCADAttribute col){
        /*
        int colIdx = currentCols.get(col);
        if(colIdx == -1){
            throw new RuntimeException(String.format("Missing column: %s", col.getHeader()));
        }
        return currentRow.getCell(colIdx).toString(); // returns the contents of the cell as text 
        */
        return super.getCellString(col.getHeader());
    }
    
    /**
     * Gets the double value of the cell in the current
     * row, in the given column.
     * 
     * @param col the column to get the cell value for
     * @return the double value of the cell.
     * @throws RuntimeException if the given column is not found or the cell is not numberical
     */
    protected double getCellDouble(AutoCADAttribute col){
        String data = getCellString(col.getHeader());
        double ret = 0.0;
        try {
            ret = Double.parseDouble(data);
        } catch(NumberFormatException ex){
            Logger.logError(String.format("Cannot convert \"%s\" to a double", data));
            throw ex;
        } 
        return ret;
    }
    
    /**
     * Gets the integer value of the cell in the current
     * row, in the given column.
     * 
     * @param col the column to get the cell value for
     * @return the int value of the cell.
     * @throws RuntimeException if the given column is not found, or the cell is non-numberical
     */
    protected int getCellInt(AutoCADAttribute col){
        return (int)getCellDouble(col);
    }
    
    
    /**
     * Checks to see if the given columns contain all the columns this needs.
     * @param columns the columns to check against
     * @return whether or not the given set of columns contains all the columns this extractor needs
     */
    public final boolean hasRequiredColumns(HashMap<AutoCADAttribute, Integer> columns){
        return requiredColumns.stream().allMatch((AutoCADAttribute requiredColumn)->{
            return columns.containsKey(requiredColumn) && columns.get(requiredColumn) != -1;
        });
    }
    
    /**
     * Extracts data from the given row, and converts it to an AutoCAD element.
     * @param columns the mapping of columns to the index of the column
     * in the given row.
     * @param currentRow the row to extract data from
     * @return the extracted AutoCADElement.
     */
    /*
    public synchronized final T extract(HashMap<AutoCADAttribute, Integer> columns, Row currentRow){
        // temporarily set the columns and row. Note this method is synchronized to prevent multithreading issues        
        this.currentCols = columns;
        this.currentRow = currentRow;
        T ret = doExtract();
        this.currentCols = null;
        this.currentRow = null;
        return ret;
    }*/
    
    /**
     * Performs extraction on the current row.
     * Use the following methods to extract
     * data from the row:
     * <ul>
     * <li>getCellString(colName)</li>
     * <li>getCellDouble(colName)</li>
     * <li>getCellInt(colName)</li>
     * </ul>
     * @see AbstractAutoCADElementExtractor#getCellString
     * @see AbstractAutoCADElementExtractor#getCellDouble
     * @see AbstractAutoCADElementExtractor#getCellInt
     * @return the extracted element
     */
    //public abstract T doExtract();
}
