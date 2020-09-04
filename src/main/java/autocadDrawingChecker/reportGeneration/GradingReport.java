package autocadDrawingChecker.reportGeneration;

import autocadDrawingChecker.grading.ExportComparison;
import java.util.LinkedList;

/**
 *
 * @author Matt
 */
public class GradingReport extends LinkedList<ExportComparison> {
    public GradingReport(){
        super();
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Grading report:");
        forEach((expComp)->{
            sb.append("\n").append(expComp.toString());
        });
        return sb.toString();
    }
}
