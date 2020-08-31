package autocadDrawingChecker.autocadData;

/**
 *
 * @author Matt Crow
 */
public enum AutoCADAttribute {
    LAYER("Layer");
    
    private final String header;
    
    private AutoCADAttribute(String sheetHeader){
        header = sheetHeader;
    }
    
    public final String getHeader(){
        return header;
    }
}
