package autocadDrawingChecker.grading;

import autocadDrawingChecker.autocadData.AutoCADExport;
import java.util.HashMap;
import java.util.List;

/**
 * The ExportComparison class
 * handles grading a single student
 * file to the instructor file.
 * <b>Note that this comparison may 
 * not be commutative: 
 * <pre>ExportComparison(x, y) != ExportComparison(y, x)</pre>
 * </b>
 * 
 * @author Matt Crow.
 */
public class ExportComparison {
    private final AutoCADExport src;
    private final AutoCADExport compareTo;
    private final List<AbstractGradingCriteria> attrs;
    private final HashMap<AbstractGradingCriteria, Double> scores;
    private final double finalGrade;
    
    public ExportComparison(AutoCADExport xp1, AutoCADExport xp2, List<AbstractGradingCriteria> criteria){
        src = xp1;
        compareTo = xp2;
        attrs = criteria;
        scores = new HashMap<>();
        finalGrade = runComparison();
    }
    
    private double runComparison(){
        double similarityScore = 0.0;
        double newScore = 0.0;
        for(AbstractGradingCriteria attr : attrs){
            newScore = attr.computeScore(src, compareTo);
            scores.put(attr, newScore);
            similarityScore += newScore;
        }
        return similarityScore / attrs.size(); // average similarity score
    }
    
    public final AutoCADExport getCmpFile(){
        return compareTo;
    }   
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Comparing %s to %s:", src.getFileName(), compareTo.getFileName()));
        scores.forEach((attr, score)->{
            sb.append(String.format("\n* %s: %d%%", attr.getName(), (int)(score * 100)));
        });
        sb.append(String.format("\nFinal Grade: %d%%", (int)(finalGrade * 100)));
        return sb.toString();
    }
}
