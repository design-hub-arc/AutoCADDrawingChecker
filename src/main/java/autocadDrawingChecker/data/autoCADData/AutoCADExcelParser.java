package autocadDrawingChecker.data.autoCADData;

import autocadDrawingChecker.data.core.ExcelParser;
import autocadDrawingChecker.data.core.DataSet;
import autocadDrawingChecker.data.core.RecordExtractor;
import java.util.HashMap;

/**
 * The AutoCADExcelParser is used to 
 * read an Excel spreadsheet,
 * extracting the AutoCAD date within as an
 * AutoCADExport object.
 * 
 * @author Matt Crow
 */
public class AutoCADExcelParser extends ExcelParser {
    
    
    /**
     * @param fileToParse a path to the Excel file this should parse.
     */
    public AutoCADExcelParser(String fileToParse){
        super(fileToParse);
    }
    
    @Override
    protected DataSet createExtractionHolder(){
        return new AutoCADExport(this.getFileName());
    }
    
    @Override
    protected RecordExtractor createExtractor(HashMap<String, Integer> columns){
        return new AutoCADElementExtractor(columns);
    }
}
