package autocadDrawingChecker.data.elements;

import java.util.Arrays;

/**
 * The AutoCADLine class is used to differentiate 
 * between geometric lines and other AutoCAD elements.
 * 
 * @author Matt Crow
 */
public class AutoCADLine extends AutoCADElement {
    private final int angle;
    private final double[] deltas;
    private final double[] r0; // r naught
    private final double[] r; // r final
    private final double length;
    private final double thickness;
    
    public static final int DIMENSION_COUNT = 3;
    public static final double DEFAULT_THICKNESS = 1.0;
    
    public AutoCADLine(int theta, double[] start, double[] end, double[] ds, double len, double thick) {
        super();
        angle = theta;
        r0 = Arrays.copyOf(start, DIMENSION_COUNT);
        r = Arrays.copyOf(end, DIMENSION_COUNT);
        deltas = Arrays.copyOf(ds, DIMENSION_COUNT);
        length = len;
        thickness = thick;
    }
    
    public AutoCADLine(int theta, double[] start, double[] end, double[] ds, double len){
        this(theta, start, end, ds, len, DEFAULT_THICKNESS);
    }
    
    public final double getStart(int dimension){
        return r0[dimension];
    }
    public final double getEnd(int dimension){
        return r[dimension];
    }
    public final double getDelta(int dimension){
        return deltas[dimension];
    }

    /**
     * 
     * @return the physical length of this line.
     */
    public final double getLength(){
        return length;
    }
    
    public final double getThickness(){
        return thickness;
    }
    
    /**
     * 
     * @return the angle between this line
     * and the x-axis, measured in degrees
     */
    public final int getAngle(){
        return angle;
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("AutoCAD Line:");
        sb.append("\n").append(super.toString());
        sb.append(String.format("\n\t* Angle: %d", angle));
        sb.append(String.format("\n\t* Start: (%f, %f, %f)", r0[0], r0[1], r0[2]));
        sb.append(String.format("\n\t* End: (%f, %f, %f)", r[0], r[1], r[2]));
        sb.append(String.format("\n\t* Deltas: (%f, %f, %f)", deltas[0], deltas[1], deltas[2]));
        sb.append(String.format("\n\t* Length: %f", length));
        sb.append(String.format("\n\t* Thickness: %f", thickness));
        return sb.toString();
    }
}
