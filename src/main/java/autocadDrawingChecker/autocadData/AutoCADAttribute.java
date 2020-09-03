package autocadDrawingChecker.autocadData;

/**
 * An AutoCADAttribute represents
 * a column in an AutoCAD export.
 * 
 * @author Matt Crow
 */
public enum AutoCADAttribute {
    LAYER("Layer");
    
    private final String header;
    
    /**
     * 
     * @param sheetHeader the header of this attribute
     * as it appears in the AutoCAD export sheet.
     */
    private AutoCADAttribute(String sheetHeader){
        header = sheetHeader;
    }
    
    /**
     * 
     * @return the header of this attribute
     * as it appears in the AutoCAD export 
     * sheet.
     */
    public final String getHeader(){
        return header;
    }
}
