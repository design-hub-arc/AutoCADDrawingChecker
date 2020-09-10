package autocadDrawingChecker.start;

import autocadDrawingChecker.autocadData.AutoCADExcelParser;
import autocadDrawingChecker.autocadData.AutoCADExport;
import autocadDrawingChecker.grading.GradingReport;
import autocadDrawingChecker.gui.ViewController;
import autocadDrawingChecker.logging.Logger;
import java.io.IOException;

/**
 * Main servers as the starting point for the
 * application. Future versions may add support
 * for running the application from the command
 * line, with no GUI.
 * 
 * @author Matt Crow
 */
public class Main {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Application app = Application.getInstance();
        /*
        GradingReport report = app
            .setSrcPath("C:\\Users\\Matt\\Documents\\GitHub\\AutoCADDrawingChecker\\src\\main\\resources\\exports\\simpleTranslate1.xlsx")
            .setCmpPaths("C:\\Users\\Matt\\Documents\\GitHub\\AutoCADDrawingChecker\\src\\main\\resources\\exports\\simpleTranslate2.xlsx")
            .grade();
        Logger.log(report.toString());*/
        app.createGui();
    }
}
