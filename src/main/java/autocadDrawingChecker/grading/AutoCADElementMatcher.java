package autocadDrawingChecker.grading;

import autocadDrawingChecker.data.elements.AutoCADExport;
import autocadDrawingChecker.data.elements.AutoCADElement;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * The AutoCADElementMatcher class is
 used to pair elements in one
 AutoCADExport to elements in
 another. Essentially, this class compares two exports, and asks "which elements from the second export are supposed to match which elements in the first?"
 * 
 * <h2> A Rigorous Definition of what this Should do </h2>
 * Given sets A and B, and function f(a from A, b from B) => real
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
 * @param <T> the type of AutoCADElements to match. Elements 
 * which cannot be converted to this type will be ignored. Use 
 * AutoCADElement to keep all elements. 
 */
public class AutoCADElementMatcher<T extends AutoCADElement> {
    private final AutoCADExport exp1;
    private final AutoCADExport exp2;
    private final Function<AutoCADElement, T> caster;
    private final BiFunction<T, T, Double> score;
    
    /**
     * 
     * @param src
     * @param cmp
     * @param tryCast a function returning the given AutoCADElement as type T, or null if it cannot cast
     * @param scoringFunction 
     */
    public AutoCADElementMatcher(AutoCADExport src, AutoCADExport cmp, Function<AutoCADElement, T> tryCast, BiFunction<T, T, Double> scoringFunction){
        exp1 = src;
        exp2 = cmp;
        caster = tryCast;
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
    public List<MatchingAutoCADElements<T>> findMatches(){
        List<MatchingAutoCADElements<T>> matches = new LinkedList<>();
        
        // pool of unmatched elements
        List<T> pool = exp2.stream().filter((AutoCADElement e)->{
            //return e instanceof T; doesn't work because of how generics are implemented
            return true;
        }).map(caster).filter((T casted)->{ // use caster to cast ...
            return casted != null;          // ... then ignore element which it couldn't casr
        }).collect(Collectors.toList());
        
        exp1.stream().map(caster).filter((T casted)->{
            return casted != null;
        }).forEach((T srcRow)->{
            // find the closest match to srcRow
            double bestScore = 0.0;
            double currScore = 0.0;
            T bestRow = null;
            
            // find the highest score
            for(T cmpRow : pool){
                currScore = score.apply(srcRow, cmpRow);
                if(currScore > bestScore){
                    bestScore = currScore;
                    bestRow = cmpRow;
                }
            }
            
            // some rows may not match at all
            if(bestRow != null){
                MatchingAutoCADElements<T> m = new MatchingAutoCADElements<>(srcRow, bestRow);
                //System.out.println("In AutoCADElementMatcher.findMatches: " + m);
                matches.add(m);
                pool.remove(bestRow);
            }
        });
        
        return matches;
    }
}
