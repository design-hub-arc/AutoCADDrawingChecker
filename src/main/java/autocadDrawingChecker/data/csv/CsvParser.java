package autocadDrawingChecker.data.csv;

import autocadDrawingChecker.data.core.AbstractRecordConverter;
import autocadDrawingChecker.data.core.AbstractTableParser;
import autocadDrawingChecker.data.core.DataSet;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiConsumer;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

/**
 *
 * @author Matt
 */
public class CsvParser extends AbstractTableParser<List<CSVRecord>, CSVRecord> {

    @Override
    protected AbstractRecordConverter createExtractor(HashMap<String, Integer> columns) {
        return new CsvRecordConverter(columns);
    }

    
    @Override
    protected boolean isValidRow(CSVRecord row) {
        return row.isConsistent();
    }
    
    @Override
    protected void forEachRowIn(List<CSVRecord> sheet, BiConsumer<AbstractRecordConverter, CSVRecord> doThis) {
        AbstractRecordConverter converter = this.createExtractor(new HashMap<>()); // how to get headers?
        
        sheet.stream().forEach((CSVRecord apacheRecord)->{
            doThis.accept(converter, apacheRecord);
        });
    }
    
    @Override
    protected List<DataSet> extractAllDataSetsFrom(String path) throws IOException {
        List<DataSet> allSets = new LinkedList<>();
        DataSet onlyDataSet = doParseFirstSheet(path);
        if(onlyDataSet != null){
            allSets.add(onlyDataSet);
        }
        return allSets;
    }
    
    @Override
    protected DataSet doParseFirstSheet(String path) throws IOException {
        CSVParser parser = new CSVParser(new InputStreamReader(new FileInputStream(path)), CSVFormat.DEFAULT);
        DataSet ret = parseSheet(path, parser.getRecords());
        parser.close();
        return ret;
    }
}
