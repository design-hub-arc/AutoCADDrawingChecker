package autocadDrawingChecker.data.surveyData;

import autocadDrawingChecker.data.AbstractGradeableDataType;
import autocadDrawingChecker.data.core.ExtractedSpreadsheetContents;
import autocadDrawingChecker.data.core.RecordExtractor;

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
    public ExtractedSpreadsheetContents createExtractionHolder(String fileName) {
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

}
