package autocadDrawingChecker.data.core;

import autocadDrawingChecker.logging.Logger;
import java.util.HashMap;
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
        this.columns = columns;
    }
    
    private Object getCell(String col){
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
                ret = c.getNumericCellValue();
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
        int colIdx = columns.get(col);
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
    
    protected boolean hasRequiredColumns(Row currentRow){
        boolean ret = true;
        String[] reqCols = getRequiredColumns();
        for(int i = 0; i < reqCols.length && ret; i++){
            if(!rowHasCell(currentRow, reqCols[i])){
                ret = false;
            }
        }
        return ret;
    }
    
    boolean canExtractRow(Row currentRow){
        return hasRequiredColumns(currentRow);
    }
    
    /**
     * Extracts data from the given row, and converts it to an AutoCAD element.
     * @param currentRow the row to extract data from
     * @return the extracted AutoCADElement.
     */
    public synchronized final Record extract(Row currentRow){
        // temporarily set the row. Note this method is synchronized to prevent multithreading issues
        this.currentRow = currentRow;
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
