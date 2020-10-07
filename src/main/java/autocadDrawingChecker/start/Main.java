package autocadDrawingChecker.start;

import org.apache.commons.cli.DefaultParser;
import autocadDrawingChecker.grading.criteria.GradingCriteriaLoader;
import autocadDrawingChecker.logging.Logger;
import java.util.Arrays;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.ParseException;

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
        
        System.out.println("Args are " + Arrays.toString(args));
        
        
        Options cmdLineOpts = new Options();
        Option instrFilePath = new Option("src", "instructor-file", false, "Instructor file path");
        cmdLineOpts.addOption(instrFilePath);
        
        try {
            CommandLine line = new DefaultParser().parse(cmdLineOpts, args);
            String srcPath = line.getOptionValue("instructor-file");
            if(srcPath != null){
                main.app.getData().setInstructorFilePath(srcPath);
                System.out.println(srcPath);
            }
        } catch (ParseException ex) {
            Logger.logError(ex);
        }
        
        main.app.setLoadedCriteria(new GradingCriteriaLoader().getAll());
        
        main.app.getData()
            //.setInstructorFilePath("C:\\Users\\Matt\\Desktop\\AutoCAD Drawing Checker\\sample files to work with\\sample\\Check Sample - Master File.xls.xlsx")
            .setStudentFilePaths("C:\\Users\\Matt\\Desktop\\AutoCAD Drawing Checker\\sample files to work with\\sample");
        /*System.out.println(app.grade().toString());*/
        main.app.createGui();
    }
}
