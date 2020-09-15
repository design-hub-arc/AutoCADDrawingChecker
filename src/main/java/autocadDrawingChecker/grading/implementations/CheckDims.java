package autocadDrawingChecker.grading.implementations;

import autocadDrawingChecker.autocadData.AutoCADDimension;
import autocadDrawingChecker.autocadData.AutoCADElement;
import autocadDrawingChecker.autocadData.AutoCADElementMatcher;
import autocadDrawingChecker.autocadData.AutoCADExport;
import autocadDrawingChecker.autocadData.MatchingAutoCADElements;
import autocadDrawingChecker.grading.AbstractGradingCriteria;
import autocadDrawingChecker.grading.MathUtil;
import java.util.List;

/**
 *
 * @author Matt
 */
public class CheckDims implements AbstractGradingCriteria {

    @Override
    public String getName() {
        return "Check Dimensions";
    }
    
    private double getMatchScore(AutoCADElement row1, AutoCADElement row2){
        double ret = 0.0;
        if(row1 instanceof AutoCADDimension && row2 instanceof AutoCADDimension){
            AutoCADDimension d1 = (AutoCADDimension)row1;
            AutoCADDimension d2 = (AutoCADDimension)row2;
            // grade on 3 things...
            ret += 1.0 - MathUtil.percentError(d1.getDynamicDimension(), d2.getDynamicDimension());
            ret += (d1.getDimensionStyle().equals(d2.getDimensionStyle())) ? 1.0 : 0.0;
            ret += (d1.getTextDefinedSize().equals(d2.getTextDefinedSize())) ? 1.0 : 0.0;
            // ... so take the average
            ret /= 3.0;
        }
        return ret;
    }

    @Override
    public double computeScore(AutoCADExport exp1, AutoCADExport exp2) {
        List<MatchingAutoCADElements> matches = new AutoCADElementMatcher(exp1, exp2, this::getMatchScore).findMatches();
        double netScore = 0.0;
        netScore = matches.stream().map((match)->{
            return getMatchScore(match.getElement1(), match.getElement2());
        }).reduce(netScore, (accumulator, score) -> accumulator + score);
        if(!matches.isEmpty()){
            netScore /= matches.size();
        }
        return netScore;
    }

    @Override
    public String getDescription() {
        return "Checks the dimensions of a student export against those of the instructor";
    }

}
