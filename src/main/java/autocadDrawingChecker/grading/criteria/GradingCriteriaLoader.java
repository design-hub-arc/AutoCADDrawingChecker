package autocadDrawingChecker.grading.criteria;

import autocadDrawingChecker.grading.criteria.autoCADCriteria.LinesPerLayer;
import autocadDrawingChecker.grading.criteria.autoCADCriteria.LineStart;
import autocadDrawingChecker.grading.criteria.autoCADCriteria.LineLength;
import autocadDrawingChecker.grading.criteria.autoCADCriteria.LineEnd;
import autocadDrawingChecker.grading.criteria.autoCADCriteria.LineAngle;
import autocadDrawingChecker.grading.criteria.autoCADCriteria.TextMatches;
import autocadDrawingChecker.util.AbstractLoader;

/**
 * The GradingCriteriaLoader is used
 * to gather all subclasses of AbstractGradingCriteria.
 * 
 * @author Matt Crow
 */
public class GradingCriteriaLoader extends AbstractLoader<AbstractGradingCriteria>{
    @Override
    public final AbstractGradingCriteria[] getAll(){
        return new AbstractGradingCriteria[]{
            new LineCount(),
            new LinesPerLayer(),
            new LineLength(),
            new LineAngle(),
            new LineStart(),
            new LineEnd(),
            new TextMatches()
        };
    }
}
