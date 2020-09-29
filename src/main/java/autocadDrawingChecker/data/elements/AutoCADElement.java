package autocadDrawingChecker.data.elements;

/**
 * An AutoCADElement represents
 a single record in an AutoCADExport.
 * 
 * @author Matt Crow
 */
public class AutoCADElement extends Record {
    public static final String NAME_COL = "Name";
    public static final String LAYER_COL = "Layer";
    
    public AutoCADElement(){
        super();
    }
    
    public final void setName(String newName){
        this.setAttribute(NAME_COL, newName);
    }
    public final String getName(){
        return (String)getAttribute(NAME_COL);
    }
    
    public final void setLayer(String layer){
        setAttribute(LAYER_COL, layer);
    }
    /**
     * 
     * @return the AutoCAD layer this
     * resides within
     */
    public final String getLayer(){
        return (String)getAttribute(LAYER_COL);
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("AutoCAD Element:");
        sb.append(super.toString());
        return sb.toString();
    }
}
