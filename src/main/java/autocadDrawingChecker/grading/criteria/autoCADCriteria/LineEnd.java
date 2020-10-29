package autocadDrawingChecker.grading.criteria.autoCADCriteria;

import autocadDrawingChecker.grading.criteria.AbstractVectorCriteria;
import autocadDrawingChecker.data.excel.autoCADData.AutoCADElement;
import autocadDrawingChecker.data.excel.autoCADData.AutoCADExport;

/**
 *
 * @author Matt
 */
public class LineEnd implements AbstractVectorCriteria<AutoCADExport, AutoCADElement>, AbstractAutoCADElementCriteria {
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
}
