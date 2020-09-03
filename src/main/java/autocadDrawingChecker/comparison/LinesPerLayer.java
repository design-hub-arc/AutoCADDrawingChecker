package autocadDrawingChecker.comparison;

import autocadDrawingChecker.autocadData.AutoCADExport;
import java.util.HashMap;

/**
 *
 * @author Matt
 */
public class LinesPerLayer extends AbstractGradingCriteria {

    public LinesPerLayer() {
        super("Lines per layer");
    }

    @Override
    public double computeScore(AutoCADExport exp1, AutoCADExport exp2) {
        double score = 0.0;
        HashMap<String, Integer> srcLines = exp1.getLayerLineCounts();
        HashMap<String, Integer> cmpLines = exp2.getLayerLineCounts();
        
        for(String layer : srcLines.keySet()){
            if(cmpLines.containsKey(layer)){
                score += MathUtil.percentError(srcLines.get(layer), cmpLines.get(layer));
            }
        }
        return 1.0 - (score / srcLines.size());
    }

    @Override
    public String getDescription() {
        return "Grades the student on how close the number of lines in each of their layers matches up with the comparison file's equivalent layer.";
    }

}
