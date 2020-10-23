package autocadDrawingChecker.data.core;

import autocadDrawingChecker.data.AbstractGradableDataType;
import java.io.IOException;
import java.util.HashMap;

/**
 *
 * @author Matt
 */
public class GenericExcelDataType implements AbstractGradableDataType {
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
