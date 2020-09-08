package autocadDrawingChecker.grading;

import autocadDrawingChecker.autocadData.AutoCADExport;
import autocadDrawingChecker.autocadData.AutoCADLine;
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
    
    @Override
    public double computeScore(AutoCADExport exp1, AutoCADExport exp2) {
        List<AutoCADLine> srcLines = exp1.stream().filter((row)->row instanceof AutoCADLine).map((row)->(AutoCADLine)row).collect(Collectors.toList());
        List<AutoCADLine> cmpLines = exp2.stream().filter((row)->row instanceof AutoCADLine).map((row)->(AutoCADLine)row).collect(Collectors.toList());
        
        double srcTotalLength = srcLines.stream().map((line)->line.getLength()).reduce(0.0, Double::sum);
        double cmpTotalLength = cmpLines.stream().map((line)->line.getLength()).reduce(0.0, Double::sum);
        // how do I want to sort the lines? Do I want to sort them outside of this function?
        
        
        return 1.0 - MathUtil.percentError(srcTotalLength, cmpTotalLength); // currently just total length
    }

    @Override
    public String getDescription() {
        return "Grades the drawing based on how closely the length of lines match the original drawing";
    }

}
