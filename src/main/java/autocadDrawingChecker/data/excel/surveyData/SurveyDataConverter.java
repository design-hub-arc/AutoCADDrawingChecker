package autocadDrawingChecker.data.excel.surveyData;

import autocadDrawingChecker.data.core.Record;
import autocadDrawingChecker.data.excel.ExcelRecordConverter;
import java.util.HashMap;

/**
 *
 * @author Matt
 */
public class SurveyDataConverter extends ExcelRecordConverter {

    public SurveyDataConverter(HashMap<String, Integer> columns) {
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
