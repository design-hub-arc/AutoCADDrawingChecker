package autocadDrawingChecker.grading;

import autocadDrawingChecker.autocadData.AutoCADExport;
import autocadDrawingChecker.autocadData.AutoCADLine;
import autocadDrawingChecker.autocadData.AutoCADRow;
import autocadDrawingChecker.autocadData.ExportMatcher;
import autocadDrawingChecker.autocadData.MatchingAutoCADElements;
import java.util.List;

/**
 *
 * @author Matt
 */
public class LineEnd extends AbstractGradingCriteria {
    public LineEnd(){
        super("Line End");
    }
    
    private double getMatchScore(AutoCADRow r1, AutoCADRow r2){
        double score = 0.0;
        if(r1 instanceof AutoCADLine && r2 instanceof AutoCADLine){
            for(int i = 0; i < AutoCADLine.DIMENSION_COUNT; i++){
                score += 1.0 - MathUtil.percentError(
                    ((AutoCADLine)r1).getRSub(i),
                    ((AutoCADLine)r2).getRSub(i)
                );
            }
        }
        return score / AutoCADLine.DIMENSION_COUNT;
    }
    
    private double getAvgEndScore(List<MatchingAutoCADElements> matches){
        double total = 0.0;
        for(MatchingAutoCADElements match : matches){
            total += getMatchScore(match.getElement1(), match.getElement2());
        }
        return total / matches.size();
    }
    
    @Override
    public double computeScore(AutoCADExport exp1, AutoCADExport exp2) {
        List<MatchingAutoCADElements> closestMatches = new ExportMatcher(exp1, exp2, this::getMatchScore).findMatches();
        return getAvgEndScore(closestMatches);
    }

    @Override
    public String getDescription() {
        return "Grades based on how closesly the student's line end points match up with those of the instructor's";
    }

}
