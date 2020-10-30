package autocadDrawingChecker.data.core;

import autocadDrawingChecker.logging.Logger;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiConsumer;

/**
 *
 * @author Matt
 * @param <SheetType>
 * @param <RowType> the class each row from the table this parses is stored as
 */
public abstract class AbstractTableParser<SheetType, RowType> {
    private RowType currRow;
    
    public AbstractTableParser(){
        this.currRow = null;
    }
        
    protected final void setCurrRow(RowType newRow){
        this.currRow = newRow;
    }
    protected final RowType getCurrRow(){
        return this.currRow;
    }
    
    /**
     * Creates the DataSet which will hold the contents
     * of the file this is parsing. Subclasses will always
     * override this method to return a DataSet for their
     * own record type.
     * 
     * @param name the name of the sheet this is parsing
     * @return the DataSet this will store the parsed Excel file contents in 
     */
    protected DataSet createExtractionHolder(String name){
        return new DataSet(name);
    }
    
    protected abstract AbstractRecordConverter createExtractor(HashMap<String, Integer> columns);
    
    
    
    protected abstract boolean isValidRow(RowType row);
    
    protected final String sanitize(String s){
        return s.trim(); // may not want to uppercase
    }
    
    
    
    protected abstract List<DataSet> extractAllDataSetsFrom(String path) throws IOException;
    
    
    protected abstract DataSet doParseFirstSheet(String path) throws IOException;
    
    
    
    
    /**
     * This method should be overridden to iterate over each row in the given sheet, 
     * and pass both the row and converter to the given BiConsumer.
     * 
     * @param sheet the sheet to iterate over
     * @param doThis the thing to do with each row in the given sheet
     */
    protected abstract void forEachRowIn(SheetType sheet, BiConsumer<AbstractRecordConverter, RowType> doThis);
    
    protected final DataSet parseSheet(String dataSetName, SheetType sheet){
        DataSet containedTherein = this.createExtractionHolder(dataSetName);
        forEachRowIn(sheet, (AbstractRecordConverter converter, RowType row)->{
            if(isValidRow(row)){
                Record converted = null;
                try {
                    converted = converter.extract(row);
                } catch (Exception ex){
                    Logger.logError(ex);
                }
                if(converted != null){
                    containedTherein.add(converted);
                }
            }
        });
        return containedTherein;
    }
    
    
    
    
    
    
    public synchronized final DataSet parseFirstSheet(String path) throws IOException {
        DataSet ret = null;
        try {
            ret = doParseFirstSheet(path);
        } catch(Exception ex){
            Logger.logError(ex);
            throw ex;
        }
        return ret;
    }
    
    public synchronized final List<DataSet> parseAllSheets(String path) throws IOException {
        List<DataSet> allDataSets = new LinkedList<>();
        try {
            allDataSets = extractAllDataSetsFrom(path);
        } catch(Exception ex){
            Logger.logError(ex);
            throw ex;
        }
        return allDataSets;
    }
}
