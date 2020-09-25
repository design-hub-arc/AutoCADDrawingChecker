package autocadDrawingChecker.grading.implementations;

import autocadDrawingChecker.data.elements.AutoCADLine;
import autocadDrawingChecker.data.elements.AutoCADElement;

/**
 *
 * @author Matt
 */
public class LineEnd implements AbstractVectorCriteria<AutoCADLine> {
    @Override
    public String getDescription() {
        return "Grades based on how closesly the student's line end points match up with those of the instructor's";
    }

    @Override
    public String getName() {
        return "Line End";
    }

    @Override
    public double[] extractVector(AutoCADLine e) {
        double[] v = new double[AutoCADLine.DIMENSION_COUNT];
        for(int i = 0; i < v.length; i++){
            v[i] = e.getEnd(i);
        }
        return v;
    }

    @Override
    public AutoCADLine cast(AutoCADElement e) {
        return (e instanceof AutoCADLine) ? (AutoCADLine)e : null;
    }
}
