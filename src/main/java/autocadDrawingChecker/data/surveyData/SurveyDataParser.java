package autocadDrawingChecker.data.surveyData;

import autocadDrawingChecker.data.core.DataSet;
import autocadDrawingChecker.data.core.ExcelParser;
import autocadDrawingChecker.data.core.RecordExtractor;
import java.util.HashMap;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

/**
 *
 * @author Matt
 */
public class SurveyDataParser extends ExcelParser {

    public SurveyDataParser(String fileName) {
        super(fileName);
    }
    
    @Override
    protected DataSet createExtractionHolder(String sheetName){
        return new SurveyDataSet(this.getFileName() + " - " + sheetName);
    }
    
    @Override
    protected RecordExtractor createExtractor(HashMap<String, Integer> columns){
        return new SurveyDataExtractor(columns);
    }
    
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
}
