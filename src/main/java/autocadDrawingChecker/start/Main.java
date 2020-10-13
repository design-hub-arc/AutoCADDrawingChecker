package autocadDrawingChecker.start;

import autocadDrawingChecker.grading.criteria.GradingCriteriaLoader;
import java.util.Arrays;
import java.util.HashSet;

/**
 * Main serves as the starting point for the
 * application. Future versions may add support
 * for running the application from the command
 * line, with no GUI.
 * 
 * @author Matt Crow
 */
public class Main {
    private final Application app;
    private Main(){
        app = Application.getInstance();
    }
    
    /**
     * @param args the command line arguments.
     * How do I set these in Netbeans?
     * 
     * To pass arguments to Gradle, use
     * 
     * gradle run --args="args go in here"
     */
    public static void main(String[] args) {
        Main main = new Main();
        main.app.setLoadedCriteria(new GradingCriteriaLoader().getAll());
        
        
        
        System.out.println("Args are " + Arrays.toString(args));
        
        HashSet<String> argSet = new HashSet<>();
        for(String arg : args){
            argSet.add(arg.toLowerCase());
        }
        boolean debug = argSet.contains("--debug");
        
        if(debug){
            main.app.getData()
            .setInstructorFilePath("C:\\Users\\Matt\\Desktop\\AutoCAD Drawing Checker\\sample files to work with\\sample\\Check Sample - Master File.xls.xlsx")
            .setStudentFilePaths("C:\\Users\\Matt\\Desktop\\AutoCAD Drawing Checker\\sample files to work with\\sample");
            System.out.println(main.app.gradeAutoCAD().toString());
        } else {
            main.app.createGui();
        }
    }
}
