package autocadDrawingChecker.data.elements;

import java.util.HashMap;

/**
 * An AutoCADElement represents
 a single record in an AutoCADExport.
 * 
 * @author Matt Crow
 */
public class AutoCADElement {
    private final HashMap<String, Object> attributes;
    
    public static final String NAME_COL = "Name";
    public static final String LAYER_COL = "Layer";
    
    public AutoCADElement(){
        this.attributes = new HashMap<>();
    }
    
    public final void setName(String newName){
        this.setAttribute(NAME_COL, newName);
    }
    public final String getName(){
        return getAttributeString(NAME_COL);
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
    
    private String sanitizeAttributeName(String name){
        return name.trim().toUpperCase();
    }
    
    public final AutoCADElement setAttribute(String attrName, Object value){
        attributes.put(sanitizeAttributeName(attrName), value);
        return this;
    }
    public final boolean hasAttribute(String attributeName){
        return attributes.containsKey(sanitizeAttributeName(attributeName));
    }
    public final Object getAttribute(String attributeName){
        if(!hasAttribute(attributeName)){
            throw new NullPointerException();
        }
        return attributes.get(sanitizeAttributeName(attributeName));
    }
    public final String getAttributeString(String attributeName){
        return getAttribute(attributeName).toString();
    }
    public final double getAttributeDouble(String attributeName){
        return Double.parseDouble(getAttributeString(attributeName));
    }
    public final int getAttributeInt(String attributeName){
        return (int)getAttributeDouble(attributeName);
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("AutoCAD Element:");
        attributes.forEach((k, v)->{
            sb.append(String.format("\n- %s : %s", k, v.toString()));
        });
        return sb.toString();
    }
}
