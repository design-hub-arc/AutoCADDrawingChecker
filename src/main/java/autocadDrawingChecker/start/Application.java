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
    private final ArrayList<AbstractGradingCriteria> criteria;
    
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
    
    public final boolean isSrcPathSet(){
        return srcPath != null;
    }
    
    public final boolean isCmpPathsSet(){
        return cmpPaths != null && cmpPaths.length > 0;
    }
    
    public final boolean isCriteriaSet(){
        return !criteria.isEmpty();
    }
    
    public final String getSrcPath(){
        return srcPath;
    }
    
    public final String[] getCmpPaths(){
        return cmpPaths;
    }
    
    public final AbstractGradingCriteria[] getCriteria(){
        return criteria.toArray(new AbstractGradingCriteria[criteria.size()]);
    }
    
    public final boolean isReadyToGrade(){
        return isSrcPathSet() && isCmpPathsSet() && isCriteriaSet();
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
