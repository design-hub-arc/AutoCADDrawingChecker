package autocadDrawingChecker.grading.criteria;

import autocadDrawingChecker.data.autoCADData.AutoCADElement;
import autocadDrawingChecker.data.autoCADData.AutoCADExport;
import autocadDrawingChecker.grading.AutoCADElementMatcher;
import autocadDrawingChecker.grading.MatchingAutoCADElements;
import java.util.Arrays;
import java.util.List;

/**
 * AbstractElementCriteria adds behavior to AbstractGradingCriteria to add
 * explicit support for grading exports at the element level, meaning an export
 * is graded based on how well its individual elements score.
 * 
 * @author Matt Crow
 */
public interface AbstractElementCriteria extends AbstractGradingCriteria {
    public static final String[] ANY_TYPE = new String[0];
    
    public abstract double getMatchScore(AutoCADElement e1, AutoCADElement e2);
    
    /**
     * Computes the average match score of elements in the given exports.
     * @param exp1 the instructor export
     * @param exp2 the student export to grade
     * @return the student's net score for this criteria. Ranges from 0.0 to 1.0
     */
    @Override
    public default double computeScore(AutoCADExport exp1, AutoCADExport exp2){
        List<MatchingAutoCADElements> matches = new AutoCADElementMatcher(exp1, exp2, this::canAccept, this::getMatchScore).findMatches();
        double netScore = matches.stream().map((MatchingAutoCADElements match)->{
            return getMatchScore(match.getElement1(), match.getElement2());
        }).reduce(0.0, Double::sum);
        
        if(!matches.isEmpty()){
            netScore /= matches.size();
        }
        return netScore;
    }
    
    /**
     * Checks to see if the given AutoCADElement can -or should-
     * be graded by this criteria. This is used by the AutoCADElementMatcher
     * to decide if the given element should be graded.
     * @see AutoCADElementMatcher
     * @param e the AutoCADElement to check
     * @return whether or not this criteria can grade e
     */
    public default boolean canAccept(AutoCADElement e){
        String[] types = getAllowedTypes();
        String eType = e.getName();
        boolean acceptable = Arrays.equals(types, ANY_TYPE);
        for(int i = 0; i < types.length && !acceptable; i++){
            acceptable = eType.equalsIgnoreCase(types[i]);
        }        
        return acceptable;
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
