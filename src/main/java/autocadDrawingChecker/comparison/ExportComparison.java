package autocadDrawingChecker.comparison;

import autocadDrawingChecker.autocadData.AutoCADExport;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Matt
 */
public class ExportComparison {
    private final AutoCADExport src;
    private final AutoCADExport compareTo;
    private final List<AttributeToCompare> attrs;
    
    public ExportComparison(AutoCADExport xp1, AutoCADExport xp2, List<AttributeToCompare> criteria){
        src = xp1;
        compareTo = xp2;
        attrs = criteria;
    }
    
    private double lineCountCompare(){
        return 1.0 - MathUtil.percentError(src.size(), compareTo.size());
    }
    private double linesPerLayerCompare(){
        double score = 0.0;
        HashMap<String, Integer> srcLines = src.getLayerLineCounts();
        HashMap<String, Integer> cmpLines = compareTo.getLayerLineCounts();
        
        for(String layer : srcLines.keySet()){
            if(cmpLines.containsKey(layer)){
                score += MathUtil.percentError(srcLines.get(layer), cmpLines.get(layer));
            }
        }
        return 1.0 - (score / srcLines.size());
    }
    
    public final double runComparison(){
        double similarityScore = 0.0;
        for(AttributeToCompare attr : attrs){
            switch(attr){
                case LINE_COUNT:
                    similarityScore += lineCountCompare();
                    break;
                case LINES_PER_LAYER:
                    similarityScore += linesPerLayerCompare();
                    break;
                default:
                    System.err.println("Uncaught attribute to compare in ExportComparison.runComparison: " + attr);
                    break;
            }
        }
        return similarityScore / attrs.size(); // average similarity score
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Comparing the following exports:");
        sb.append("\n").append(src.toString());
        sb.append("\n").append(compareTo.toString());
        sb.append("\nGrading based on: ").append(attrs.stream().map((attr)->attr.getDisplayText()).collect(Collectors.joining(", ", "", "")));
        return sb.toString();
    }
}
