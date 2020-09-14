package autocadDrawingChecker.grading.implementations;

import autocadDrawingChecker.autocadData.AutoCADElement;
import autocadDrawingChecker.autocadData.AutoCADElementMatcher;
import autocadDrawingChecker.autocadData.AutoCADExport;
import autocadDrawingChecker.autocadData.MatchingAutoCADElements;
import autocadDrawingChecker.grading.AbstractGradingCriteria;
import java.util.List;

/**
 *
 * @author Matt
 */
public class TextMatches extends AbstractGradingCriteria {

    public TextMatches() {
        super("Text Matches");
    }
    
    private double getMatchScore(AutoCADElement row1, AutoCADElement row2){
        double score = 0.0;
        
        return score;
    }
    @Override
    public double computeScore(AutoCADExport exp1, AutoCADExport exp2) {
        List<MatchingAutoCADElements> closestMatches = new AutoCADElementMatcher(exp1, exp2, this::getMatchScore).findMatches();
        double netScore = 0.0;
        netScore = closestMatches.stream().map((match)->{
            return getMatchScore(match.getElement1(), match.getElement2());
        }).reduce(netScore, (accumulator, score) -> accumulator + score);
        if(!closestMatches.isEmpty()){
            netScore /= closestMatches.size();
        }
        return netScore;
    }

    @Override
    public String getDescription() {
        return "Checks to see if the text in a student file's text components matches that of the professor's export";
    }

}
