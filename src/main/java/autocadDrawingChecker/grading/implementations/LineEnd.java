package autocadDrawingChecker.grading.implementations;

import autocadDrawingChecker.autocadData.AutoCADLine;
import autocadDrawingChecker.autocadData.AutoCADElement;

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
    public boolean canGrade(AutoCADElement e) {
        return e instanceof AutoCADLine;
    }

    @Override
    public double[] extractVector(AutoCADElement e) {
        double[] v = new double[AutoCADLine.DIMENSION_COUNT];
        if(e instanceof AutoCADLine){
            for(int i = 0; i < v.length; i++){
                v[i] = ((AutoCADLine)e).getEnd(i);
            }
        }
        return v;
    }
}
