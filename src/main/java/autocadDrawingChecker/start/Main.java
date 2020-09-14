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
        
        GradingReport report = app
            .setSrcPath("C:\\Users\\Matt\\Desktop\\AutoCAD Drawing Checker\\sample files to work with\\Check Sample - Master File.xls.xlsx")
            .setCmpPaths("C:\\Users\\Matt\\Desktop\\AutoCAD Drawing Checker\\sample files to work with")
            .grade();
        Logger.log(report.toString());
        //app.createGui();
    }
}
