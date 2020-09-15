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
     * @param whatIExpected the expected value. If this is 0.0, returns 1.0 iff 
     * whatIGot is also 0.0, else returns 1.0.
     * @param whatIGot the actual value.
     * @return a double between 0.0 and 1.0. The greater the difference between
     * the two given parameters, the greater this number will be. This returns 0.0
     * if and only if the two parameters are exactly the same. <b>Any percent
     * error greater than 1.0 is rounded down to 1.0.
     */
    public static double percentError(double whatIExpected, double whatIGot){
        double ret = 0.0;
        if(whatIExpected == 0.0){
            ret = (whatIGot == 0.0) ? 0.0 : 1.0;
        } else {
            ret = Math.abs((whatIGot - whatIExpected) / whatIExpected);
        }
        if(ret > 1.0){
            ret = 1.0;
        }
        return ret;
    }
    
    public static int rotate180(int theta){
        return (180 + theta) % 360;
    }
    
    public static void main(String[] args){
        
    }
}
