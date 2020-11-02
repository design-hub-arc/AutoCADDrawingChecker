package autocadDrawingChecker.grading.criteria;

import autocadDrawingChecker.data.core.DataSet;
import autocadDrawingChecker.data.core.Record;
import autocadDrawingChecker.grading.ElementMatcher;
import autocadDrawingChecker.grading.MatchingElements;
import java.util.List;
import java.util.stream.Collectors;

/**
 * AbstractElementCriteria adds behavior to AbstractGradingCriteria to add
 * explicit support for grading exports at the element level, meaning an export
 * is graded based on how well its individual elements score.
 * 
 * @author Matt Crow
 */
public interface AbstractElementCriteria<DataSetType extends DataSet, T extends Record> extends AbstractGradingCriteria<DataSetType> {
    /**
     * Calculates the grade for the two given elements.
     * Returns a value between 0.0 and 1.0, with 1.0 meaning
     * they match perfectly, and 0.0 meaning they don't match
     * at all, or are ungradable by this criteria.
     * 
     * @param e1 the instructor element
     * @param e2 the student element
     * @return the student's score for the given element
     */
    public abstract double getMatchScore(T e1, T e2);
    
    /**
     * Computes the average match score of elements in the given exports.
     * @param exp1 the instructor export
     * @param exp2 the student export to grade
     * @return the student's net score for this criteria. Ranges from 0.0 to 1.0
     */
    @Override
    public default double computeScore(DataSetType exp1, DataSetType exp2){
        List<T> gradableElements = exp1.stream().map(this::tryCastRecord).filter((T converted)->{
            return converted != null;
        }).filter((T nonNullConv)->{
            return getMatchScore(nonNullConv, nonNullConv) != 0; // filter out non-gradable rows
        }).collect(Collectors.toList());
        List<MatchingElements<T>> matches = new ElementMatcher<>(gradableElements, exp2, this::tryCastRecord, this::getMatchScore).findMatches();
        double netScore = matches.stream().map((MatchingElements<T> match)->{
            return getMatchScore(tryCastRecord(match.getElement1()), tryCastRecord(match.getElement2()));
        }).reduce(0.0, Double::sum);
        
        if(!matches.isEmpty()){
            netScore /= gradableElements.size();
        }
        return netScore;
    }
    
    public abstract T tryCastRecord(Record rec);
}
