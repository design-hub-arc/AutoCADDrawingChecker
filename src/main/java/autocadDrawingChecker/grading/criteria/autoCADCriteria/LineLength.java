package autocadDrawingChecker.grading.criteria.autoCADCriteria;

import autocadDrawingChecker.data.excel.autoCADData.AutoCADExport;
import autocadDrawingChecker.data.excel.autoCADData.AutoCADElement;
import autocadDrawingChecker.data.core.Record;
import autocadDrawingChecker.grading.MathUtil;

/**
 * 
 * @author Matt Crow
 */
public class LineLength implements AbstractAutoCADElementCriteria {
    
    @Override
    public double getMatchScore(AutoCADElement r1, AutoCADElement r2){
        return  MathUtil.gradeSimilarity(r1.getAttributeDouble("length"), r2.getAttributeDouble("length"));
    }
    
    private double getTotalLineLength(AutoCADExport exp){
        return exp.stream().filter((Record e)->{
            return e instanceof AutoCADElement; 
        }).map((rec)->{
            return (AutoCADElement)rec;
        }).filter(this::canAccept).map((AutoCADElement imReallyALine)->{
            return imReallyALine.getAttributeDouble("length");
        }).reduce(0.0, Double::sum);
    }
    
    @Override
    public double computeScore(AutoCADExport exp1, AutoCADExport exp2) {
        double srcTotalLength = getTotalLineLength(exp1);
        double cmpTotalLength = getTotalLineLength(exp2);
        double avgLenScore = AbstractAutoCADElementCriteria.super.computeScore(exp1, exp2);
        double totalLengthScore = MathUtil.gradeSimilarity(srcTotalLength, cmpTotalLength);
        return (avgLenScore + totalLengthScore) / 2;
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
    public String[] getAllowedTypes() {
        return new String[]{"Line"};
    }
}
