package autocadDrawingChecker.start;

import autocadDrawingChecker.grading.AbstractGradingCriteria;
import autocadDrawingChecker.grading.Grader;
import autocadDrawingChecker.grading.GradingReport;
import java.util.ArrayList;

/**
 *
 * @author Matt
 */
public class Application {
    private String srcPath;
    private String[] cmpPaths;
    private ArrayList<AbstractGradingCriteria> criteria;
    
    public Application(){
        srcPath = null;
        cmpPaths = new String[0];
        criteria = new ArrayList<>();
    }
    
    public final Application setSrcPath(String path){
        srcPath = path;
        return this;
    }
    
    public final Application setCmpPaths(String... paths){
        cmpPaths = paths;
        return this;
    }
    
    public final Application setCriteria(AbstractGradingCriteria... newCriteria){
        criteria.clear();
        for(AbstractGradingCriteria c : newCriteria){
            criteria.add(c);
        }
        return this;
    }
    
    public final boolean isReadyToGrade(){
        return 
            srcPath != null 
            && cmpPaths != null 
            && cmpPaths.length > 0 
            && !criteria.isEmpty()
            ;
    }
    
    public final GradingReport grade(){
        Grader g = new Grader(
            srcPath,
            cmpPaths,
            criteria
        );
        
        return g.grade();
    }
}
