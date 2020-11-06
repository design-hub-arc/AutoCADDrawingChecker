package autocadDrawingChecker.grading;

import autocadDrawingChecker.data.core.Record;
import autocadDrawingChecker.logging.Logger;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * The ElementMatcher class is
 used to pair elements in one
 AutoCADExport to elements in
 another. Essentially, this class compares two exports, and asks "which elements from the second export are supposed to match which elements in the first?"
 * 
 * <h2> A Rigorous Definition of what this Should do </h2>
 * Given sets A and B, and function <pre>f(a from A, b from B) =&gt; real</pre>
 * this class returns the set C such that each element c in C contains
 * an ordered pair (a from A, b from B) such that 
 * there exists no set c in C such that for any c2 in C, c2 shares an element with c and c2 is not c
 * Furthermore, C must be generated in a way such that for any combination of a set C1 containing ordered pairs (a from A, b from B)
 * the sum of f(c) for c in C is greater than or equal to the sum of f(c1) for c1 in C1
 * 
 * <h2> Requirements </h2>
 * <ul>
 * <li>each set c returned must contain one element from a, and one from b</li>
 * <li>each key and value in c must be unique in C</li>
 * <li>C must contain the maximum average value of f(c) possible <b>This is the hard one</b></li>
 * </ul>
 * 
 * @author Matt Crow 
 * @param <T> the type of record this will match
 */
public class ElementMatcher<T extends Record> {
    private final List<? extends Record> exp1;
    private final List<? extends Record> exp2;
    private final Function<Record, T> recordCaster;
    private final BiFunction<T, T, Double> score;
    
    /**
     * 
     * @param src the instructor AutoCADExport the student's file should conform to
     * @param cmp the student's file
     * @param recordCaster a function which accepts a Record, and attempts to cast it to T. Returns null if it fails
    * @param scoringFunction a function which returns a double between 0.0 and 1.0. When given an instructor and student file, it should return a number
     * within this range, with higher scores meaning the student's export is similar to the instructor export, and lower ones meaning the two exports are 
     * different. Essentially, this acts as a grader assigning a score based on how well the student did by some metric.
     */
    public ElementMatcher(List<? extends Record> src, List<? extends Record> cmp, Function<Record, T> recordCaster, BiFunction<T, T, Double> scoringFunction){
        exp1 = src;
        exp2 = cmp;
        this.recordCaster = recordCaster;
        score = scoringFunction;
    }
    
    /**
     * This is currently not an ideal algorithm.
     * For example, given two drawings, each with
     * two lines, it could match a 100% match, then
     * a 0% match, resulting in a score of 50%. But,
     * suppose it were possible for it to find two
     * 70% matches using a different algorithm, a
     * score of 70%. Therefore, this greedy matching
     * algorithm is not ideal.
     * 
     * @return the list of matches between the two
     * given files.
     */
    public List<MatchingElements<T>> findMatches(){
        List<MatchingElements<T>> matches = new LinkedList<>();
        
        // pool of unmatched elements
        List<T> pool = exp2.stream().map(recordCaster).filter((casted)->casted != null).collect(Collectors.toList());
        
        exp1.stream().map(recordCaster).filter((casted)->casted != null).forEach((T srcRow)->{
            // find the closest match to srcRow
            double bestScore = 0.0;
            double currScore = 0.0;
            T bestRow = null;
            
            // find the highest score
            for(T cmpRow : pool){
                try {
                    currScore = score.apply(srcRow, cmpRow);
                } catch (Exception ex){
                    Logger.logError(ex);
                    currScore = 0.0;
                }
                if(currScore > bestScore){
                    bestScore = currScore;
                    bestRow = cmpRow;
                }
            }
            
            // some rows may not match at all
            if(bestRow != null){
                MatchingElements<T> m = new MatchingElements<>(srcRow, bestRow);
                //System.out.println("In ElementMatcher.findMatches: " + m);
                matches.add(m);
                pool.remove(bestRow);
            }
        });
        
        return matches;
    }
}
