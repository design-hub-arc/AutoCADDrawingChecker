package autocadDrawingChecker.autocadData;

/**
 * The MatchingAutoCADElements class is 
 * used to connect AutoCADRows from one
 * file to another. Essentially, a match
 * between element a in drawing 1 and
 * element b in drawing 2 means that,
 * when grading, the program assumes
 * a is meant to be b, and should therefore
 * grade on how well they conform to each
 * other.
 * 
 * @author Matt Crow
 */
public class MatchingAutoCADElements {
    private final AutoCADRow element1;
    private final AutoCADRow element2;
    
    public MatchingAutoCADElements(AutoCADRow a, AutoCADRow b){
        element1 = a;
        element2 = b;
    }
    
    public final AutoCADRow getElement1(){
        return element1;
    }
    
    public final AutoCADRow getElement2(){
        return element2;
    }
    
    @Override
    public String toString(){
        return String.format("%s matches %s", element1.toString(), element2.toString());
    }
}
