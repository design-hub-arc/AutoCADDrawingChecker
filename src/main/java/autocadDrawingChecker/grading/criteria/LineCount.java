package autocadDrawingChecker.grading.criteria;

import autocadDrawingChecker.data.core.ExtractedSpreadsheetContents;

/**
 * @author Matt Crow
 */
public class LineCount implements AbstractGradingCriteria<ExtractedSpreadsheetContents> {

    @Override
    public double computeScore(ExtractedSpreadsheetContents exp1, ExtractedSpreadsheetContents exp2) {
        return (exp1.size() == exp2.size()) ? 1.0 : 0.0;//1.0 - MathUtil.percentError(exp1.size(), exp2.size());
    }

    @Override
    public String getDescription() {
        return "Grades the student on how close their number of lines match with that of the comparison file.";
    }

    @Override
    public String getName() {
        return "Line count";
    }

}
