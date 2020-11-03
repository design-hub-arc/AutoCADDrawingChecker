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
    protected AbstractRecordConverter<CSVRecord> createExtractor(Map<String, Integer> columns) {
        return new CsvRecordConverter(columns);
    }

    
    @Override
    protected boolean isValidRow(CSVRecord row) {
        return row.isConsistent();
    }
    
    @Override
    protected void forEachRowIn(List<CSVRecord> sheet, BiConsumer<AbstractRecordConverter<CSVRecord>, CSVRecord> doThis) {
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
        AbstractRecordConverter<CSVRecord> converter = this.createExtractor(headerMap);
        
        sheet.stream().forEach((CSVRecord apacheRecord)->{
            doThis.accept(converter, apacheRecord);
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
}
