package autocadDrawingChecker.data.core;

import autocadDrawingChecker.logging.Logger;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.regex.Pattern;

/**
 * The AbstractTableParser class is used to convert data from files into a more generic and useful format the
 * program can use. In this way, where the data comes from is irrelevant to the grading process.
 * 
 * This class uses the Algorithm design pattern, allowing developers to pick-and-choose what functionality they
 * wish to override when subclassing.
 * 
 * 
 * @author Matt Crow
 * @param <SheetType> the class representing each individual sheet in the files this should parse.
 * @param <RowType> the class each row from the table this parses is stored as
 */
public abstract class AbstractTableParser<SheetType, RowType> {
    /*
    Part I: Public entry points
    */
    
    /**
     * Reads the given file, and returns the contents of its first sheet as a DataSet.
     * Use this method to get the instructor file contents.
     * 
     * @param path the complete file path to the file to parse.
     * @return the converted contents of the first sheet of the given file.
     * @throws IOException if anything untoward happens when parsing the given file.
     */
    public final DataSet parseFirstSheet(String path) throws IOException {
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
    
    /**
     * Reads the given file, and returns the contents of each sheet contained therein as a DataSet.
     * Use this method to get the student file contents.
     * 
     * @param path the complete file path to the file to parse.
     * @return the converted contents of the given file, with each sheet as a separate DataSet.
     * @throws IOException if anything untoward happens when parsing the given file.
     */
    public final List<DataSet> parseAllSheets(String path) throws IOException {
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
    
    
    
    /*
    Part II: Private algorithm steps
    */
    
    /**
     * Sanitizes the given header string to ensure all headers are in the same format.
     * @param s the header to clean.
     * @return the cleaned header.
     */
    private String sanitize(String s){
        return s.trim().toUpperCase(); // not sure if I want to uppercase
    }
    
    /**
     * Checks if this can properly convert the given row.
     * @param columns the set of columns for the sheet from whence this row came.
     * @param row the row to check.
     * @return whether or not the row can be converted.
     */
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
    
    /**
     * Locates headers in the given sheet, and converts them to the format
     * this requires.
     * 
     * @param sheet the sheet to locate headers in.
     * @return a Map of sanitized headers to the column they map to in the given sheet.
     */
    private Map<String, Integer> getHeadersFrom(SheetType sheet){
        HashMap<String, Integer> cleanedHeaders = new HashMap<>();
        Map<String, Integer> raw = doGetHeadersFrom(sheet);
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
    private DataSet parseSheet(String dataSetName, SheetType sheet){
        DataSet containedTherein = this.createExtractionHolder(dataSetName);
        Map<String, Integer> headers = this.getHeadersFrom(sheet);
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
    
    
    
    
    /*
    Part III: Optionally overridable methods
    */
    
    /**
     * Creates the DataSet which will hold the contents
     * of the file this is parsing. Subclasses may want to
     * override this method to return a DataSet for their
     * own record type.
     * 
     * @param name the name of the sheet this is parsing
     * @return the DataSet this will store the parsed file contents in 
     */
    protected DataSet createExtractionHolder(String name){
        return new DataSet(name);
    }    
    
    /**
     * Creates a new Record used to hold the converted contents of a spreadsheet row.
     * Subclasses may want to override this method to return a subclass of Record.
     * 
     * @return an empty Record 
     */
    protected Record createNew(){
        return new Record();
    }
    
    /**
     * Only override this method if the parsed contents must have specific columns.
     * Columns may include regular expressions if you wish.
     * 
     * @return the list of columns each row converted by this must have. 
     */
    protected String[] getRequiredColumns(){
        return new String[0];
    }
    
    
    
    
    /*
    Part IV: Mandatory overridable methods
    */
    
    /**
     * 
     * @param sheet the sheet to get the name for.
     * @return the name of the given sheet.
     */
    protected abstract String getSheetName(SheetType sheet);
    
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
     * Use this method to get the headers contained in the given sheet. You needn't worry about
     * whether or not the headers are formatted nicely, as the AbstractTableParser handles 
     * sanitization for you.
     * 
     * @param sheet the sheet to get headers from.
     * @return a mapping of column headers to their index.
     */
    protected abstract Map<String, Integer> doGetHeadersFrom(SheetType sheet);
    
    /**
     * Checks to see if this should parse the given row.
     * 
     * @param row the row to check.
     * @return whether or not the given row is valid.
     */
    protected abstract boolean isValidRow(RowType row);
    
    /**
     * This method should be overridden to iterate over each row in the given sheet, 
     * and pass the row to the given Consumer.
     * 
     * @param sheet the sheet to iterate over
     * @param doThis the thing to do with each row in the given sheet
     */
    protected abstract void forEachRowIn(SheetType sheet, Consumer<RowType> doThis);
    
    /**
     * Returns whether or not a row has a valid cell at the given index.
     * You may want this method to return false if the given cell is empty or otherwise not useful.
     * 
     * @param currRow the row to check for a cell.
     * @param idx the index of the column the cell should be in.
     * @return whether or not the given row has a valid cell at the given index.
     */    
    protected abstract boolean doesRowHaveCell(RowType currRow, int idx);
    
    /**
     * Gets the value of a cell in the given row. Subclasses should
     * try to convert the cell's value to a number whenever possible.
     * 
     * @param currRow the row to get the cell from.
     * @param idx the column index of the cell to get.
     * @return the value of the cell this locates.
     */
    protected abstract Object doGetCell(RowType currRow, int idx);    
}
