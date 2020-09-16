package autocadDrawingChecker.grading.implementations;

import autocadDrawingChecker.autocadData.AutoCADElement;
import autocadDrawingChecker.autocadData.AutoCADElementMatcher;
import autocadDrawingChecker.autocadData.AutoCADExport;
import autocadDrawingChecker.autocadData.MatchingAutoCADElements;
import autocadDrawingChecker.grading.AbstractGradingCriteria;
import autocadDrawingChecker.grading.MathUtil;
import java.util.List;

/**
 * AbstractVectorCriteria is used grading based on some multi-element grading
 * criteria. Currently, this just uses the average score of each vector component,
 * but I may change to normalized dot product later.
 * 
 * @author Matt Crow
 */
public interface AbstractVectorCriteria extends AbstractGradingCriteria {
    public default double getMatchScore(AutoCADElement e1, AutoCADElement e2){
        double score = 0.0;
        double[] v1;
        double[] v2;
        int numComponents;
        if(canGrade(e1) && canGrade(e2)){
            v1 = extractVector(e1);
            v2 = extractVector(e2);
            numComponents = Math.min(v1.length, v2.length);
            for(int i = 0; i < numComponents; i++){
                score += 1.0 - MathUtil.percentError(v1[i], v2[i]);
            }
            score /= numComponents; // average percent correct
        }
        return score;
    }
    public default double getAvgScore(List<MatchingAutoCADElements> matches){
        double total = matches.stream().map((MatchingAutoCADElements match)->{
            return getMatchScore(match.getElement1(), match.getElement2());
        }).reduce(0.0, Double::sum);
        if(!matches.isEmpty()){
            total /= matches.size();
        }
        return total;
    }
    
    @Override
    public default double computeScore(AutoCADExport exp1, AutoCADExport exp2) {
        List<MatchingAutoCADElements> closestMatches = new AutoCADElementMatcher(exp1, exp2, this::getMatchScore).findMatches();
        return getAvgScore(closestMatches);
    }

    /**
     * 
     * @param e
     * @return whether or not this can grade the given element.
     * If it cannot, getMatchScore will always return 0.0 when
     * the given element is passed as a parameter.
     */
    public abstract boolean canGrade(AutoCADElement e);
    
    /**
     * 
     * @param e
     * @return the vector interpretation of the given element,
     * which this will grade on
     */
    public abstract double[] extractVector(AutoCADElement e);
    
    @Override
    public abstract String getDescription();

    @Override
    public abstract String getName();
}
