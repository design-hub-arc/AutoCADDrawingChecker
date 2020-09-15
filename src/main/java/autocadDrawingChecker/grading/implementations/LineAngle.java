package autocadDrawingChecker.grading.implementations;

import autocadDrawingChecker.autocadData.AutoCADExport;
import autocadDrawingChecker.autocadData.AutoCADLine;
import autocadDrawingChecker.autocadData.AutoCADElement;
import autocadDrawingChecker.autocadData.AutoCADElementMatcher;
import autocadDrawingChecker.autocadData.MatchingAutoCADElements;
import autocadDrawingChecker.grading.AbstractGradingCriteria;
import autocadDrawingChecker.grading.MathUtil;
import java.util.List;

/**
 * @author Matt Crow
 */
public class LineAngle implements AbstractGradingCriteria {
    
    private double getMatchScore(AutoCADElement r1, AutoCADElement r2){
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
    
    private double getAvgAngleScore(List<MatchingAutoCADElements> matches){
        double ret = 0.0;
        for(MatchingAutoCADElements match : matches){
            ret += getMatchScore(match.getElement1(), match.getElement2());
        }
        ret /= matches.size();
        return ret;
    }
    
    @Override
    public double computeScore(AutoCADExport exp1, AutoCADExport exp2) {
        List<MatchingAutoCADElements> closestMatches = new AutoCADElementMatcher(exp1, exp2, this::getMatchScore).findMatches();
        
        return getAvgAngleScore(closestMatches);
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
