package autocadDrawingChecker.data.core;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 *
 * @author Matt
 */
public class DataSet extends LinkedList<Record> {
    private final String fileName;
    
    public DataSet(String fileName){
        super();
        this.fileName = fileName;
    }
    
    public final String getFileName(){
        return fileName;
    }
    
    public final HashMap<Object, LinkedList<Record>> sortRecordsByColumn(String column){
        HashMap<Object, LinkedList<Record>> valueToRecords = new HashMap<>();
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
        HashMap<Object, LinkedList<Record>> sorted = sortRecordsByColumn(column);
        HashMap<Object, Integer> counts = new HashMap<>();
        sorted.forEach((key, list)->{
            counts.put(key, list.size());
        });
        return counts;
    }
    
    public final Set<String> getColumns(){
        Set<String> cols = new HashSet<>();
        forEach((Record e)->{
            cols.addAll(e.getAttributes());
        });
        return cols;
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Extracted Spreadsheet Contents:");
        forEach((Record row)->sb.append("\n").append(row.toString()));
        sb.append("\nEnd of Extracted Spreadsheet Contents");
        return sb.toString();
    }
}
