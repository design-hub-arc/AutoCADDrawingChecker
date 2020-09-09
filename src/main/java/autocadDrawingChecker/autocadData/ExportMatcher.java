package autocadDrawingChecker.autocadData;

import java.util.LinkedList;
import java.util.List;
import java.util.function.BiFunction;

/**
 * The ExportMatcher class is
 * used to pair elements in one
 * AutoCADExport to elements in
 * another.
 * 
 * For a more rigorous definition,
 * this class takes two sets, A and B,
 * and returns a set C such that
 * each element c in C is the 
 * ordered pair (a from A, b from B)
 * where each key and value in C
 * are unique to at most one pair c.
 * The set C is generated such 
 * that given some similarity
 * score function f(c), there exists
 * no pair p (a from A, b from B)
 * such that f(p) > f(c from C).
 * So C contains the set of
 * matches which maximize this
 * similarity score function.
 * 
 * @author Matt Crow
 */
public class ExportMatcher {
    private final AutoCADExport exp1;
    private final AutoCADExport exp2;
    private final BiFunction<AutoCADRow, AutoCADRow, Double> score;
    
    public ExportMatcher(AutoCADExport src, AutoCADExport cmp, BiFunction<AutoCADRow, AutoCADRow, Double> scoringFunction){
        exp1 = src;
        exp2 = cmp;
        score = scoringFunction;
    }
    
    /**
     * 
     * @return 
     */
    public List<Match<AutoCADRow>> findMatches(){
        List<Match<AutoCADRow>> matches = new LinkedList<>();
        
        // pool of unmatched elements
        LinkedList<AutoCADRow> pool = new LinkedList<>();
        exp2.forEach(pool::add);
        
        exp1.forEach((AutoCADRow srcRow)->{
            // find the closest match to srcRow
            double bestScore = 0.0;
            double currScore = 0.0;
            AutoCADRow bestRow = null;
            
            // find the highest score
            for(AutoCADRow cmpRow : pool){
                currScore = score.apply(srcRow, cmpRow);
                if(currScore > bestScore){
                    bestScore = currScore;
                    bestRow = cmpRow;
                }
            }
            
            // some rows may not match at all
            if(bestRow != null){
                Match<AutoCADRow> m = new Match<>(srcRow, bestRow);
                System.out.println("In ExportMatcher.findMatches: " + m);
                matches.add(m);
                pool.remove(bestRow);
            }
        });
        
        return matches;
    }
}
