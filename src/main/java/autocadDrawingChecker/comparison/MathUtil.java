package autocadDrawingChecker.comparison;

import autocadDrawingChecker.logging.Logger;

/**
 *
 * @author Matt
 */
public class MathUtil {
    /**
     * 
     * @param whatIExpected
     * @param whatIGot
     * @return a double between 0.0 and 1.0 
     */
    public static double percentError(double whatIExpected, double whatIGot){
        return Math.abs((whatIGot - whatIExpected) / whatIExpected);
    }
    
    public static void main(String[] args){
        Logger.log(percentError(1, 1));
        Logger.log(percentError(1, 2));
        Logger.log(percentError(2, 1));
    }
}
