package autocadDrawingChecker.start;

import autocadDrawingChecker.excel.ExcelFileVectorParser;
import autocadDrawingChecker.gui.AppWindow;
import java.io.IOException;
import java.io.InputStream;

public class Main {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        new AppWindow();
        /*
        InputStream in = Main.class.getResourceAsStream("/testWorkbook.xlsx");
        if(in == null){
            System.err.println("Can't find test workbook!");
        } else {
            ExcelFileVectorParser.parse(in);
        }
        */
    }
}
