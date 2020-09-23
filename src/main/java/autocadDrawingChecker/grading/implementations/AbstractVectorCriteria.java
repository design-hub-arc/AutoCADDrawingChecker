package autocadDrawingChecker.grading.implementations;

import autocadDrawingChecker.autocadData.elements.AutoCADElement;
import autocadDrawingChecker.grading.AbstractElementCriteria;
import autocadDrawingChecker.grading.MathUtil;

/**
 * AbstractVectorCriteria is used grading based on some multi-element grading
 * criteria. Currently, this just uses the average score of each vector component,
 * but I may change to normalized dot product later.
 * 
 * @author Matt Crow
 * @param <T> the type of AutoCADElement this will grade
 */
public interface AbstractVectorCriteria<T extends AutoCADElement> extends AbstractElementCriteria<T> {
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
     * @param e
     * @return the vector interpretation of the given element,
     * which this will grade on
     */
    public abstract double[] extractVector(T e);
}
