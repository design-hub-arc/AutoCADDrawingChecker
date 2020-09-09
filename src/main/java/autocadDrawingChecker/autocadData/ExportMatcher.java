package autocadDrawingChecker.autocadData;

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

}
