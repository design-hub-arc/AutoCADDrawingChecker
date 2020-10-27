package autocadDrawingChecker.grading.criteria;

import autocadDrawingChecker.data.core.DataSet;
import autocadDrawingChecker.grading.MathUtil;

/**
 * @author Matt Crow
 */
public class LineCount implements AbstractGradingCriteria<DataSet> {

    @Override
    public double computeScore(DataSet exp1, DataSet exp2) {
        return MathUtil.gradeSimilarity(exp1.size(), exp2.size());
    }

    @Override
    public String getDescription() {
        return "Grades the student on how close their number of lines match with that of the comparison file.";
    }

    @Override
    public String getName() {
        return "Line count";
    }

    @Override
    public DataSet tryCastDataSet(DataSet contents) {
        return (contents != null) ? contents : null;
    }
}
