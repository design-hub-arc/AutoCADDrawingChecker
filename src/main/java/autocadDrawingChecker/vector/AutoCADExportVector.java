package autocadDrawingChecker.vector;

/**
 *
 * @author Matt
 */
public class AutoCADExportVector {
    private final String layer;
    
    public AutoCADExportVector(String layerName){
        layer = layerName;
    }
    
    public final String getLayer(){
        return layer;
    }
    
    @Override
    public String toString(){
        return String.format("AutoCADExportVector{layer=\"%s\"}", layer);
    }
}
