package autocadDrawingChecker.data.autoCADData;

import autocadDrawingChecker.data.core.DataSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * An AutoCADExport is used to store the data extracted by
 * AutoCADExcelParser. One should use LinkedList methods,
 * such as LinkedList::forEach and LinkedList::stream, to
 * operate on the contents of the Export.
 * 
 * @author Matt Crow
 */
public class AutoCADExport extends DataSet {
     
    public AutoCADExport(String fileName){
        super(fileName);
    }
    
    /**
     * Computes how many lines are contained in each AutoCAD layer
     * in the export.
     * 
     * @return a HashMap, where the key is the name of an AutoCAD
     * layer, and the value is the number of lines contained in this
     * export that reside within that layer. <b>Note: The value 
     * will always be greater than 0, so you needn't worry about
     * checking for that</b>
     */
    public final HashMap<String, Integer> getLayerLineCounts(){
        HashMap<String, Integer> lineCounts = new HashMap<>();
        this.stream().map((r)->(AutoCADElement)r).forEach((AutoCADElement row)->{
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
        return String.format("AutoCAD Data Export:\n%s", super.toString());
    }
}
