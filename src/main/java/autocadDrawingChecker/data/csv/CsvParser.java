package autocadDrawingChecker.data.csv;

import autocadDrawingChecker.data.core.AbstractRecordConverter;
import autocadDrawingChecker.data.core.AbstractTableParser;
import autocadDrawingChecker.logging.Logger;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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
        AbstractRecordConverter<CSVRecord> converter = this.createExtractor(sheet.get(0).getParser().getHeaderMap());
        
        sheet.stream().forEach((CSVRecord apacheRecord)->{
            doThis.accept(converter, apacheRecord);
        });
    }

    @Override
    protected List<List<CSVRecord>> extractSheets(String path) throws IOException {
        List<List<CSVRecord>> sheets = new LinkedList<>();
        //                                                                                      need to specify that this has headers
        try (CSVParser parser = new CSVParser(new InputStreamReader(new FileInputStream(path)), CSVFormat.DEFAULT.withHeader())) {
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
