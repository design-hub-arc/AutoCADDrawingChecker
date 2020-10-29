package autocadDrawingChecker.data.surveyData;

import autocadDrawingChecker.data.core.Record;
import autocadDrawingChecker.data.core.ExcelRecordExtractor;
import java.util.HashMap;

/**
 *
 * @author Matt
 */
public class SurveyDataExtractor extends ExcelRecordExtractor {

    public SurveyDataExtractor(HashMap<String, Integer> columns) {
        super(columns);
    }
    @Override
    protected String[] getRequiredColumns(){
        return SurveyDataRecord.REQ_COLS;
    }
    
    @Override
    protected Record createNew(){
        return new SurveyDataRecord();
    }
}
