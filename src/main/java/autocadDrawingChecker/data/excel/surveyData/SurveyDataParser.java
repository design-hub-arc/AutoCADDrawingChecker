package autocadDrawingChecker.data.excel.surveyData;

import autocadDrawingChecker.data.core.DataSet;
import autocadDrawingChecker.data.core.Record;
import autocadDrawingChecker.data.excel.ExcelParser;
import autocadDrawingChecker.data.excel.ExcelRecordConverter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

/**
 *
 * @author Matt
 */
public class SurveyDataParser extends ExcelParser {
    
    @Override
    protected DataSet createExtractionHolder(String name){
        return new SurveyDataSet(name);
    }
    
    /*
    @Override
    protected ExcelRecordConverter createExtractor(Map<String, Integer> columns){
        return new SurveyDataConverter(columns);
    }*/
    
    @Override
    protected Row locateHeaderRow(Sheet sheet){
        Iterator<Row> rows = sheet.rowIterator();
        Row headers = null;
        Row currRow = null;
        while(rows.hasNext() && headers == null){
            currRow = rows.next();
            if(currRow.getCell(0) != null && currRow.getCell(0).getStringCellValue().equalsIgnoreCase("STA")){
                headers = currRow;
            }
        }
        return headers;
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
