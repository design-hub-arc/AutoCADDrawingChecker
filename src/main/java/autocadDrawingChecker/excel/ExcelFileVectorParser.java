package autocadDrawingChecker.excel;

import java.io.IOException;
import java.io.InputStream;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.extractor.XSSFExcelExtractor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Matt
 */
public class ExcelFileVectorParser {
    
    public static void parse(InputStream in) throws IOException{
        XSSFWorkbook workbook = new XSSFWorkbook(in);
        workbook.getSheetAt(0).getRow(0).forEach((Cell c)->{
            System.out.println(c.getStringCellValue());
        });
        XSSFExcelExtractor reader = new XSSFExcelExtractor(workbook);
        
    }
}
