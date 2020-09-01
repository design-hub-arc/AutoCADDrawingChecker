package autocadDrawingChecker.comparison;

import autocadDrawingChecker.autocadData.AutoCADExport;

/**
 *
 * @author Matt
 */
public abstract class AbstractGradingCriteria {
    private final String dispText;
    
    public AbstractGradingCriteria(String displayText){
        dispText = displayText;
    }
    
    public final String getDisplayText(){
        return dispText;
    }
    
    public abstract double computeScore(AutoCADExport exp1, AutoCADExport exp2);
}
