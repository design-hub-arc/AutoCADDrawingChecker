package autocadDrawingChecker.autocadData;

/**
 *
 * @author Matt
 */
public class AutoCADPolyline extends AutoCADElement {
    private final double length;
    private final double thickness;
    private final double area;
    private final int closed;
    private final double globalWidth;
    
    public AutoCADPolyline(double len, double thick, double area, int closed, double globalWidth){
        super();
        length = len;
        thickness = thick;
        this.area = area;
        this.closed = closed;
        this.globalWidth = globalWidth;
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
    public final int getClosed(){
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
        sb.append(String.format("\n\t* Closed: %d", closed));
        sb.append(String.format("\n\t* Global Width: %f", globalWidth));
        return sb.toString();
    }
}
