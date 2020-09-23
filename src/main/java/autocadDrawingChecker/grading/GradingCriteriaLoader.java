package autocadDrawingChecker.grading;

import autocadDrawingChecker.grading.implementations.CheckDims;
import autocadDrawingChecker.grading.implementations.LinesPerLayer;
import autocadDrawingChecker.grading.implementations.LineStart;
import autocadDrawingChecker.grading.implementations.LineLength;
import autocadDrawingChecker.grading.implementations.LineEnd;
import autocadDrawingChecker.grading.implementations.LineCount;
import autocadDrawingChecker.grading.implementations.LineAngle;
import autocadDrawingChecker.grading.implementations.TextMatches;
import autocadDrawingChecker.util.AbstractLoader;
import java.util.ArrayList;

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
            new CheckDims(),
            new TextMatches()
        };
    }
}
