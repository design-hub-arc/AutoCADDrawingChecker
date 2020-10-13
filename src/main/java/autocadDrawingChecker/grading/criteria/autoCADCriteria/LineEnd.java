package autocadDrawingChecker.grading.criteria.autoCADCriteria;

import autocadDrawingChecker.grading.criteria.AbstractVectorCriteria;
import autocadDrawingChecker.data.autoCADData.AutoCADElement;
import autocadDrawingChecker.data.autoCADData.AutoCADExport;
import autocadDrawingChecker.data.core.SpreadsheetRecord;

/**
 *
 * @author Matt
 */
public class LineEnd implements AbstractVectorCriteria<AutoCADElement, AutoCADExport> {
    @Override
    public String getDescription() {
        return "Grades based on how closesly the student's line end points match up with those of the instructor's";
    }

    @Override
    public String getName() {
        return "Line End";
    }

    @Override
    public double[] extractVector(AutoCADElement e) {
        double[] v = new double[]{
            e.getAttributeDouble("end x"),
            e.getAttributeDouble("end y"),
            e.getAttributeDouble("end y")
        };
        return v;
    }

    @Override
    public String[] getAllowedTypes() {
        return new String[]{"Line"};
        
    }

    @Override
    public AutoCADElement tryCast(SpreadsheetRecord rec) {
        return (rec instanceof AutoCADElement) ? (AutoCADElement)rec : null;
    }
}
