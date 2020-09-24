package autocadDrawingChecker.autocadData.elements;

/**
 *
 * @author Matt
 */
public abstract class AbstractAutoCADText extends AutoCADElement {
    private final String textContents;
    private final int rotation;
    
    public AbstractAutoCADText(String textContents, int rotation){
        this.textContents = textContents;
        this.rotation = rotation;
    }
    
    public final String getTextContents(){
        return textContents;
    }
    
    public final int getRotation(){
        return rotation;
    }
}
