package autocadDrawingChecker.data.surveyData;

import autocadDrawingChecker.data.AbstractGradeableDataType;
import autocadDrawingChecker.data.core.DataSet;
import autocadDrawingChecker.data.core.RecordExtractor;
import java.io.IOException;
import java.util.HashMap;

/**
 *
 * @author Matt
 */
public class SurveyDataType implements AbstractGradeableDataType {

    @Override
    public RecordExtractor createExtractor(HashMap<String, Integer> columns) {
        return new SurveyDataExtractor(columns);
    }

    @Override
    public DataSet createExtractionHolder(String fileName) {
        return new SurveyDataSet(fileName);
    }

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
        return new SurveyDataParser(fileName).parse();
    }
}
