package autocadDrawingChecker.comparison;

import java.util.ArrayList;

/**
 * TODO: make this scan class path
 * @author Matt
 */
public class GradingCriteriaLoader {
    public static final ArrayList<AbstractGradingCriteria> getAllCriteria(){
        ArrayList<AbstractGradingCriteria> ret = new ArrayList<>();
        ret.add(new LineCount());
        ret.add(new LinesPerLayer());
        return ret;
    }
}
