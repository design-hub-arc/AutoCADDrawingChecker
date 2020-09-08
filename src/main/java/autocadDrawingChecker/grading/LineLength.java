package autocadDrawingChecker.grading;

import autocadDrawingChecker.autocadData.AutoCADExport;

/**
 *
 * @author Matt Crow
 */
public class LineLength extends AbstractGradingCriteria {
    
    public LineLength(){
        super("Line Length");
    }
    
    @Override
    public double computeScore(AutoCADExport exp1, AutoCADExport exp2) {
        return 0.0;
    }

    @Override
    public String getDescription() {
        return "Grades the drawing based on how closely the length of lines match the original drawing";
    }

}
