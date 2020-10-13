package autocadDrawingChecker.grading.criteria.autoCADCriteria;

import autocadDrawingChecker.data.autoCADData.AutoCADExport;
import autocadDrawingChecker.grading.criteria.AbstractGradingCriteria;
import java.util.HashMap;
import java.util.Objects;

/**
 *
 * @author Matt Crow
 */
public class LinesPerLayer implements AbstractGradingCriteria<AutoCADExport> {

    @Override
    public double computeScore(AutoCADExport exp1, AutoCADExport exp2) {
        double score = 0.0;
        HashMap<Object, Integer> srcLines = exp1.getCountsPerColumnValue("Layer");//exp1.getLayerLineCounts();
        HashMap<Object, Integer> cmpLines = exp2.getCountsPerColumnValue("Layer");//exp2.getLayerLineCounts();
        
        for(Object layer : srcLines.keySet()){
            if(cmpLines.containsKey(layer) && Objects.equals(cmpLines.get(layer), srcLines.get(layer))){
                score++; // only gives points on layers which contain the exact same number of lines
            }
            /*
            if(cmpLines.containsKey(layer)){
                score += MathUtil.percentError(srcLines.get(layer), cmpLines.get(layer));
            }
            */
        }
        return score / srcLines.size();
        //return 1.0 - (score / srcLines.size());
    }

    @Override
    public String getDescription() {
        return "Grades the student on how close the number of lines in each of their layers matches up with the comparison file's equivalent layer.";
    }

    @Override
    public String getName() {
        return "Lines per layer";
    }

}
