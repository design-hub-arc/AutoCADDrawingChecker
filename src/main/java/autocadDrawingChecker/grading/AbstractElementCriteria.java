package autocadDrawingChecker.grading;

import autocadDrawingChecker.autocadData.elements.AutoCADElement;
import autocadDrawingChecker.autocadData.AutoCADElementMatcher;
import autocadDrawingChecker.autocadData.AutoCADExport;
import autocadDrawingChecker.autocadData.MatchingAutoCADElements;
import java.util.List;

/**
 * AbstractElementCriteria adds behavior to AbstractGradingCriteria to add
 * explicit support for grading exports at the element level, meaning an export
 * is graded based on how well its individual elements score.
 * 
 * @author Matt Crow
 * @param <T> the type of elements to grade
 */
public interface AbstractElementCriteria<T extends AutoCADElement> extends AbstractGradingCriteria {
    /**
     * Converts e to the given type T. If that
     * is not possible, return null instead.
     * @param e
     * @return e as T, if possible
     */
    public abstract T cast(AutoCADElement e);
    
    public abstract double getMatchScore(T e1, T e2);
    
    /**
     * Computes the average match score of elements in the given exports.
     * @param exp1
     * @param exp2
     * @return 
     */
    @Override
    public default double computeScore(AutoCADExport exp1, AutoCADExport exp2){
        List<MatchingAutoCADElements<T>> matches = new AutoCADElementMatcher<>(exp1, exp2, this::cast, this::getMatchScore).findMatches();
        double netScore = matches.stream().map((MatchingAutoCADElements<T> match)->{
            return getMatchScore(match.getElement1(), match.getElement2());
        }).reduce(0.0, Double::sum);
        
        if(!matches.isEmpty()){
            netScore /= matches.size();
        }
        return netScore;
    }
}
