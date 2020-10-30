package autocadDrawingChecker.data.csv;

import autocadDrawingChecker.data.AbstractGradableDataType;
import autocadDrawingChecker.data.core.AbstractTableParser;
/**
 *
 * @author Matt
 */
public class GenericCsvDataType implements AbstractGradableDataType {

    @Override
    public String getName() {
        return "CSV";
    }

    @Override
    public String getDescription() {
        return "A generic Comma Separated Values file";
    }

    @Override
    public AbstractTableParser createParser(String fileName) {
        return new CsvParser(fileName);
    }
}
