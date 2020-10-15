package autocadDrawingChecker.data.surveyData;

import autocadDrawingChecker.data.core.Record;
import autocadDrawingChecker.data.core.RecordExtractor;

/**
 *
 * @author Matt
 */
public class SurveyDataExtractor extends RecordExtractor {
    @Override
    protected Record createNew(){
        return new SurveyDataRecord();
    }
}
