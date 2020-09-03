package autocadDrawingChecker.comparison;

import autocadDrawingChecker.autocadData.AutoCADExport;

/**
 *
 * @author Matt
 */
public class LineCount extends AbstractGradingCriteria {

    public LineCount() {
        super("Line count");
    }

    @Override
    public double computeScore(AutoCADExport exp1, AutoCADExport exp2) {
        return 1.0 - MathUtil.percentError(exp1.size(), exp2.size());
    }

    @Override
    public String getDescription() {
        return "Grades the student on how close their number of lines match with that of the comparison file.";
    }

}
