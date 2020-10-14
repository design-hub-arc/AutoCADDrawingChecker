package autocadDrawingChecker.data.core;

import autocadDrawingChecker.data.AbstractGradeableDataType;

/**
 *
 * @author Matt
 */
public class GenericExcelDataType implements AbstractGradeableDataType {

    @Override
    public RecordExtractor createExtractor() {
        return new RecordExtractor();
    }

    @Override
    public ExtractedSpreadsheetContents createExtractionHolder(String fileName) {
        return new ExtractedSpreadsheetContents(fileName);
    }

    @Override
    public String getName() {
        return "Basic Excel";
    }

    @Override
    public String getDescription() {
        return "Any Excel file with headers in the first row";
    }
}
