package autocadDrawingChecker.data.csv;

import autocadDrawingChecker.data.core.AbstractRecordConverter;
import autocadDrawingChecker.data.core.AbstractTableParser;
import autocadDrawingChecker.data.core.DataSet;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    protected DataSet doParseSheet(List<CSVRecord> sheet) {
        DataSet ret = this.createExtractionHolder("This csv file should have a name");
        AbstractRecordConverter converter = this.createExtractor(new HashMap<>()); // how to get headers?
        sheet.stream().filter((CSVRecord rec)->{
            return converter.canExtractRow(rec);
        }).map((rec)->{
            return converter.extract(rec);
        }).filter((rec)->{
            return rec != null;
        }).forEach(ret::add);
        return ret;
    }
    
    @Override
    protected List<DataSet> extractAllDataSetsFrom(InputStream in) throws IOException {
        List<DataSet> allSets = new LinkedList<>();
        DataSet onlyDataSet = doParseFirstSheet(in);
        if(onlyDataSet != null){
            allSets.add(onlyDataSet);
        }
        
        return allSets;
    }
    
    @Override
    protected DataSet doParseFirstSheet(InputStream in) throws IOException {
        CSVParser parser = new CSVParser(new InputStreamReader(in), CSVFormat.DEFAULT);
        DataSet ret = doParseSheet(parser.getRecords());
        parser.close();
        return ret;
    }
}
