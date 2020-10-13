package autocadDrawingChecker.grading;

import autocadDrawingChecker.data.autoCADData.AutoCADElement;

/**
 * The MatchingAutoCADElements class is 
 * used to connect AutoCADElements from one
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
    private final AutoCADElement element1;
    private final AutoCADElement element2;
    
    public MatchingAutoCADElements(AutoCADElement a, AutoCADElement b){
        element1 = a;
        element2 = b;
    }
    
    public final AutoCADElement getElement1(){
        return element1;
    }
    
    public final AutoCADElement getElement2(){
        return element2;
    }
    
    @Override
    public String toString(){
        return String.format("%s matches %s", element1.toString(), element2.toString());
    }
}
