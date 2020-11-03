package autocadDrawingChecker.data.core;

import autocadDrawingChecker.logging.Logger;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.regex.Pattern;

/**
 *
 * @author Matt
 * @param <SheetType>
 * @param <RowType> the class each row from the table this parses is stored as
 */
public abstract class AbstractTableParser<SheetType, RowType> {
    
    protected abstract boolean doesRowHaveCell(RowType currRow, int idx);
    
    protected abstract Object doGetCell(RowType currRow, int idx);
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
    }
    
    public final boolean canExtractRow(RowType row){
        return hasRequiredColumns(row);
    }*/
    
    /**
     * Extracts data from the given row, and converts it to a Record.
     * @param columns the columns for the file this row comes from
     * @param row the row to extract data from
     * @return the extracted Record.
     */
    private Record convertRecord(Map<String, Integer> columns, RowType row){
        Record ret = createNew();
        columns.forEach((header, index)->{
            if(this.doesRowHaveCell(row, index)){
                ret.setAttribute(header, doGetCell(row, index));
            }
        });
        
        return ret;
    }
    
    private boolean canConvert(Map<String, Integer> columns, RowType row){
        boolean ret = true;
        String[] reqCols = this.getRequiredColumns();
        for(int i = 0; i < reqCols.length && ret; i++){
            if(!this.doesRowHaveCell(row, columns.get(sanitize(reqCols[i])))){
                ret = false;
            }
        }
        return ret;
    }
    
    protected final String sanitize(String s){
        return s.trim().toUpperCase(); // not sure if I want to uppercase
    }
    
    protected abstract Map<String, Integer> getHeadersFrom(SheetType sheet);
    
    private Map<String, Integer> loadHeadersFrom(SheetType sheet){
        HashMap<String, Integer> cleanedHeaders = new HashMap<>();
        Map<String, Integer> raw = getHeadersFrom(sheet);
        raw.forEach((k, v)->{
            cleanedHeaders.put(sanitize(k), v);
        });
        for(String reqCol : this.getRequiredColumns()){
            for(String actualCol : raw.keySet()){
                if(!reqCol.equals(actualCol) && Pattern.matches(reqCol, actualCol)){
                    cleanedHeaders.put(reqCol, raw.get(actualCol));
                }
            }
        }
        return cleanedHeaders;
    }
    
    /**
     * Converts the given sheet into a DataSet usable by the program.
     * 
     * @param dataSetName the name the program should give to the DataSet this returns.
     * This will usually be in the format workbook_name - sheet_name.
     * 
     * @param sheet the sheet to convert
     * @return the converted sheet
     */
    protected final DataSet parseSheet(String dataSetName, SheetType sheet){
        DataSet containedTherein = this.createExtractionHolder(dataSetName);
        Map<String, Integer> headers = this.loadHeadersFrom(sheet);
        forEachRowIn(sheet, (RowType row)->{
            if(isValidRow(row)){
                Record converted = null;
                try {
                    if(canConvert(headers, row)){
                        converted = this.convertRecord(headers, row);
                    }
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
            List<SheetType> allSheets = this.extractSheets(path);
            ret = parseSheet(path, allSheets.get(0));
        } catch(Exception ex){
            Logger.logError(ex);
            throw ex;
        }
        return ret;
    }
    
    public synchronized final List<DataSet> parseAllSheets(String path) throws IOException {
        List<DataSet> allDataSets = new LinkedList<>();
        try {
            List<SheetType> allSheets = this.extractSheets(path);
            allSheets.stream().map((SheetType sheet)->{
                return this.parseSheet(String.format("%s - %s", path, getSheetName(sheet)), sheet);
            }).filter((DataSet converted)->{
                return converted != null;
            }).forEach(allDataSets::add);
        } catch(Exception ex){
            Logger.logError(ex);
            throw ex;
        }
        return allDataSets;
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
    
    protected Record createNew(){
        return new Record();
    }
    
    protected String[] getRequiredColumns(){
        return new String[0];
    }
    
    protected abstract boolean isValidRow(RowType row);
    /**
     * Subclasses should override this method to read the given file, run it through
     * some file reader, and return each spreadsheet contained therein.
     * 
     * @param path the complete file path to the file to parse.
     * @return a list of sheets contained in the given file. Should never return null.
     * @throws IOException if any errors occur when reading the file.
     */
    protected abstract List<SheetType> extractSheets(String path) throws IOException;
    /**
     * This method should be overridden to iterate over each row in the given sheet, 
     * and pass both the row and converter to the given Consumer.
     * 
     * @param sheet the sheet to iterate over
     * @param doThis the thing to do with each row in the given sheet
     */
    protected abstract void forEachRowIn(SheetType sheet, Consumer<RowType> doThis);
    protected abstract String getSheetName(SheetType sheet);
    
}
