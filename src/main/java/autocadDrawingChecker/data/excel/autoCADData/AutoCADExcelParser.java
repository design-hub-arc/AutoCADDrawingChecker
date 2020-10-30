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
    
    @Override
    protected DataSet createExtractionHolder(String name){
        return new AutoCADExport(name);
    }
    
    @Override
    protected ExcelRecordConverter createExtractor(HashMap<String, Integer> columns){
        return new AutoCADElementConverter(columns);
    }
}
