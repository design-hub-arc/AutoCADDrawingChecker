package autocadDrawingChecker.data.excel;

import autocadDrawingChecker.data.AbstractGradableDataType;
import autocadDrawingChecker.data.core.AbstractTableParser;

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
    public AbstractTableParser createParser() {
        return new ExcelParser();
    }
}
