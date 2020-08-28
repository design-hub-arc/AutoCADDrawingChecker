package autocadDrawingChecker.start;

import java.io.IOException;
import java.io.InputStream;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.extractor.XSSFExcelExtractor;

public class Main {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        InputStream in = Main.class.getResourceAsStream("/testWorkbook.xlsx");
        System.out.println(in);
        XSSFWorkbook workbook = new XSSFWorkbook(in);
        
        XSSFExcelExtractor reader = new XSSFExcelExtractor(workbook);
        System.out.println("Application runs properly");
    }
}
