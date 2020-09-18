package autocadDrawingChecker.start;

import autocadDrawingChecker.grading.GradingReport;
import autocadDrawingChecker.grading.implementations.CheckDims;
import autocadDrawingChecker.logging.Logger;

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
        
        //GradingReport report = 
        app.getData()
            .setInstructorFilePath("C:\\Users\\Matt\\Desktop\\AutoCAD Drawing Checker\\sample files to work with\\Check Sample - Master File.xls.xlsx")
            .setStudentFilePaths("C:\\Users\\Matt\\Desktop\\AutoCAD Drawing Checker\\sample files to work with")
            .setCriteriaSelected(new CheckDims(), false);
            //.setCmpPaths("C:\\Users\\Matt\\Desktop\\AutoCAD Drawing Checker\\sample files to work with\\Check Sample - Master File.xls.xlsx")
            //.setCmpPaths("C:\\Users\\Matt\\Desktop\\AutoCAD Drawing Checker\\sample files to work with\\Master File.xls - angle180.xlsx")
            //.grade();
        //Logger.log(report.toString());
        app.createGui();
    }
}
