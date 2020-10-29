package autocadDrawingChecker.data.core;

import autocadDrawingChecker.logging.Logger;
import java.util.HashMap;
import java.util.regex.Pattern;

/**
 *
 * @author Matt
 * @param <RowType>
 */
public abstract class AbstractRecordExtractor<RowType> {
    private final HashMap<String, Integer> columns;
    private RowType currentRow;
    
    /**
     * 
     * @param columns the columns located by the ExcelParser.
     * If any of this' required columns involve regex, this
     * will automatically locate them as well.
     */
    @SuppressWarnings("unchecked")
    public AbstractRecordExtractor(HashMap<String, Integer> columns){
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
    
    protected final String sanitize(String s){
        return s.trim().toUpperCase();
    }
    
    protected abstract Object doGetCell(RowType currRow, int idx);
    
    protected final Object getCell(String col){
        col = sanitize(col);
        int colIdx = columns.get(col);
        if(colIdx == -1){
            throw new RuntimeException(String.format("Missing column: %s", col));
        }
        return doGetCell(currentRow, colIdx);
    }
    
    protected String getCellString(String col){
        String ret = null;
        try {
            ret = getCell(col).toString();
        } catch(ClassCastException ex){
            Logger.logError(String.format("Column \"%s\" is not a string", col));
            throw ex;
        }
        return ret;
    }
    
    protected abstract boolean doesRowHaveCell(RowType currRow, int idx);
    
    protected boolean rowHasCell(RowType row, String col){
        col = sanitize(col);
        int colIdx = (columns.containsKey(col)) ? columns.get(col) : -1;
        boolean hasCol = 
            colIdx != -1 && 
            doesRowHaveCell(row, colIdx);
        return hasCol;
    }
    protected boolean currentRowHasCell(String col){
        return rowHasCell(currentRow, col);
    }
    
    protected String[] getRequiredColumns(){
        return new String[0];
    }
    
    protected boolean hasRequiredColumns(RowType row){
        boolean ret = true;
        String[] reqCols = getRequiredColumns();
        for(int i = 0; i < reqCols.length && ret; i++){
            if(!rowHasCell(row, reqCols[i])){
                ret = false;
            }
        }
        return ret;
    }
    
    boolean canExtractRow(RowType row){
        return hasRequiredColumns(row);
    }
    
    /**
     * Extracts data from the given row, and converts it to an AutoCAD element.
     * @param row the row to extract data from
     * @return the extracted AutoCADElement.
     */
    public synchronized final Record extract(RowType row){
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
