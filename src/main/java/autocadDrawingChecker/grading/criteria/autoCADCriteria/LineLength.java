package autocadDrawingChecker.grading.criteria.autoCADCriteria;

import autocadDrawingChecker.data.autoCADData.AutoCADExport;
import autocadDrawingChecker.data.autoCADData.AutoCADElement;
import autocadDrawingChecker.data.core.SpreadsheetRecord;
import autocadDrawingChecker.grading.criteria.AbstractElementCriteria;

/**
 * 
 * @author Matt Crow
 */
public class LineLength implements AbstractElementCriteria<AutoCADElement, AutoCADExport>, AbstractAutoCADElementCriteria {
    
    @Override
    public double getMatchScore(AutoCADElement r1, AutoCADElement r2){
        return  (r1.getAttributeDouble("length") == r2.getAttributeDouble("length")) ? 1.0 : 0.0;
    }
    
    private double getTotalLineLength(AutoCADExport exp){
        return exp.stream().filter((SpreadsheetRecord e)->{
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
        double totalLengthScore = (srcTotalLength == cmpTotalLength) ? 1.0 : 0.0;
        return (avgLenScore + totalLengthScore) / 2;//* (1.0 - MathUtil.percentError(srcTotalLength, cmpTotalLength));
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
