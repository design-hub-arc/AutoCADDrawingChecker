package autocadDrawingChecker.grading.criteria.implementations;

import autocadDrawingChecker.data.autoCADData.AutoCADElement;
import autocadDrawingChecker.grading.criteria.AbstractElementCriteria;
import autocadDrawingChecker.grading.MathUtil;

/**
 * @author Matt Crow
 */
public class LineAngle implements AbstractElementCriteria {
    /**
     * 
     * @param r1 a line in the instructor's file
     * @param r2 a line in the student's file
     * @return 1.0 if r2 is parallel to r1, else 0.0  
     */
    @Override
    public double getMatchScore(AutoCADElement r1, AutoCADElement r2){
        int r1Angle = r1.getAttributeInt("angle");
        int r2Angle = r2.getAttributeInt("angle");
        
        return (r1Angle == r2Angle || r1Angle == MathUtil.rotate180(r2Angle)) ? 1.0 : 0.0; 
        //                                       check if they got a line reversed
    }
    
    @Override
    public String getDescription() {
        return "Grades the students based on how their lines' angles match up with those of the instructor file";
    }

    @Override
    public String getName() {
        return "Line Angle";
    }

    @Override
    public String[] getAllowedTypes() {
        return new String[]{"Line"};
        
    }

}
