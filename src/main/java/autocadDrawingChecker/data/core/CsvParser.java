package autocadDrawingChecker.data.core;

import java.util.HashMap;
import java.util.List;
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
    protected RecordExtractor createExtractor(HashMap<String, Integer> columns) {
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
}
