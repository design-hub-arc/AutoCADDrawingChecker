package autocadDrawingChecker.grading.implementations;

import autocadDrawingChecker.autocadData.AutoCADElement;
import autocadDrawingChecker.grading.AbstractElementCriteria;
import autocadDrawingChecker.grading.MathUtil;

/**
 * AbstractVectorCriteria is used grading based on some multi-element grading
 * criteria. Currently, this just uses the average score of each vector component,
 * but I may change to normalized dot product later.
 * 
 * @author Matt Crow
 */
public interface AbstractVectorCriteria extends AbstractElementCriteria {
    @Override
    public default double getMatchScore(AutoCADElement e1, AutoCADElement e2){
        double score = 0.0;
        double[] v1;
        double[] v2;
        int numComponents;
        if(canGrade(e1) && canGrade(e2)){
            v1 = extractVector(e1);
            v2 = extractVector(e2);
            numComponents = Math.min(v1.length, v2.length);
            for(int i = 0; i < numComponents; i++){
                score += 1.0 - MathUtil.percentError(v1[i], v2[i]);
            }
            score /= numComponents; // average percent correct
        }
        return score;
    }
    
    /**
     * 
     * @param e
     * @return whether or not this can grade the given element.
     * If it cannot, getMatchScore will always return 0.0 when
     * the given element is passed as a parameter.
     */
    public abstract boolean canGrade(AutoCADElement e);
    
    /**
     * 
     * @param e
     * @return the vector interpretation of the given element,
     * which this will grade on
     */
    public abstract double[] extractVector(AutoCADElement e);
}
