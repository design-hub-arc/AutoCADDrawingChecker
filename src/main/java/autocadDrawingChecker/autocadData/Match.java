package autocadDrawingChecker.autocadData;

/**
 * The Match class is used
 * to connect two similar objects
 * together.
 * 
 * @author Matt Crow
 * @param <T> the type of
 * the two objects matched
 * by this.
 */
public class Match<T> {
    private final T obj1;
    private final T obj2;
    
    public Match(T a, T b){
        obj1 = a;
        obj2 = b;
    }
    
    public final T getObj1(){
        return obj1;
    }
    
    public final T getObj2(){
        return obj2;
    }
    
    @Override
    public String toString(){
        return String.format("%s matches %s", obj1.toString(), obj2.toString());
    }
}
