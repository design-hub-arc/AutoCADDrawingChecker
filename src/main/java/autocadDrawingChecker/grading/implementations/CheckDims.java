package autocadDrawingChecker.grading.implementations;

import autocadDrawingChecker.autocadData.AutoCADDimension;
import autocadDrawingChecker.autocadData.AutoCADElement;
import autocadDrawingChecker.grading.AbstractElementCriteria;
import autocadDrawingChecker.grading.MathUtil;

/**
 *
 * @author Matt
 */
public class CheckDims implements AbstractElementCriteria {

    @Override
    public String getName() {
        return "Check Dimensions";
    }
    
    @Override
    public double getMatchScore(AutoCADElement row1, AutoCADElement row2){
        double ret = 0.0;
        if(row1 instanceof AutoCADDimension && row2 instanceof AutoCADDimension){
            AutoCADDimension d1 = (AutoCADDimension)row1;
            AutoCADDimension d2 = (AutoCADDimension)row2;
            // grade on 3 things...
            ret += 1.0 - MathUtil.percentError(d1.getDynamicDimension(), d2.getDynamicDimension());
            ret += (d1.getDimensionStyle().equals(d2.getDimensionStyle())) ? 1.0 : 0.0;
            ret += (d1.getTextDefinedSize().equals(d2.getTextDefinedSize())) ? 1.0 : 0.0;
            // ... so take the average
            ret /= 3.0;
        }
        return ret;
    }
    
    @Override
    public String getDescription() {
        return "Checks the dimensions of a student export against those of the instructor";
    }

}
