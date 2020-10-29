package autocadDrawingChecker.start;

import autocadDrawingChecker.data.GradableDataTypeLoader;
import autocadDrawingChecker.data.surveyData.SurveyDataParser;
import autocadDrawingChecker.grading.criteria.GradingCriteriaLoader;
import java.io.IOException;
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
        new GradableDataTypeLoader().getAll().forEach(data::addGradableDataType);
        
        data.setSelectedDataType(data.getGradableDataTypes().get(1));
        
        System.out.println("Args are " + Arrays.toString(args));
        
        HashSet<String> argSet = new HashSet<>();
        for(String arg : args){
            argSet.add(arg.toLowerCase());
        }
        boolean debug = argSet.contains("--debug");
        boolean noGui = argSet.contains("--no-gui");
        
        if(debug){
            try {
                new SurveyDataParser("C:\\Users\\Matt\\Desktop\\AutoCAD Drawing Checker\\sample files to work with\\Survey Data\\template\\GPS Style - Survey Field Notes - Template.xlsx").parseAllSheets().forEach(System.out::println);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            
            data
            .setInstructorFilePath("C:\\Users\\Matt\\Desktop\\AutoCAD Drawing Checker\\sample files to work with\\Survey Data\\civil67\\67Civi-Student 1 - GPS Style Survey Simulation - Survey Field Notes.xlsx")
              .setStudentFilePaths("C:\\Users\\Matt\\Desktop\\AutoCAD Drawing Checker\\sample files to work with\\Survey Data\\civil67");
            for(double d = 0.0; d < 1000.0; d += 100.0){
                data.setCriteriaThreshold(d);
                System.out.printf("\nWhen threshold is %f...\n", d);
                data.grade().forEach((gradedExport)->{
                    System.out.printf("%5.3f ", gradedExport.getFinalGrade());
                });
            }
        } 
        
        if(!noGui){
            main.app.createGui();
        }
    }
}
