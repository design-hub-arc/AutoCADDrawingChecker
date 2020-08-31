package autocadDrawingChecker.comparison;

/**
 *
 * @author Matt
 */
public enum AttributeToCompare {
    LINE_COUNT("Line count"),
    LINES_PER_LAYER("Lines per layer");
    
    private final String displayText;
    
    private AttributeToCompare(String text){
        displayText = text;
    }
    
    public final String getDisplayText(){
        return displayText;
    }
}
