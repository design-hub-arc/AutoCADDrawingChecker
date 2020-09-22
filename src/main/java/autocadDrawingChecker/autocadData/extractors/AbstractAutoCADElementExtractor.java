package autocadDrawingChecker.autocadData.extractors;

import autocadDrawingChecker.autocadData.AutoCADAttribute;
import autocadDrawingChecker.autocadData.elements.AutoCADElement;
import autocadDrawingChecker.logging.Logger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.apache.poi.ss.usermodel.Row;

/**
 * This class is used to convert spreadsheet data
 * into an AutoCADElement. Ideally, this should allow
 * for more extensibility, as it moves the extraction
 * specifics out of the AutoCADExcelParse.
 * 
 * @author Matt Crow
 * @param <T> the type of AutoCADElement this extracts
 */
public abstract class AbstractAutoCADElementExtractor<T extends AutoCADElement> {
    private final String name;
    private final List<AutoCADAttribute> requiredColumns;
    private HashMap<AutoCADAttribute, Integer> currentCols;
    private Row currentRow;
    
    
    /**
     * 
     * @param name if this name shows up in the "name" column of an AutoCAD export,
     * this will attempt to extract that line.
     * @param requiredColumns the columns this requires. Automatically converted to
     * upper case and trimmed.
     */
    public AbstractAutoCADElementExtractor(String name, AutoCADAttribute... requiredColumns){
        this.name = name.toUpperCase();
        this.requiredColumns = Arrays.asList(requiredColumns);
    }
    
    public final String getName(){
        return name;
    }
    
    protected final void setColumns(HashMap<AutoCADAttribute, Integer> cols){
        currentCols = cols;
    }
    protected final void setCurrentRow(Row row){
        currentRow = row;
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
        int colIdx = currentCols.get(col);
        if(colIdx == -1){
            throw new RuntimeException(String.format("Missing column: %s", col.getHeader()));
        }
        return currentRow.getCell(colIdx).toString(); // returns the contents of the cell as text 
    }
    
    protected double getCellDouble(AutoCADAttribute col){
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
    
    protected int getCellInt(AutoCADAttribute col){
        return (int)getCellDouble(col);
    }
    
    
    /**
     * Checks to see if the given columns contain all the columns this needs.
     * @param columns
     * @return 
     */
    public final boolean hasRequiredColumns(HashMap<AutoCADAttribute, Integer> columns){
        return requiredColumns.stream().allMatch((AutoCADAttribute requiredColumn)->{
            return columns.containsKey(requiredColumn) && columns.get(requiredColumn) != -1;
        });
    }
    
    /**
     * Extracts data from the given row, and converts it to an AutoCAD element.
     * @param columns the mapping of column headers to the index of the column
     * in the given row.
     * @param currentRow the row to extract data from
     * @return the extracted AutoCADElement.
     */
    public final T extract(HashMap<AutoCADAttribute, Integer> columns, Row currentRow){
        setColumns(columns);
        setCurrentRow(currentRow);
        return doExtract();
    }
    
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
    public abstract T doExtract();
}
