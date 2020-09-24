package autocadDrawingChecker.start;

import autocadDrawingChecker.autocadData.extractors.ExtractorLoader;
import autocadDrawingChecker.grading.GradingCriteriaLoader;

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
        app.setExtractors(new ExtractorLoader().getAll());
        app.setLoadedCriteria(new GradingCriteriaLoader().getAll());
        
        app.getData()
            .setInstructorFilePath("C:\\Users\\Matt\\Desktop\\AutoCAD Drawing Checker\\sample files to work with\\Acceptance Test Drawing 1\\Better Data Extracts\\instructor file.xls")
            .setStudentFilePaths("C:\\Users\\Matt\\Desktop\\AutoCAD Drawing Checker\\sample files to work with\\Acceptance Test Drawing 1\\Better Data Extracts");
        System.out.println(app.grade().toString());
        //app.createGui();
    }
}
