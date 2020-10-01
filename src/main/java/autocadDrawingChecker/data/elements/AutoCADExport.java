package autocadDrawingChecker.data.elements;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * An AutoCADExport is used to store the data extracted by
 * AutoCADExcelParser. One should use LinkedList methods,
 * such as LinkedList::forEach and LinkedList::stream, to
 * operate on the contents of the Export
 * 
 * @author Matt Crow
 */
public class AutoCADExport extends LinkedList<AutoCADElement> {
    private final String fileName;
     
    public AutoCADExport(String fileName){
        super();
        this.fileName = fileName;
    }
    
    public final String getFileName(){
        return fileName;
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
        forEach((AutoCADElement row)->{
            String layer = row.getLayer();
            if(!lineCounts.containsKey(layer)){
                lineCounts.put(layer, 0);
            }
            lineCounts.put(layer, lineCounts.get(layer) + 1);
        });
        return lineCounts;
    } 
    
    public final HashMap<Object, LinkedList<AutoCADElement>> sortRecordsByColumn(String column){
        HashMap<Object, LinkedList<AutoCADElement>> valueToRecords = new HashMap<>();
        forEach((record)->{
            Object value = record.getAttribute(column);
            if(!valueToRecords.containsKey(value)){
                valueToRecords.put(value, new LinkedList<>());
            }
            valueToRecords.get(value).add(record);
        });
        return valueToRecords;
    }
    public final HashMap<Object, Integer> getCountsPerColumnValue(String column){
        HashMap<Object, LinkedList<AutoCADElement>> sorted = sortRecordsByColumn(column);
        HashMap<Object, Integer> counts = new HashMap<>();
        sorted.forEach((key, list)->{
            counts.put(key, list.size());
        });
        return counts;
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("AutoCAD Data Export:");
        forEach((AutoCADElement row)->sb.append("\n").append(row.toString()));
        sb.append("\nEnd of AutoCAD Export Data");
        return sb.toString();
    }
}
