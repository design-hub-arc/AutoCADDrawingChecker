package autocadDrawingChecker.data.csv;

import autocadDrawingChecker.data.AbstractGradableDataType;
import autocadDrawingChecker.data.core.AbstractTableParser;
/**
 *
 * @author Matt
 */
public class GenericCsvDataType implements AbstractGradableDataType {
    private final boolean hasHeaders;
    
    public GenericCsvDataType(boolean hasHeaders){
        this.hasHeaders = hasHeaders;
    }
    
    @Override
    public String getName() {
        return "CSV";
    }

    @Override
    public String getDescription() {
        return String.format("A generic Comma Separated Values file, %s headers", (hasHeaders) ? "with" : "without");
    }

    @Override
    public AbstractTableParser createParser() {
        return new CsvParser(hasHeaders);
    }
}
