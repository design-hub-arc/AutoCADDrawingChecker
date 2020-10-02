package autocadDrawingChecker.grading.criteria;

import autocadDrawingChecker.data.AutoCADExport;
import autocadDrawingChecker.logging.Logger;

/**
 *
 * @author Matt
 */
public class CompareAllColumns implements AbstractGradingCriteria {

    @Override
    public double computeScore(AutoCADExport exp1, AutoCADExport exp2) {
        Logger.logError("CompareAllColumns not implemented yet");
        return 0.0;
    }

    @Override
    public String getName() {
        return "Compare All Columns";
    }

    @Override
    public String getDescription() {
        return "Grades all columns in the student files";
    }
}
