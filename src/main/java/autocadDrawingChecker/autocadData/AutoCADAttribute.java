package autocadDrawingChecker.autocadData;

/**
 * An AutoCADAttribute represents
 * a column in an AutoCAD export.
 * 
 * @author Matt Crow
 */
public enum AutoCADAttribute {
    NAME("Name"),
    LAYER("Layer"),
    START_X("Start X"),
    START_Y("Start Y"),
    START_Z("Start Z"),
    END_X("End X"),
    END_Y("End Y"),
    END_Z("End Z");
    
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
