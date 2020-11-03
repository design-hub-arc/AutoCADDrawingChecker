package autocadDrawingChecker.data.core;

import autocadDrawingChecker.logging.Logger;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * The AbstractRecordConverter class is used to convert records from a file into a more generic
 * format usable by the program, essentially rendering the type of source a record came from irrelevant.
 * In this way, subclasses of this class serve the Adapter design pattern.
 * 
 * This class may later be merged into AbstractTableParser, given how coupled their behaviors are. 
 * 
 * @author Matt Crow
 * @param <RowType> the class this will convert to the generic Record class used by the program. 
 */
public abstract class AbstractRecordConverter<RowType> {
    /*
    protected abstract Object doGetCell(RowType currRow, int idx);
    
    protected final Object getCell(String col){
        col = sanitize(col);
        int colIdx = columns.get(col);
        if(colIdx == -1){
            throw new RuntimeException(String.format("Missing column: %s", col));
        }
        return doGetCell(currentRow, colIdx);
    }
    
    protected final String getCellString(String col){
        String ret = null;
        try {
            ret = getCell(col).toString();
        } catch(ClassCastException ex){
            Logger.logError(String.format("Column \"%s\" is not a string", col));
            throw ex;
        }
        return ret;
    }
    */
    //protected abstract boolean doesRowHaveCell(RowType currRow, int idx);
    /*
    protected final boolean rowHasCell(RowType row, String col){
        col = sanitize(col);
        int colIdx = (columns.containsKey(col)) ? columns.get(col) : -1;
        boolean hasCol = 
            colIdx != -1 && 
            doesRowHaveCell(row, colIdx);
        return hasCol;
    }*/
    /*
    protected final boolean currentRowHasCell(String col){
        return rowHasCell(currentRow, col);
    }*/
    /*
    protected final boolean hasRequiredColumns(RowType row){
        boolean ret = true;
        String[] reqCols = getRequiredColumns();
        for(int i = 0; i < reqCols.length && ret; i++){
            if(!rowHasCell(row, reqCols[i])){
                ret = false;
            }
        }
        return ret;
    }*/
    /*
    public final boolean canExtractRow(RowType row){
        return hasRequiredColumns(row);
    }*/
    
    /**
     * Extracts data from the given row, and converts it to an AutoCAD element.
     * @param row the row to extract data from
     * @return the extracted AutoCADElement.
     */
    /*
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
    }*/
    
    
}
