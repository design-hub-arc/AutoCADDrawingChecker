package autocadDrawingChecker.grading.criteria.implementations;

import autocadDrawingChecker.data.elements.AutoCADElement;

/**
 *
 * @author Matt
 */
public class LineEnd implements AbstractVectorCriteria {
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
