package autocadDrawingChecker.grading;

import autocadDrawingChecker.logging.Logger;

/**
 * MathUtil is a static class used to hold helpful math-related functions
 * needed by grading criteria.
 * 
 * @author Matt Crow
 */
public class MathUtil {
    /**
     * Computes how much a given value differs from the value it's supposed
     * to have.
     * @param whatIExpected the expected value.
     * @param whatIGot the actual value.
     * @return a double between 0.0 and 1.0 <b>Nope!</b>. The greater the difference between
     * the two given parameters, the greater this number will be. This returns 0.0
     * if and only if the two parameters are exactly the same.
     */
    public static double percentError(double whatIExpected, double whatIGot){
        double ret = Math.abs((whatIGot - whatIExpected) / whatIExpected);
        return ret;
    }
    
    
    public static void main(String[] args){
        
    }
}
