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
public class LineStart extends AbstractGradingCriteria {
    
    public LineStart(){
        super("Line Start");
    }
    
    private double getMatchScore(AutoCADRow r1, AutoCADRow r2){
        double score = 0.0;
        if(r1 instanceof AutoCADLine && r2 instanceof AutoCADLine){
            for(int i = 0; i < AutoCADLine.DIMENSION_COUNT; i++){
                score += 1.0 - MathUtil.percentError(
                    ((AutoCADLine)r1).getR0Sub(i),
                    ((AutoCADLine)r2).getR0Sub(i)
                );
            }
        }
        return score / AutoCADLine.DIMENSION_COUNT;
    }
    
    private double getAvgStartScore(List<Match<AutoCADRow>> matches){
        double total = 0.0;
        for(Match<AutoCADRow> match : matches){
            total += getMatchScore(match.getObj1(), match.getObj2());
        }
        return total / matches.size();
    }
    
    @Override
    public double computeScore(AutoCADExport exp1, AutoCADExport exp2) {
        List<Match<AutoCADRow>> closestMatches = new ExportMatcher(exp1, exp2, this::getMatchScore).findMatches();
        return getAvgStartScore(closestMatches);
    }

    @Override
    public String getDescription() {
        return "Grades based on how closesly the student's line start points match up with those of the instructor's";
    }

}
