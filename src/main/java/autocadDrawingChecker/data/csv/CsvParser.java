package autocadDrawingChecker.data.csv;

import autocadDrawingChecker.data.core.AbstractRecordConverter;
import autocadDrawingChecker.data.core.AbstractTableParser;
import autocadDrawingChecker.logging.Logger;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

/**
 *
 * @author Matt
 */
public class CsvParser extends AbstractTableParser<List<CSVRecord>, CSVRecord> {
    private final boolean hasHeaders;
    
    public CsvParser(boolean hasHeaders){
        super();
        this.hasHeaders = hasHeaders;
    }

    
    @Override
    protected boolean isValidRow(CSVRecord row) {
        return row.isConsistent();
    }
    
    
    @Override
    protected Map<String, Integer> getHeadersFrom(List<CSVRecord> sheet) {
        HashMap<String, Integer> headerMap = new HashMap<>();
        if(sheet.isEmpty()){
            // cannot parse, just ignore
        } else if(hasHeaders){
            sheet.get(0).getParser().getHeaderMap().forEach((k, v)->{
                headerMap.put(k, v); // copy headers from Apache parser
            });
        } else {
            // sheet isn't empty, and we don't have headers
            int numCols = sheet.get(0).size();
            for(int i = 0; i < numCols; i++){
                headerMap.put(String.format("Column#%d", i + 1), i);
            } // make my own headers
            
        }
        return headerMap;
    }
    
    @Override
    protected void forEachRowIn(List<CSVRecord> sheet, Consumer<CSVRecord> doThis) {
        sheet.stream().forEach((CSVRecord apacheRecord)->{
            doThis.accept(apacheRecord);
        });
    }

    @Override
    protected List<List<CSVRecord>> extractSheets(String path) throws IOException {
        List<List<CSVRecord>> sheets = new LinkedList<>();
        CSVFormat format = (this.hasHeaders) ? CSVFormat.DEFAULT.withHeader() : CSVFormat.DEFAULT;
        try (CSVParser parser = new CSVParser(new InputStreamReader(new FileInputStream(path)), format)) {
            sheets.add(parser.getRecords());
        } catch (Exception ex){
            Logger.logError(ex);
        }
        return sheets;
    }

    @Override
    protected String getSheetName(List<CSVRecord> sheet) {
        return "data";
    }
    
    @Override
    protected boolean doesRowHaveCell(CSVRecord currRow, int idx) {
        return currRow.isSet(idx) && currRow.get(idx) != null;
    }
    
    @Override
    protected Object doGetCell(CSVRecord currRow, int idx) {
        Object ret = currRow.get(idx);
        boolean foundType = false;
        // see if it's numeric
        try {
            ret = Double.parseDouble(ret.toString());
            foundType = true;
        } catch(NumberFormatException ex){
            // not a number
        }
        
        if(!foundType){
            // first, convert to a string
            ret = ret.toString();
            if(ret.toString().equalsIgnoreCase("true")){
                ret = Boolean.TRUE;
                foundType = true; // it's a boolean
            } else if(ret.toString().equalsIgnoreCase("false")){
                ret = Boolean.FALSE;
                foundType = true;
            }
            // don't use Boolean.parseString methods, as they don't account for values other than true or false
            // this way, if it is neither true nor false, this method returns it as a string
        }
        return ret;
    }
}
