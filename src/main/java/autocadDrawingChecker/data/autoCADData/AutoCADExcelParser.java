package autocadDrawingChecker.data.autoCADData;

import autocadDrawingChecker.data.core.ExcelParser;
import autocadDrawingChecker.data.core.DataSet;
import autocadDrawingChecker.data.core.RecordExtractor;
import java.io.IOException;

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
    protected RecordExtractor createExtractor(){
        return new AutoCADElementExtractor();
    }
    
    /**
     * Reads the Excel file with the
     * given complete file path, and
     * returns its contents as an AutoCADExport.
     * 
     * Later versions may extract multiple Exports based on column values,
     * or this functionality may be deferred to AutoCADExport.
     * 
     * @param fileName the complete path to an Excel file.
     * @return the contents of the first sheet of the given Excel file , as 
     * an AutoCADExport.
     * @throws IOException if the fileName given does not point to an Excel file
     */
    public static AutoCADExport parse(String fileName) throws IOException{
        return (AutoCADExport)new AutoCADExcelParser(
            fileName
        ).parse();
    }
}
