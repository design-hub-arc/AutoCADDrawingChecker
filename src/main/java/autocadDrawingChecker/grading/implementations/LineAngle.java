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
 * Still needs to consider complimentary angles
 * @author Matt Crow
 */
public class LineAngle implements AbstractGradingCriteria {
    
    private double getMatchScore(AutoCADElement r1, AutoCADElement r2){
        double score = 0.0;
        if(r1 instanceof AutoCADLine && r2 instanceof AutoCADLine){
            score = 1.0 - MathUtil.percentError(
                ((AutoCADLine)r1).getAngle(),
                ((AutoCADLine)r2).getAngle()
            );
        }
        return score;
    }
    
    private double getAvgAngleScore(List<MatchingAutoCADElements> matches){
        double ret = 0.0;
        for(MatchingAutoCADElements match : matches){
            ret += 1.0 - MathUtil.percentError(
                ((AutoCADLine)match.getElement1()).getAngle(),
                ((AutoCADLine)match.getElement2()).getAngle()
            );
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
