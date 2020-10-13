package autocadDrawingChecker.grading.criteria;

import autocadDrawingChecker.data.core.ExtractedSpreadsheetContents;
import autocadDrawingChecker.data.core.SpreadsheetRecord;

/**
 * AbstractVectorCriteria is used grading based on some multi-element grading
 * criteria. Currently, this just uses the average score of each vector component,
 * but I may change to normalized dot product later.
 * 
 * @author Matt Crow
 */
public interface AbstractVectorCriteria<T extends SpreadsheetRecord, U extends ExtractedSpreadsheetContents> extends AbstractElementCriteria<T, U> {
    @Override
    public default double getMatchScore(T e1, T e2){
        double score = 0.0;
        double[] v1 = extractVector(e1);
        double[] v2 = extractVector(e2);
        int numComponents = Math.min(v1.length, v2.length);
        
        for(int i = 0; i < numComponents; i++){
            score += (v1[i] == v2[i]) ? 1.0 : 0.0;//1.0 - MathUtil.percentError(v1[i], v2[i]);
        }
        score /= numComponents; // average percent correct
        
        return score;
    }
    
    /**
     * 
     * @param e the element to extract a vector from
     * @return the vector interpretation of the given element,
     * which this will grade on
     */
    public abstract double[] extractVector(T e);
}
