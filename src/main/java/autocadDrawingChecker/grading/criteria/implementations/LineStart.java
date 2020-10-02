package autocadDrawingChecker.grading.criteria.implementations;

import autocadDrawingChecker.data.elements.AutoCADElement;

/**
 *
 * @author Matt Crow
 */
public class LineStart implements AbstractVectorCriteria {
    @Override
    public String getDescription() {
        return "Grades based on how closesly the student's line start points match up with those of the instructor's";
    }

    @Override
    public String getName() {
        return "Line Start";
    }

    @Override
    public double[] extractVector(AutoCADElement e) {
        double[] v = new double[]{
            e.getAttributeDouble("start x"),
            e.getAttributeDouble("start y"),
            e.getAttributeDouble("start z")
        };
        return v;
    }

    @Override
    public String[] getAllowedTypes(){
        return new String[]{"Line"};
    }
}
