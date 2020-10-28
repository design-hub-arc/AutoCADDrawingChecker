package autocadDrawingChecker.data.core;

import autocadDrawingChecker.logging.Logger;
import java.util.HashMap;
import java.util.regex.Pattern;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

/**
 *
 * @author Matt
 */
public class RecordExtractor {
    private final HashMap<String, Integer> columns;
    private Row currentRow;
    
    public RecordExtractor(HashMap<String, Integer> columns){
        if(columns == null){
            throw new NullPointerException("Columns cannot be null");
        }
        this.columns = (HashMap<String, Integer>)columns.clone();
        for(String reqCol : this.getRequiredColumns()){
            for(String actualCol : columns.keySet()){
                if(!reqCol.equals(actualCol) && Pattern.matches(reqCol, actualCol)){
                    this.columns.put(reqCol, columns.get(actualCol));
                }
            }
        }
    }
    
    private String sanitize(String s){
        return s.trim().toUpperCase();
    }
    
    private Object getCell(String col){
        col = sanitize(col);
        Object ret = null;
        int colIdx = columns.get(col);
        if(colIdx == -1){
            throw new RuntimeException(String.format("Missing column: %s", col));
        }
        Cell c = currentRow.getCell(colIdx);
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
                Logger.logError(String.format("RecordExtractor encountered cell with type %s", c.getCellType().name()));
                //ret = c.toString();
                break;
        }
        return ret;
    }
    
    protected String getCellString(String col){
        String ret = null;
        try {
            ret = (String)getCell(col);
        } catch(ClassCastException ex){
            Logger.logError(String.format("Column \"%s\" is not a string", col));
            throw ex;
        }
        return ret;
    }
    
    protected boolean rowHasCell(Row row, String col){
        col = sanitize(col);
        int colIdx = (columns.containsKey(col)) ? columns.get(col) : -1;
        boolean hasCol = 
            colIdx != -1 && 
            row.getCell(colIdx) != null && row.getCell(colIdx).getCellType() != CellType.BLANK;
        // getCell doesn't throw an exception if it doesn't have a cell for the given column: it just returns null
        
        return hasCol;
    }
    protected boolean currentRowHasCell(String col){
        return rowHasCell(currentRow, col);
    }
    
    protected String[] getRequiredColumns(){
        return new String[0];
    }
    
    protected boolean hasRequiredColumns(Row row){
        boolean ret = true;
        String[] reqCols = getRequiredColumns();
        for(int i = 0; i < reqCols.length && ret; i++){
            if(!rowHasCell(row, reqCols[i])){
                ret = false;
            }
        }
        return ret;
    }
    
    boolean canExtractRow(Row row){
        return hasRequiredColumns(row);
    }
    
    /**
     * Extracts data from the given row, and converts it to an AutoCAD element.
     * @param row the row to extract data from
     * @return the extracted AutoCADElement.
     */
    public synchronized final Record extract(Row row){
        // temporarily set the row. Note this method is synchronized to prevent multithreading issues
        this.currentRow = row;
        Record ret = doExtract();
        this.currentRow = null;
        return ret;
    }
    
    private Record doExtract(){
        Record ret = createNew();
        columns.keySet().forEach((header)->{
            if(this.currentRowHasCell(header)){
                ret.setAttribute(header, getCell(header));
            }
        });
        return ret;
    }
    
    protected Record createNew(){
        return new Record();
    }
}
