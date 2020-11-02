package autocadDrawingChecker.data.excel.surveyData;

import autocadDrawingChecker.data.core.Record;
import autocadDrawingChecker.data.excel.ExcelRecordConverter;
import java.util.Map;

/**
 *
 * @author Matt
 */
public class SurveyDataConverter extends ExcelRecordConverter {

    public SurveyDataConverter(Map<String, Integer> columns) {
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
