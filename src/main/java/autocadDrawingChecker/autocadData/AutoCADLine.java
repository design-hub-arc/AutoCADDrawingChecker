package autocadDrawingChecker.autocadData;

/**
 * The AutoCADLine class is used to differentiate 
 * between geometric lines and other AutoCAD elements.
 * 
 * I'm not quite sure how AutoCAD works, so I
 * don't know if I'll need different classes
 * for each element type.
 * 
 * @author Matt Crow
 */
public class AutoCADLine extends AutoCADRow {
    private final double[] r0; // r naught
    private final double[] r; // r final
    
    public AutoCADLine(String layerName, double[] start, double[] end) {
        super(layerName);
        r0 = start;
        r = end;
    }

    /**
     * 
     * @return the length of this line.
     */
    public final double getLength(){
        double inRoot = 0.0;
        for(int i = 0; i < r0.length && i < r.length; i++){
            inRoot += Math.pow(r[i] - r0[i], 2);
        }
        return Math.sqrt(inRoot);
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("AutoCAD Line:");
        sb.append("\n").append(super.toString());
        sb.append(String.format("\n\t* Line from (%f, %f, %f) to (%f, %f, %f)", r0[0], r0[1], r0[2], r[0], r[1], r[2]));
        return sb.toString();
    }
}
