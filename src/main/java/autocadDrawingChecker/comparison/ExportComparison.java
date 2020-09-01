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
    private final HashMap<AttributeToCompare, Double> scores;
    private final double finalGrade;
    
    public ExportComparison(AutoCADExport xp1, AutoCADExport xp2, List<AttributeToCompare> criteria){
        src = xp1;
        compareTo = xp2;
        attrs = criteria;
        scores = new HashMap<>();
        finalGrade = runComparison();
    }
    
    // I don't like this
    private double runComparison(){
        double similarityScore = 0.0;
        double newScore = 0.0;
        for(AttributeToCompare attr : attrs){
            switch(attr){
                case LINE_COUNT:
                    newScore = lineCountCompare();
                    scores.put(attr, newScore);
                    similarityScore += newScore;
                    break;
                case LINES_PER_LAYER:
                    newScore = linesPerLayerCompare();
                    scores.put(attr, newScore);
                    similarityScore += newScore;
                    break;
                default:
                    System.err.println("Uncaught attribute to compare in ExportComparison.runComparison: " + attr);
                    break;
            }
        }
        return similarityScore / attrs.size(); // average similarity score
    }
    
    public final AutoCADExport getCmpFile(){
        return compareTo;
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
    
    
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Comparing %s to %s:", src.getFileName(), compareTo.getFileName()));
        scores.forEach((attr, score)->{
            sb.append(String.format("\n* %s: %d%%", attr.getDisplayText(), (int)(score * 100)));
        });
        sb.append(String.format("\nFinal Grade: %d%%", (int)(finalGrade * 100)));
        return sb.toString();
    }
}
