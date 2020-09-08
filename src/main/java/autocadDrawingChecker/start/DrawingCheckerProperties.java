package autocadDrawingChecker.start;

import autocadDrawingChecker.grading.AbstractGradingCriteria;
import java.util.List;
import java.util.Properties;

/**
 * oh wait, where would I save this?
 * 
 * Not done
 * 
 * @author Matt
 */
public class DrawingCheckerProperties extends Properties {
    private static final String SRC_KEY = "srcPath";
    private static final String CMP_KEY = "cmpPath";
    private static final String CRIT_KEY = "critKey";
    
    public static final String DEFAULT_SRC = "SRC_PATH_NOT_SET";
    public static final String DEFAULT_CMP = "CMP_PATH_NOT_SET";
    public static final String DEFAULT_CRITERIA = "CRIT_NOT_SET";
    
    public DrawingCheckerProperties(){
        super();
        setProperty(SRC_KEY, DEFAULT_SRC);
        setProperty(CMP_KEY, DEFAULT_CMP);
        setProperty(CRIT_KEY, DEFAULT_CRITERIA);
    }
    
    public final boolean isSrcSet(){
        return !DEFAULT_SRC.equals(getProperty(SRC_KEY));
    }
    public final boolean isCmpSet(){
        return !DEFAULT_CMP.equals(getProperty(CMP_KEY));
    }
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
}
