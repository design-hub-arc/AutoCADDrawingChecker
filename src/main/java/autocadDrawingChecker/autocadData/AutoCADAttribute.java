package autocadDrawingChecker.autocadData;

/**
 * An AutoCADAttribute represents
 * a column in an AutoCAD export.
 * 
 * @author Matt Crow
 */
public enum AutoCADAttribute {
    COUNT("Count"),
    NAME("Name"),
    ANGLE("Angle"),
    COLOR("Color"),
    DELTA_X("Delta X"),
    DELTA_Y("Delta Y"),
    DELTA_Z("Delta Z"),
    DRAWING("Drawing"),
    END_X("End X"),
    END_Y("End Y"),
    END_Z("End Z"),
    LAYER("Layer"),
    LENGTH("Length"),
    LINE_TYPE("Linetype"),
    LINE_TYPE_SCALE("Linetype Scale"),
    LINE_WEIGTH("Lineweight"),
    PLOT_STYLE("Plot Style"),
    START_X("Start X"),
    START_Y("Start Y"),
    START_Z("Start Z"),
    THICKNESS("Thickness"),
    CONTENTS("Contents"),
    CONTENTS_RTF("ContentsRTF"),
    POSITION_X("Position X"),
    POSITION_Y("Position Y"),
    POSITION_Z("Position Z"),
    ROTATION("Rotation"),
    SHOW_BORDERS("ShowBorders"),
    WIDTH("Width"),
    AREA("Area"),
    CLOSED("Closed"),
    GLOBAL_WIDTH("Global Width"),
    DIM_STYLE("Dim Style"),
    DYNAMIC_DIMENSION("DynamicDimension"),
    TEXT_DEFINED_SIZE("TextDefinedSize");
    
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
