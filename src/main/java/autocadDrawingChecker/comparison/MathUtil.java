package autocadDrawingChecker.comparison;

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
        System.out.println(percentError(1, 1));
        System.out.println(percentError(1, 2));
        System.out.println(percentError(2, 1));
    }
}
