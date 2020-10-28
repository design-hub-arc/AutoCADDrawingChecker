package autocadDrawingChecker.data.core;

import autocadDrawingChecker.data.AbstractGradableDataType;

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
    public ExcelParser createParser(String fileName) {
        return new ExcelParser(fileName);
    }
}
