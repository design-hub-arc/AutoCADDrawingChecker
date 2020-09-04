package autocadDrawingChecker.grading;

import java.util.LinkedList;

/**
 * The GradingReport class is used to bundle a set of graded exports
 * together so they can be outputted together into one file. This
 * class will likely be rewritten in later versions to write the
 * report in a more easily readable format.
 * 
 * @author Matt Crow
 */
public class GradingReport extends LinkedList<ExportComparison> {
    public GradingReport(){
        super();
    }
    
    /**
     * Use this method to get the text
     * to write as a .txt file when the user
     * saves it.
     * 
     * @return the contents of all the ExportComparisons
     * contained herein. 
     */
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
