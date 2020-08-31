package autocadDrawingChecker.autocadData;

import autocadDrawingChecker.autocadData.AutoCADExportVector;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Matt
 */
public class AutoCADExcelParser {
    
    public static List<AutoCADExportVector> parse(InputStream in) throws IOException{
        ArrayList<AutoCADExportVector> vectors = new ArrayList<>();
        HashMap<String, Integer> headerToCol = new HashMap<>();
        XSSFWorkbook workbook = new XSSFWorkbook(in);
        XSSFSheet sheet = workbook.getSheetAt(0);
        sheet.getRow(0).forEach((Cell c)->{
            System.out.println(c.getStringCellValue());
            if(c.getStringCellValue().toLowerCase().equals("layer")){
                headerToCol.put("layer", c.getColumnIndex());
            }
        });
        
        sheet.forEach((row)->{
            if(row.getRowNum() != 0 && row.getLastCellNum() > 0){ // skip header and empty rows
                vectors.add(
                    new AutoCADExportVector(
                        row.getCell(
                            headerToCol.get("layer")
                        ).getStringCellValue()
                    )
                );
            }
        });
        
        vectors.forEach(System.out::println);
        
        return vectors;
    }
}
