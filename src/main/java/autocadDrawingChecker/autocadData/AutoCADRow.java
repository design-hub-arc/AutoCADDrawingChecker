package autocadDrawingChecker.autocadData;

/**
 * An AutoCADRow represents
 * a single record in an AutoCADExport.
 * 
 * @author Matt Crow
 */
public class AutoCADRow {
    private final String inLayer;
    /**
     * 
     * @param layerName the value from the 
     * 'Layer' column of the Excel workbook
     * from whence this came.
     */
    public AutoCADRow(String layerName){
        inLayer = layerName;
    }
    
    /**
     * 
     * @return the AutoCAD layer this
     * resides within
     */
    public final String getLayer(){
        return inLayer;
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("AutoCAD Data Row:");
        sb.append("\n\t* Layer: ").append(inLayer);
        return sb.toString();
    }
}
