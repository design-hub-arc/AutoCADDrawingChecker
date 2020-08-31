package autocadDrawingChecker.autocadData;

/**
 *
 * @author Matt
 */
public class AutoCADRow {
    private final String inLayer;
    
    public AutoCADRow(String layerName){
        inLayer = layerName;
    }
    
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
