package autocadDrawingChecker.grading;

import autocadDrawingChecker.autocadData.AutoCADExport;
import autocadDrawingChecker.autocadData.AutoCADLine;
import autocadDrawingChecker.autocadData.AutoCADRow;
import autocadDrawingChecker.autocadData.ExportMatcher;
import autocadDrawingChecker.autocadData.Match;
import java.util.List;

/**
 *
 * @author Matt Crow
 */
public class LineAngle extends AbstractGradingCriteria {
    
    public LineAngle(){
        super("Line Angle");
    }
    
    private double getMatchScore(AutoCADRow r1, AutoCADRow r2){
        double score = 0.0;
        if(r1 instanceof AutoCADLine && r2 instanceof AutoCADLine){
            //score = ((AutoCADLine)r1).normDot((AutoCADLine)r2);
            score = 1.0 - MathUtil.percentError(
                ((AutoCADLine)r1).getAngle(),
                ((AutoCADLine)r2).getAngle()
            );
        }
        return score;
    }
    
    private double getAvgAngleScore(List<Match<AutoCADRow>> matches){
        double ret = 0.0;
        for(Match<AutoCADRow> match : matches){
            ret += 1.0 - MathUtil.percentError(
                ((AutoCADLine)match.getObj1()).getAngle(),
                ((AutoCADLine)match.getObj2()).getAngle()
            );
        }
        ret /= matches.size();
        return ret;
    }
    
    @Override
    public double computeScore(AutoCADExport exp1, AutoCADExport exp2) {
        List<Match<AutoCADRow>> closestMatches = new ExportMatcher(exp1, exp2, this::getMatchScore).findMatches();
        
        return getAvgAngleScore(closestMatches);
    }

    @Override
    public String getDescription() {
        return "Grades the students based on how their lines' angles match up with those of the instructor file";
    }

}
