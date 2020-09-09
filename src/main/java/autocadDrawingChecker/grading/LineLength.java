package autocadDrawingChecker.grading;

import autocadDrawingChecker.autocadData.AutoCADExport;
import autocadDrawingChecker.autocadData.AutoCADLine;
import autocadDrawingChecker.autocadData.AutoCADRow;
import autocadDrawingChecker.autocadData.ExportMatcher;
import autocadDrawingChecker.autocadData.Match;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Matt Crow
 */
public class LineLength extends AbstractGradingCriteria {
    
    public LineLength(){
        super("Line Length");
    }
    
    private double getMatchScore(AutoCADRow r1, AutoCADRow r2){
        double score = 0.0;
        if(r1 instanceof AutoCADLine && r2 instanceof AutoCADLine){
            score = ((AutoCADLine)r1).normDot((AutoCADLine)r2);
        }
        return score;
    }
    
    private double getAvgLengthScore(List<Match<AutoCADRow>> matches){
        double ret = 0.0;
        for(Match<AutoCADRow> match : matches){
            ret += 1.0 - MathUtil.percentError(
                ((AutoCADLine)match.getObj1()).getLength(),
                ((AutoCADLine)match.getObj2()).getLength()
            );
        }
        ret /= matches.size();
        return ret;
    }
    
    @Override
    public double computeScore(AutoCADExport exp1, AutoCADExport exp2) {
        List<AutoCADLine> srcLines = exp1.stream().filter((row)->row instanceof AutoCADLine).map((row)->(AutoCADLine)row).collect(Collectors.toList());
        List<AutoCADLine> cmpLines = exp2.stream().filter((row)->row instanceof AutoCADLine).map((row)->(AutoCADLine)row).collect(Collectors.toList());
        
        double srcTotalLength = srcLines.stream().map((line)->line.getLength()).reduce(0.0, Double::sum);
        double cmpTotalLength = cmpLines.stream().map((line)->line.getLength()).reduce(0.0, Double::sum);
        
        // how do I want to sort the lines? Do I want to sort them outside of this function?
        
        List<Match<AutoCADRow>> closestMatches = new ExportMatcher(exp1, exp2, this::getMatchScore).findMatches();
        
        double avgLenScore = getAvgLengthScore(closestMatches);
        
        return avgLenScore * (1.0 - MathUtil.percentError(srcTotalLength, cmpTotalLength));
    }

    @Override
    public String getDescription() {
        return "Grades the drawing based on how closely the length of lines match the original drawing";
    }

}
