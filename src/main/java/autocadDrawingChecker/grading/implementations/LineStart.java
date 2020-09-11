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
 *
 * @author Matt Crow
 */
public class LineStart extends AbstractGradingCriteria {
    
    public LineStart(){
        super("Line Start");
    }
    
    private double getMatchScore(AutoCADElement r1, AutoCADElement r2){
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
    
    private double getAvgStartScore(List<MatchingAutoCADElements> matches){
        double total = 0.0;
        for(MatchingAutoCADElements match : matches){
            total += getMatchScore(match.getElement1(), match.getElement2());
        }
        return total / matches.size();
    }
    
    @Override
    public double computeScore(AutoCADExport exp1, AutoCADExport exp2) {
        List<MatchingAutoCADElements> closestMatches = new AutoCADElementMatcher(exp1, exp2, this::getMatchScore).findMatches();
        return getAvgStartScore(closestMatches);
    }

    @Override
    public String getDescription() {
        return "Grades based on how closesly the student's line start points match up with those of the instructor's";
    }

}
