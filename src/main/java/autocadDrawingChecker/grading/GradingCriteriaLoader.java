package autocadDrawingChecker.grading;

import java.util.ArrayList;

/**
 * The GradingCriteriaLoader is used
 * to gather all subclasses of AbstractGradingCriteria.
 * 
 * TODO: make this scan class path
 * @author Matt Crow
 */
public class GradingCriteriaLoader {
    public static final ArrayList<AbstractGradingCriteria> getAllCriteria(){
        ArrayList<AbstractGradingCriteria> ret = new ArrayList<>();
        ret.add(new LineCount());
        ret.add(new LinesPerLayer());
        ret.add(new LineLength());
        ret.add(new LineAngle());
        ret.add(new LineStart());
        ret.add(new LineEnd());
        return ret;
    }
}
