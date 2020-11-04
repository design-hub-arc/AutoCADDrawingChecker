package autocadDrawingChecker.start;

/**
 *
 * @author Matt
 * @param <T> the type of the data this attribute contains
 */
public class DrawingCheckerAttribute<T> {
    private T selectedValue;
    
    DrawingCheckerAttribute(){
        selectedValue = null;
    }
    
    public final void set(T newValue){
        selectedValue = newValue;
    }
    public final T get(){
        if(!isSet()){
            throw new NullPointerException();
        }
        return selectedValue;
    }
    public boolean isSet(){
        return selectedValue != null;
    }
}
