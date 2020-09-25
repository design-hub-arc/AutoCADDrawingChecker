package autocadDrawingChecker.data.elements;

/**
 *
 * @author Matt
 */
public class AutoCADPolyline extends AutoCADElement {
    private final double length;
    private final double thickness;
    private final double area;
    private final String closed;
    private final double globalWidth;
    
    private static final double DEFAULT_THICKNESS = 1.0;
    private static final double DEFAULT_AREA = 1.0;
    private static final String DEFAULT_CLOSED = "FALSE";
    private static final double DEFAULT_GLOBAL_WIDTH = 1.0;
    
    public AutoCADPolyline(double len, double thick, double area, String closed, double globalWidth){
        super();
        length = len;
        thickness = thick;
        this.area = area;
        this.closed = closed;
        this.globalWidth = globalWidth;
    }
    public AutoCADPolyline(double len){
        this(len, DEFAULT_THICKNESS, DEFAULT_AREA, DEFAULT_CLOSED, DEFAULT_GLOBAL_WIDTH);
    }
    
    public final double getLength(){
        return length;
    }
    public final double getThickness(){
        return thickness;
    }
    public final double getArea(){
        return area;
    }
    public final String getClosed(){
        return closed;
    }
    public final double getGlobalWidth(){
        return globalWidth;
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("AutoCAD Polyline:");
        sb.append("\n").append(super.toString());
        sb.append(String.format("\n\t* Length: %f", length));
        sb.append(String.format("\n\t* Thickness: %f", thickness));
        sb.append(String.format("\n\t* Area: %f", area));
        sb.append(String.format("\n\t* Closed: %s", closed));
        sb.append(String.format("\n\t* Global Width: %f", globalWidth));
        return sb.toString();
    }
}
