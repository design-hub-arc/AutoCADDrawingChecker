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
    public abstract double getMatchScore(T e1, T e2);
    
    /**
     * Computes the average match score of elements in the given exports.
     * @param exp1 the instructor export
     * @param exp2 the student export to grade
     * @return the student's net score for this criteria. Ranges from 0.0 to 1.0
     */
    @Override
    public default double computeScore(DataSetType exp1, DataSetType exp2){
        List<T> l1 = exp1.stream().map(this::tryCastRecord).collect(Collectors.toList());
        List<T> l2 = exp2.stream().map(this::tryCastRecord).collect(Collectors.toList());
        List<MatchingElements<T>> matches = new ElementMatcher<>(l1, l2, this::canAccept, this::getMatchScore).findMatches();
        double netScore = matches.stream().map((MatchingElements<T> match)->{
            return getMatchScore(tryCastRecord(match.getElement1()), tryCastRecord(match.getElement2()));
        }).reduce(0.0, Double::sum);
        
        if(!matches.isEmpty()){
            netScore /= matches.size();
        }
        return netScore;
    }
    
    public abstract T tryCastRecord(Record rec);
    public abstract boolean canAccept(T e);
    
    
}
