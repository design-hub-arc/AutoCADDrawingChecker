package autocadDrawingChecker._notImpl;

import autocadDrawingChecker.logging.Logger;
import java.util.HashMap;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

/**
 * @author Matt
 */
public class RecordExtractor {
    private HashMap<String, Integer> currentCols;
    private Row currentRow;
    
    private Object getCell(String col){
        Object ret = null;
        int colIdx = currentCols.get(col);
        if(colIdx == -1){
            throw new RuntimeException(String.format("Missing column: %s", col));
        }
        Cell c = currentRow.getCell(colIdx);
        switch(c.getCellType()){
            case BOOLEAN:
                ret = c.getBooleanCellValue();
                break;
            case NUMERIC:
                ret = c.getNumericCellValue();
                break;
            case STRING:
                ret = c.getStringCellValue();
                break;
            default:
                Logger.logError(String.format("RecordExtractor encountered cell with type %s", c.getCellType().name()));
                ret = c.toString();
                break;
        }
        return ret;
    }
    
    protected boolean currentRowHasCell(String col){
        int colIdx = currentCols.get(col);
        boolean hasCol = colIdx != -1;
        if(hasCol){
            try {
                currentRow.getCell(colIdx);
            } catch(Exception ex){
                Logger.logError(ex);
                hasCol = false;
            }
        }
        return hasCol;
    }
    
    /**
     * Extracts data from the given row, and converts it to an AutoCAD element.
     * @param columns the mapping of columns to the index of the column
     * in the given row.
     * @param currentRow the row to extract data from
     * @return the extracted AutoCADElement.
     */
    public synchronized final Record extract(HashMap<String, Integer> columns, Row currentRow){
        // temporarily set the columns and row. Note this method is synchronized to prevent multithreading issues
        this.currentCols = columns;
        this.currentRow = currentRow;
        Record ret = doExtract();
        this.currentCols = null;
        this.currentRow = null;
        return ret;
    }
    
    private Record doExtract(){
        Record ret = new Record();
        currentCols.keySet().forEach((header)->{
            if(this.currentRowHasCell(header)){
                ret.setAttribute(header, getCell(header));
            }
        });
        return ret;
    } 
}
