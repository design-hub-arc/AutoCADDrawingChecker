package autocadDrawingChecker.data.core;

import autocadDrawingChecker.logging.Logger;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

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
    
    // will need to change this to a more generic return type
    protected abstract AbstractRecordConverter createExtractor(HashMap<String, Integer> columns);
    
    
    
    protected abstract boolean isValidRow(RowType row);
    
    protected final String sanitize(String s){
        return s.trim(); // may not want to uppercase
    }
    
    protected abstract List<DataSet> extractAllDataSetsFrom(InputStream in) throws IOException;
    
    protected abstract DataSet doParseSheet(SheetType sheet);
    
    protected abstract DataSet doParseFirstSheet(InputStream in) throws IOException;
    
    public final DataSet parseSheet(SheetType sheet){
        return doParseSheet(sheet);
    }
    
    
    
    
    
    
    public synchronized final DataSet parseFirstSheet(String path) throws IOException {
        DataSet ret = null;
        try (InputStream in = new FileInputStream(path)) {
            ret = doParseFirstSheet(in);
        } catch(Exception ex){
            Logger.logError(ex);
            throw ex;
        }
        return ret;
    }
    
    public synchronized final List<DataSet> parseAllSheets(String path) throws IOException {
        List<DataSet> allDataSets = new LinkedList<>();
        try (InputStream in = new FileInputStream(path)) {
            allDataSets = extractAllDataSetsFrom(in);
        } catch(Exception ex){
            Logger.logError(ex);
            throw ex;
        }
        return allDataSets;
    }
}
