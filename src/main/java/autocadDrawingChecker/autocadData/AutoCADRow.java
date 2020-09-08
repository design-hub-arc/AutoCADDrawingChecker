package autocadDrawingChecker.autocadData;

/**
 * An AutoCADRow represents
 * a single record in an AutoCADExport.
 * 
 * @author Matt Crow
 */
public class AutoCADRow {
    private final String inLayer;
    private final double length;
    
    /**
     * 
     * @param layerName the value from the 
     * 'Layer' column of the Excel workbook
     * from whence this came.
     * @param lineLength the length of this line
     */
    public AutoCADRow(String layerName, double lineLength){
        inLayer = layerName;
        length = lineLength;
    }
    
    /**
     * 
     * @return the AutoCAD layer this
     * resides within
     */
    public final String getLayer(){
        return inLayer;
    }
    
    /**
     * 
     * @return the length of this line.
     */
    public final double getLength(){
        return length;
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("AutoCAD Data Row:");
        sb.append("\n\t* Layer: ").append(inLayer);
        sb.append(String.format("\n\t* Length: %f", length));
        return sb.toString();
    }
}
