package autocadDrawingChecker.data.core;

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
    
    public CsvParser(String fileName){
        super(fileName);
    }

    @Override
    protected AbstractRecordExtractor createExtractor(HashMap<String, Integer> columns) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected CSVRecord locateHeaderRow(List<CSVRecord> sheet) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected boolean isValidRow(CSVRecord row) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected List<DataSet> extractAllDataSetsFrom(InputStream in) throws IOException {
        List<DataSet> allSets = new LinkedList<>();
        CSVParser parser = new CSVParser(new InputStreamReader(in), CSVFormat.DEFAULT);
        parser.getRecords();
        parser.close();
        return allSets;
    }
}
