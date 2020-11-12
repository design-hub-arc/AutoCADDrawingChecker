package autocadDrawingChecker.start;

import autocadDrawingChecker.data.GradableDataTypeLoader;
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
     * 
     * To pass arguments to Gradle, use
     * 
     * gradle run --args="args go in here"
     */
    public static void main(String[] args) {
        Main main = new Main();
        DrawingCheckerData data = main.app.getData();
        new GradingCriteriaLoader().getAll().forEach(data::addCriteria);
        new GradableDataTypeLoader().getAll().forEach(data::addGradableDataType);
        
        System.out.println("Args are " + Arrays.toString(args));
        
        HashSet<String> argSet = new HashSet<>();
        for(String arg : args){
            argSet.add(arg.toLowerCase());
        }
        boolean debug = argSet.contains("--debug");
        boolean noGui = argSet.contains("--no-gui");
        
        if(debug){
            // insert your debug code here!
        } 
        
        if(!noGui){
            main.app.createGui();
        }
    }
}
