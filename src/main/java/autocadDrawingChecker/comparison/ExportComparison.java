package autocadDrawingChecker.comparison;

import autocadDrawingChecker.autocadData.AutoCADExport;

/**
 *
 * @author Matt
 */
public class ExportComparison {
    private final AutoCADExport src;
    private final AutoCADExport compareTo;
    
    public ExportComparison(AutoCADExport xp1, AutoCADExport xp2){
        src = xp1;
        compareTo = xp2;
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Comparing the following exports:");
        sb.append("\n").append(src.toString());
        sb.append("\n").append(compareTo.toString());
        return sb.toString();
    }
}
