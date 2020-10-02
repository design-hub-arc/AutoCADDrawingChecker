package autocadDrawingChecker.start;

import autocadDrawingChecker.grading.criteria.GradingCriteriaLoader;

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
        app.setLoadedCriteria(new GradingCriteriaLoader().getAll());
        /*
        app.getData()
            .setInstructorFilePath("C:\\Users\\Matt\\Desktop\\AutoCAD Drawing Checker\\sample files to work with\\sample\\Check Sample - Master File.xls.xlsx")
            .setStudentFilePaths("C:\\Users\\Matt\\Desktop\\AutoCAD Drawing Checker\\sample files to work with\\sample");
        System.out.println(app.grade().toString());*/
        app.createGui();
    }
}
