package autocadDrawingChecker.start;

import autocadDrawingChecker.data.GradeableDataTypeLoader;
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
        DrawingCheckerData data = main.app.getData();
        new GradingCriteriaLoader().getAll().forEach(data::addCriteria);
        new GradeableDataTypeLoader().getAll().forEach(data::addGradeableDataType);
        
        data.setSelectedDataType(data.getGradeableDataTypes().get(1));
        
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
            System.out.println(data.grade().toString());
        } else {
            main.app.createGui();
        }
    }
}
