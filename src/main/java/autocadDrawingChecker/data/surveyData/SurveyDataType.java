package autocadDrawingChecker.data.surveyData;

import autocadDrawingChecker.data.AbstractGradableDataType;
import autocadDrawingChecker.data.core.DataSet;
import autocadDrawingChecker.data.core.RecordExtractor;
import java.io.IOException;
import java.util.HashMap;

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
    public DataSet parseFile(String fileName) throws IOException {
        return new SurveyDataParser(fileName).parseFirstSheet();
    }
}
