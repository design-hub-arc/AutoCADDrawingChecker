package autocadDrawingChecker.data.surveyData;

import autocadDrawingChecker.data.AbstractGradeableDataType;
import autocadDrawingChecker.data.core.DataSet;
import autocadDrawingChecker.data.core.RecordExtractor;
import java.io.IOException;

/**
 *
 * @author Matt
 */
public class SurveyDataType implements AbstractGradeableDataType {

    @Override
    public RecordExtractor createExtractor() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DataSet createExtractionHolder(String fileName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
