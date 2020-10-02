package autocadDrawingChecker.grading.criteria.implementations;

import autocadDrawingChecker.data.elements.AutoCADElement;
import autocadDrawingChecker.grading.criteria.AbstractElementCriteria;

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
    public double getMatchScore(AutoCADElement d1, AutoCADElement d2){
        double ret = 0.0;
        
        String[] attrs = new String[]{
            "DynamicDimension",
            "Dim Style",
            "TextDefinedSize"
        };
        for(String attr : attrs){
            ret += (d1.getAttribute(attr).equals(d2.getAttribute(attr))) ? 1.0 : 0.0;
        }
        // take the average
        ret /= attrs.length;
        
        return ret;
    }
    
    @Override
    public String getDescription() {
        return "Checks the dimensions of a student export against those of the instructor";
    }

    @Override
    public String[] getAllowedTypes() {
        return new String[]{"Diametric Dimension", "Rotated Dimension"};
    }

}
