package autocadDrawingChecker.data.excel.autoCADData;

import autocadDrawingChecker.data.excel.ExcelParser;
import autocadDrawingChecker.data.core.DataSet;
import autocadDrawingChecker.data.excel.ExcelRecordConverter;
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
     * @param fileToParse a path to the Excel file this should parseFirstSheet.
     */
    public AutoCADExcelParser(String fileToParse){
        super(fileToParse);
    }
    
    @Override
    protected DataSet createExtractionHolder(String sheetName){
        return new AutoCADExport(this.getFileName() + " - " + sheetName);
    }
    
    @Override
    protected ExcelRecordConverter createExtractor(HashMap<String, Integer> columns){
        return new AutoCADElementConverter(columns);
    }
}
