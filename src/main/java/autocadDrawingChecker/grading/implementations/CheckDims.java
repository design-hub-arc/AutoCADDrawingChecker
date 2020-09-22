package autocadDrawingChecker.grading.implementations;

import autocadDrawingChecker.autocadData.elements.AutoCADDimension;
import autocadDrawingChecker.autocadData.elements.AutoCADElement;
import autocadDrawingChecker.grading.AbstractElementCriteria;
import autocadDrawingChecker.grading.MathUtil;

/**
 *
 * @author Matt
 */
public class CheckDims implements AbstractElementCriteria<AutoCADDimension> {

    @Override
    public String getName() {
        return "Check Dimensions";
    }
    
    @Override
    public double getMatchScore(AutoCADDimension d1, AutoCADDimension d2){
        double ret = 0.0;
        
        // grade on 3 things...
        ret += (d1.getDynamicDimension() == d2.getDynamicDimension()) ? 1.0 : 0.0; //1.0 - MathUtil.percentError(d1.getDynamicDimension(), d2.getDynamicDimension());
        ret += (d1.getDimensionStyle().equals(d2.getDimensionStyle())) ? 1.0 : 0.0;
        ret += (d1.getTextDefinedSize().equals(d2.getTextDefinedSize())) ? 1.0 : 0.0;
        // ... so take the average
        ret /= 3.0;
        
        return ret;
    }
    
    @Override
    public String getDescription() {
        return "Checks the dimensions of a student export against those of the instructor";
    }

    @Override
    public AutoCADDimension cast(AutoCADElement e) {
        return (e instanceof AutoCADDimension) ? (AutoCADDimension)e : null;
    }

}
