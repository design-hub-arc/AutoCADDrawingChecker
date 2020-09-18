package autocadDrawingChecker.start;

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
        app.getData()
            .setInstructorFilePath("C:\\Users\\Matt\\Desktop\\AutoCAD Drawing Checker\\sample files to work with\\Check Sample - Master File.xls.xlsx")
            .setStudentFilePaths("C:\\Users\\Matt\\Desktop\\AutoCAD Drawing Checker\\sample files to work with")
            .setCriteriaSelected(new CheckDims(), false);
        */
        app.createGui();
    }
}
