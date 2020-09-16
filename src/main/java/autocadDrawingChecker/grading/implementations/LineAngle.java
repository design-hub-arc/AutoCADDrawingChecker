package autocadDrawingChecker.grading.implementations;

import autocadDrawingChecker.autocadData.AutoCADLine;
import autocadDrawingChecker.autocadData.AutoCADElement;
import autocadDrawingChecker.grading.AbstractElementCriteria;
import autocadDrawingChecker.grading.MathUtil;

/**
 * @author Matt Crow
 */
public class LineAngle implements AbstractElementCriteria {
    
    @Override
    public double getMatchScore(AutoCADElement r1, AutoCADElement r2){
        double score = 0.0;
        double percErr = 0.0;
        double percErrRot = 0.0;
        if(r1 instanceof AutoCADLine && r2 instanceof AutoCADLine){
            percErr = MathUtil.percentError(
                ((AutoCADLine)r1).getAngle(),
                ((AutoCADLine)r2).getAngle()
            );
            percErrRot = MathUtil.percentError(
                ((AutoCADLine)r1).getAngle(),
                MathUtil.rotate180(((AutoCADLine)r2).getAngle())
            );
            score = 1.0 - Math.min(percErr, percErrRot); // check if they just got the line reversed
        }
        return score;
    }
    
    @Override
    public String getDescription() {
        return "Grades the students based on how their lines' angles match up with those of the instructor file";
    }

    @Override
    public String getName() {
        return "Line Angle";
    }

}
