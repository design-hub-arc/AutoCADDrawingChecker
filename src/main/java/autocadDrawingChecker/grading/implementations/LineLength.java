package autocadDrawingChecker.grading.implementations;

import autocadDrawingChecker.autocadData.AutoCADExport;
import autocadDrawingChecker.autocadData.AutoCADLine;
import autocadDrawingChecker.autocadData.elements.AutoCADElement;
import autocadDrawingChecker.grading.AbstractElementCriteria;
import autocadDrawingChecker.grading.MathUtil;

/**
 * Looks like this works!
 * 
 * @author Matt Crow
 */
public class LineLength implements AbstractElementCriteria<AutoCADLine> {
    
    @Override
    public double getMatchScore(AutoCADLine r1, AutoCADLine r2){
        return  1.0 - MathUtil.percentError(r1.getLength(), r2.getLength());
    }
    
    private double getTotalLineLength(AutoCADExport exp){
        return exp.stream().filter((AutoCADElement e)->{
            return e instanceof AutoCADLine;
        }).map((AutoCADElement imReallyALine)->{
            return (AutoCADLine)imReallyALine;
        }).map((AutoCADLine line)->{
            return line.getLength();
        }).reduce(0.0, Double::sum);
    }
    
    @Override
    public double computeScore(AutoCADExport exp1, AutoCADExport exp2) {
        double srcTotalLength = getTotalLineLength(exp1);
        double cmpTotalLength = getTotalLineLength(exp2);
        double avgLenScore = AbstractElementCriteria.super.computeScore(exp1, exp2);
        
        return avgLenScore * (1.0 - MathUtil.percentError(srcTotalLength, cmpTotalLength));
    }

    @Override
    public String getDescription() {
        return "Grades the drawing based on how closely the length of lines match the original drawing";
    }

    @Override
    public String getName() {
        return "Line Length";
    }

    @Override
    public AutoCADLine cast(AutoCADElement e) {
        return (e instanceof AutoCADLine) ? (AutoCADLine)e : null;
    }

}
