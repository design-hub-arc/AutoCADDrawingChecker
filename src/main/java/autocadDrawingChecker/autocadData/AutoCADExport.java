package autocadDrawingChecker.autocadData;

import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author Matt
 */
public class AutoCADExport extends LinkedList<AutoCADRow> {
    public AutoCADExport(){
        super();
    }
    
    public final HashMap<String, Integer> getLayerLineCounts(){
        HashMap<String, Integer> lineCounts = new HashMap<>();
        forEach((AutoCADRow row)->{
            String layer = row.getLayer();
            if(!lineCounts.containsKey(layer)){
                lineCounts.put(layer, 0);
            }
            lineCounts.put(layer, lineCounts.get(layer) + 1);
        });
        return lineCounts;
    } 
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("AutoCAD Data Export:");
        forEach((AutoCADRow row)->sb.append("\n").append(row.toString()));
        sb.append("\nEnd of AutoCAD Export Data");
        return sb.toString();
    }
}
