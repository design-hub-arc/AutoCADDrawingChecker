package autocadDrawingChecker.autocadData.elements;

import java.util.Arrays;

/**
 *
 * @author Matt
 */
public class AutoCADCircle extends AutoCADElement {
    private final double[] center;
    private final double radius;
    
    public AutoCADCircle(double[] center, double radius){
        super();
        this.center = Arrays.copyOf(center, center.length);
        this.radius = radius;
    }
}
