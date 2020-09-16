package autocadDrawingChecker.grading.implementations;

import autocadDrawingChecker.autocadData.AutoCADLine;
import autocadDrawingChecker.autocadData.AutoCADElement;
import autocadDrawingChecker.grading.AbstractElementCriteria;
import autocadDrawingChecker.grading.MathUtil;

/**
 * @author Matt Crow
 */
public class LineAngle implements AbstractElementCriteria<AutoCADLine> {
    
    @Override
    public double getMatchScore(AutoCADLine r1, AutoCADLine r2){
        double score = 0.0;
        double percErr = MathUtil.percentError(
            ((AutoCADLine)r1).getAngle(),
            ((AutoCADLine)r2).getAngle()
        );;
        double percErrRot = MathUtil.percentError(
            ((AutoCADLine)r1).getAngle(),
            MathUtil.rotate180(((AutoCADLine)r2).getAngle())
        );
        score = 1.0 - Math.min(percErr, percErrRot); // check if they just got the line reversed
        
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

    @Override
    public AutoCADLine cast(AutoCADElement e) {
        return (e instanceof AutoCADLine) ? (AutoCADLine)e : null;
    }

}
