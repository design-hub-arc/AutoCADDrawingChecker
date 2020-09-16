package autocadDrawingChecker.grading.implementations;

import autocadDrawingChecker.autocadData.AutoCADLine;
import autocadDrawingChecker.autocadData.AutoCADElement;

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
    public boolean canGrade(AutoCADElement e) {
        return e instanceof AutoCADLine;
    }

    @Override
    public double[] extractVector(AutoCADElement e) {
        double[] v = new double[AutoCADLine.DIMENSION_COUNT];
        if(e instanceof AutoCADLine){
            for(int i = 0; i < v.length; i++){
                v[i] = ((AutoCADLine)e).getStart(i);
            }
        }
        return v;
    }
}
