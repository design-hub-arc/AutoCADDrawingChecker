package autocadDrawingChecker.autocadData;

import java.util.LinkedList;

/**
 *
 * @author Matt
 */
public class AutoCADExport extends LinkedList<AutoCADRow> {
    public AutoCADExport(){
        super();
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
