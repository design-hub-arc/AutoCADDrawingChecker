package autocadDrawingChecker.grading.criteria;

import autocadDrawingChecker.data.elements.AutoCADElement;
import autocadDrawingChecker.data.elements.AutoCADExport;
import autocadDrawingChecker.grading.AutoCADElementMatcher;
import autocadDrawingChecker.grading.MatchingAutoCADElements;
import java.util.List;

/**
 * AbstractElementCriteria adds behavior to AbstractGradingCriteria to add
 * explicit support for grading exports at the element level, meaning an export
 * is graded based on how well its individual elements score.
 * 
 * @author Matt Crow
 */
public interface AbstractElementCriteria<T extends AutoCADElement> extends AbstractGradingCriteria {
    public static final String[] ANY_TYPE = new String[0];
    
    /**
     * Converts e to the given type T. If that
     * is not possible, return null instead.
     * @param e the element to attempt to cast to T
     * @return e as T, if possible
     */
    public abstract T cast(AutoCADElement e);
    
    public abstract double getMatchScore(T e1, T e2);
    
    /**
     * Computes the average match score of elements in the given exports.
     * @param exp1 the instructor export
     * @param exp2 the student export to grade
     * @return the student's net score for this criteria. Ranges from 0.0 to 1.0
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
    
    /**
     * 
     * @return a list of AutoCAD Name column values.
     * This is meant to filter out unwanted rows.
     * May remove later.
     * 
     * You can make this return AbstractElementCriteria.ANY_TYPE
     * to allow all record types.
     */
    public abstract String[] getAllowedTypes();
}
