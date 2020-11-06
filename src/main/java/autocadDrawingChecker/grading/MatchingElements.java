package autocadDrawingChecker.grading;

import autocadDrawingChecker.data.core.Record;

/**
 * The MatchingElements class is 
 used to connect SpreadsheetRecords from one
 file to another. Essentially, a match
 * between element a in drawing 1 and
 * element b in drawing 2 means that,
 * when grading, the program assumes
 * a is meant to be b, and should therefore
 * grade on how well they conform to each
 * other.
 * 
 * @author Matt Crow
 * @param <T> the subclass of Record this should hold
 */
public class MatchingElements<T extends Record> {
    private final T element1;
    private final T element2;
    
    public MatchingElements(T a, T b){
        element1 = a;
        element2 = b;
    }
    
    public final T getElement1(){
        return element1;
    }
    
    public final T getElement2(){
        return element2;
    }
    
    @Override
    public String toString(){
        return String.format("%s matches %s", element1.toString(), element2.toString());
    }
}
