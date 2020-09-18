package autocadDrawingChecker.start;

import autocadDrawingChecker.grading.AbstractGradingCriteria;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

/**
 * DrawingCheckerProperties is used to store application data.
 * This will allow the program to load data in a variety of ways:
 * <ul>
 * <li>Through the GUI</li>
 * <li>From a property file (not implemented yet)</li>
 * <li>From the command line (not implemented yet)</li>
 * </ul>
 * 
 * Not sure if I need to save this as a Properties subclass.
 * 
 * @author Matt Crow
 */
public class DrawingCheckerProperties extends Properties {
    private static final String SRC_KEY = "srcPath";
    private static final String CMP_KEY = "cmpPath";
    private static final String CRIT_KEY = "critKey";
    
    public static final String DEFAULT_SRC = "SRC_PATH_NOT_SET";
    public static final String DEFAULT_CMP = "CMP_PATH_NOT_SET";
    public static final String DEFAULT_CRITERIA = "CRIT_NOT_SET";
    
    private String instructorFilePath;
    private String[] studentFilePaths;
    private final HashMap<String, Boolean> selectedCriteria;
    private final HashMap<String, AbstractGradingCriteria> nameToCriteria;
    
    public DrawingCheckerProperties(){
        super();
        
        instructorFilePath = DEFAULT_SRC;
        studentFilePaths = new String[0];
        selectedCriteria = new HashMap<>();
        nameToCriteria = new HashMap<>();
        
        setProperty(SRC_KEY, DEFAULT_SRC);
        setProperty(CMP_KEY, DEFAULT_CMP);
        setProperty(CRIT_KEY, DEFAULT_CRITERIA);
    }
    
    /**
     * 
     * @return whether or not an instructor
     * file has been selected.
     */
    public final boolean isSrcSet(){
        return !DEFAULT_SRC.equals(getProperty(SRC_KEY));
    }
    
    /**
     * 
     * @return whether or not student
     * files to grade have been selected.
     */
    public final boolean isCmpSet(){
        return !DEFAULT_CMP.equals(getProperty(CMP_KEY));
    }
    
    /**
     * 
     * @return whether or not any grading
     * criteria have been selected.
     */
    public final boolean isCritSet(){
        return !DEFAULT_CRITERIA.equals(getProperty(CRIT_KEY));
    }
    
    public final String getSrcPath(){
        return (isSrcSet()) ? getProperty(SRC_KEY) : null;
    }
    
    public final String[] getCmpPaths(){
        return (isCmpSet()) ? getProperty(CMP_KEY).split(",") : null;
    }
    
    public final List<AbstractGradingCriteria> getCriteria(){
        List<AbstractGradingCriteria> ret = null;
        if(isCritSet()){
            String[] critNames = getProperty(CRIT_KEY).split(",");
            throw new UnsupportedOperationException("Can I store a list of strings as properties?");
        }
        return ret;
    }
    
    public final DrawingCheckerProperties setSrcPath(String path){
        setProperty(SRC_KEY, path);
        return this;
    }
    
    public final DrawingCheckerProperties setCmpPaths(String[] paths){
        setProperty(CMP_KEY, String.join(",", paths));
        return this;
    }
    
    public final DrawingCheckerProperties setCriteria(String[] critNames){
        throw new UnsupportedOperationException();
        //return this;
    }
}
