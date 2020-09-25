package autocadDrawingChecker.data.elements;

/**
 * An AutoCADElement represents
 a single record in an AutoCADExport.
 * 
 * @author Matt Crow
 */
public class AutoCADElement {
    private int count;
    private String name;
    private String color;
    private String inLayer;
    private String lineType;
    private double lineTypeScale;
    private String lineWeight;
    private String plot;
    
    public AutoCADElement(){
        
    }
    
    public final void setCount(int c){
        count = c;
    }
    public final int getCount(){
        return count;
    }
    
    public final void setName(String newName){
        name = newName;
    }
    public final String getName(){
        return name;
    }
    
    public final void setColor(String colorType){
        color = colorType;
    }
    public final String getColor(){
        return color;
    }
    
    public final void setLayer(String layer){
        inLayer = layer;
    }
    /**
     * 
     * @return the AutoCAD layer this
     * resides within
     */
    public final String getLayer(){
        return inLayer;
    }
    
    public final void setLineType(String type){
        lineType = type;
    }
    public final String getLineType(){
        return lineType;
    }
    
    public final void setLineTypeScale(double scale){
        lineTypeScale = scale;
    }
    public final double getLineTypeScale(){
        return lineTypeScale;
    }
    
    public final void setLineWeight(String weight){
        lineWeight = weight;
    }
    public final String getLineWeight(){
        return lineWeight;
    }
    
    public final void setPlot(String plotType){
        plot = plotType;
    }
    public final String getPlot(){
        return plot;
    }
    
    private String blankIfNull(String s){
        return (s == null) ? "" : s;
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("AutoCAD Element:");
        sb.append(String.format("\n\t* Count: %d", count));
        sb.append(String.format("\n\t* Name: %s", blankIfNull(name)));
        sb.append(String.format("\n\t* Color: %s", blankIfNull(color)));
        sb.append(String.format("\n\t* Layer: %s", blankIfNull(inLayer)));
        sb.append(String.format("\n\t* Line Type: %s", blankIfNull(lineType)));
        sb.append(String.format("\n\t* Line Type Scale: %f", lineTypeScale));
        sb.append(String.format("\n\t* Line Weight: %s", blankIfNull(lineWeight)));
        sb.append(String.format("\n\t* Plot: %s", blankIfNull(plot)));
        return sb.toString();
    }
}
