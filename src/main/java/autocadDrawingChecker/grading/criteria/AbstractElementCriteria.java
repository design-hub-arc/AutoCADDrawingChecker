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
 * @param <DataSetType> the type of DataSet this expects to grade
 * @param <RecordType> the type of Record this expects to grade
 */
public interface AbstractElementCriteria<DataSetType extends DataSet, RecordType extends Record> extends AbstractGradingCriteria<DataSetType> {
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
    public abstract double getMatchScore(RecordType e1, RecordType e2);
    
    /**
     * Computes the average match score of elements in the given exports.
     * @param exp1 the instructor export
     * @param exp2 the student export to grade
     * @return the student's net score for this criteria. Ranges from 0.0 to 1.0
     */
    @Override
    public default double doGrade(DataSetType exp1, DataSetType exp2){
        List<RecordType> gradableElements = exp1.stream().map(this::tryCastRecord).filter((RecordType converted)->{
            return converted != null;
        }).filter((RecordType nonNullConv)->{
            return getMatchScore(nonNullConv, nonNullConv) != 0; // filter out non-gradable rows
        }).collect(Collectors.toList());
        List<MatchingElements<RecordType>> matches = new ElementMatcher<>(gradableElements, exp2, this::tryCastRecord, this::getMatchScore).findMatches();
        double netScore = matches.stream().map((MatchingElements<RecordType> match)->{
            return getMatchScore(tryCastRecord(match.getElement1()), tryCastRecord(match.getElement2()));
        }).reduce(0.0, Double::sum);
        
        if(!matches.isEmpty()){
            netScore /= gradableElements.size();
        }
        return netScore;
    }
    
    /**
     * Attempts to cast the given Record to the RecordType
     * this expects.
     * 
     * @param rec the Record to cast
     * @return the casted Record, or null if the conversion is impossible
     */
    public abstract RecordType tryCastRecord(Record rec);
}
