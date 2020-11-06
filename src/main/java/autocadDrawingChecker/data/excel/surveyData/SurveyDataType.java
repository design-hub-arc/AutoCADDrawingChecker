package autocadDrawingChecker.data.excel.surveyData;

import autocadDrawingChecker.data.AbstractGradableDataType;
import autocadDrawingChecker.data.core.AbstractTableParser;
import autocadDrawingChecker.util.FileType;

/**
 *
 * @author Matt
 */
public class SurveyDataType implements AbstractGradableDataType {
    @Override
    public String getName() {
        return "Survey Data";
    }

    @Override
    public String getDescription() {
        return "Survey data, with headers in the 8th row";
    }

    @Override
    public AbstractTableParser createParser() {
        return new SurveyDataParser();
    }

    @Override
    public FileType getRequiredFileType() {
        return FileType.EXCEL;
    }
}
