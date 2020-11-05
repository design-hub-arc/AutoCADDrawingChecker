package autocadDrawingChecker.grading.criteria;

import autocadDrawingChecker.data.core.DataSet;
import autocadDrawingChecker.grading.criteria.autoCADCriteria.LinesPerLayer;
import autocadDrawingChecker.grading.criteria.autoCADCriteria.LineStart;
import autocadDrawingChecker.grading.criteria.autoCADCriteria.LineLength;
import autocadDrawingChecker.grading.criteria.autoCADCriteria.LineEnd;
import autocadDrawingChecker.grading.criteria.autoCADCriteria.LineAngle;
import autocadDrawingChecker.grading.criteria.autoCADCriteria.TextMatches;
import autocadDrawingChecker.grading.criteria.surveyCriteria.GradeCoords;
import autocadDrawingChecker.util.AbstractLoader;
import java.util.ArrayList;
import java.util.List;

/**
 * The GradingCriteriaLoader is used
 * to gather all subclasses of AbstractGradingCriteria.
 * 
 * @author Matt Crow
 */
public class GradingCriteriaLoader extends AbstractLoader<AbstractGradingCriteria>{
    @Override
    public final List<AbstractGradingCriteria> getAll(){
        ArrayList<AbstractGradingCriteria> ret = new ArrayList<>();
        ret.add(new LineCount());
        ret.add(new LinesPerLayer());
        ret.add(new LineLength());
        ret.add(new LineAngle());
        ret.add(new LineStart());
        ret.add(new LineEnd());
        ret.add(new TextMatches());
        ret.add(new GradeCoords());
        return ret;
    }
}
