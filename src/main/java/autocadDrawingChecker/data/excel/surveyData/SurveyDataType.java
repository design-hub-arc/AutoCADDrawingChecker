package autocadDrawingChecker.data.excel.surveyData;

import autocadDrawingChecker.data.AbstractGradableDataType;
import autocadDrawingChecker.data.excel.ExcelParser;

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
    public ExcelParser createParser(String fileName) {
        return new SurveyDataParser(fileName);
    }
}
