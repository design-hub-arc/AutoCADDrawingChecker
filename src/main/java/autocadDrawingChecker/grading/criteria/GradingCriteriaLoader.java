package autocadDrawingChecker.grading.criteria;

import autocadDrawingChecker.grading.criteria.implementations.CheckDims;
import autocadDrawingChecker.grading.criteria.implementations.LinesPerLayer;
import autocadDrawingChecker.grading.criteria.implementations.LineStart;
import autocadDrawingChecker.grading.criteria.implementations.LineLength;
import autocadDrawingChecker.grading.criteria.implementations.LineEnd;
import autocadDrawingChecker.grading.criteria.implementations.LineCount;
import autocadDrawingChecker.grading.criteria.implementations.LineAngle;
import autocadDrawingChecker.grading.criteria.implementations.TextMatches;
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
            //new LineCount(),
            new LinesPerLayer(),
            new LineLength(),
            new LineAngle(),
            new LineStart(),
            new LineEnd(),
            new CheckDims(),
            new TextMatches()//,
            //new CompareAllColumns()
        };
    }
}
