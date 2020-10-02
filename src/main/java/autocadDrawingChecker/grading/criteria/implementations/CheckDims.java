package autocadDrawingChecker.grading.criteria.implementations;

import autocadDrawingChecker.data.AutoCADElement;
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
        return (d1.getAttribute("dim style").equals(d2.getAttribute("dim style"))) ? 1.0 : 0.0;
    }
    
    @Override
    public String getDescription() {
        return "Checks the dim style of a student export against those of the instructor";
    }

    @Override
    public String[] getAllowedTypes() {
        return new String[]{"Diametric Dimension", "Rotated Dimension"};
    }

}
