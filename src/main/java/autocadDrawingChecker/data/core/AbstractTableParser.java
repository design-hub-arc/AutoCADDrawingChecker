package autocadDrawingChecker.data.core;

import autocadDrawingChecker.logging.Logger;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 *
 * @author Matt
 * @param <SheetType>
 * @param <RowType> the class each row from the table this parses is stored as
 */
public abstract class AbstractTableParser<SheetType, RowType> {
    
    protected final String sanitize(String s){
        return s.trim(); // may not want to uppercase
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
        forEachRowIn(sheet, (AbstractRecordConverter<RowType> converter, RowType row)->{
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
    
    protected abstract AbstractRecordConverter createExtractor(Map<String, Integer> columns);
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
     * and pass both the row and converter to the given BiConsumer.
     * 
     * @param sheet the sheet to iterate over
     * @param doThis the thing to do with each row in the given sheet
     */
    protected abstract void forEachRowIn(SheetType sheet, BiConsumer<AbstractRecordConverter<RowType>, RowType> doThis);
    protected abstract String getSheetName(SheetType sheet);
    
}
