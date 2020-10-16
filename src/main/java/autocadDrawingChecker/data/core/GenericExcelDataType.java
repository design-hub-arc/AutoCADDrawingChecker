package autocadDrawingChecker.data.core;

import autocadDrawingChecker.data.AbstractGradeableDataType;
import java.io.IOException;
import java.util.HashMap;

/**
 *
 * @author Matt
 */
public class GenericExcelDataType implements AbstractGradeableDataType {

    @Override
    public RecordExtractor createExtractor(HashMap<String, Integer> columns) {
        return new RecordExtractor(columns);
    }

    @Override
    public DataSet createExtractionHolder(String fileName) {
        return new DataSet(fileName);
    }

    @Override
    public String getName() {
        return "Basic Excel";
    }

    @Override
    public String getDescription() {
        return "Any Excel file with headers in the first row";
    }

    @Override
    public DataSet parseFile(String fileName) throws IOException {
        return new ExcelParser(fileName).parse();
    }
}
