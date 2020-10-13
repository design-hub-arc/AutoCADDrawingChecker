package autocadDrawingChecker.data.core;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 *
 * @author Matt
 */
public class ExtractedSpreadsheetContents extends LinkedList<SpreadsheetRecord> {
    private final String fileName;
    
    public ExtractedSpreadsheetContents(String fileName){
        super();
        this.fileName = fileName;
    }
    
    public final String getFileName(){
        return fileName;
    }
    
    public final HashMap<Object, LinkedList<SpreadsheetRecord>> sortRecordsByColumn(String column){
        HashMap<Object, LinkedList<SpreadsheetRecord>> valueToRecords = new HashMap<>();
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
        HashMap<Object, LinkedList<SpreadsheetRecord>> sorted = sortRecordsByColumn(column);
        HashMap<Object, Integer> counts = new HashMap<>();
        sorted.forEach((key, list)->{
            counts.put(key, list.size());
        });
        return counts;
    }
    
    public final Set<String> getColumns(){
        Set<String> cols = new HashSet<>();
        forEach((SpreadsheetRecord e)->{
            cols.addAll(e.getAttributes());
        });
        return cols;
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Extracted Spreadsheet Contents:");
        forEach((SpreadsheetRecord row)->sb.append("\n").append(row.toString()));
        sb.append("\nEnd of Extracted Spreadsheet Contents");
        return sb.toString();
    }
}
