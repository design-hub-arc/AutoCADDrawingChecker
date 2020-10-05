package autocadDrawingChecker.data;

import autocadDrawingChecker.logging.Logger;
import java.util.HashMap;
import java.util.Set;

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
    
    // no access modifier, so only classes in this package can invoke this
    AutoCADElement(){
        this.attributes = new HashMap<>();
    }
    
    public final Set<String> getAttributes(){
        return attributes.keySet();
    }
    
    /**
     * 
     * @return the value in this' "Name" column.
     * This value is the type of AutoCAD element this is
     * (line, circle, etc.)
     */
    public final String getName(){
        return getAttributeString(NAME_COL);
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
    
    final AutoCADElement setAttribute(String attrName, Object value){
        attributes.put(sanitizeAttributeName(attrName), value);
        return this;
    }
    
    /**
     * Checks to see if this has the given attribute
     * @param attributeName the name to check for
     * @return whether or not this has the given attribute
     */
    public final boolean hasAttribute(String attributeName){
        return attributes.containsKey(sanitizeAttributeName(attributeName));
    }
    
    /**
     * Gets this' attribute with the given name.
     * If this has no such attribute, throws an exception.
     * You should use getAttributeString(), getAttributeDouble(), or getAttributeInt()
     * if you want the attribute in another format.
     * 
     * @param attributeName the name of the attribute to get.
     * @return the given attribute as an Object
     * @throws NullPointerException if this has no attribute with the given name.
     */
    public final Object getAttribute(String attributeName) throws NullPointerException {
        if(!hasAttribute(attributeName)){
            throw new NullPointerException(String.format("Doesn't have attribute \"%s\"", attributeName));
        }
        return attributes.get(sanitizeAttributeName(attributeName));
    }
    
    /**
     * 
     * @param attributeName the name of the attribute to get
     * @return the attribute with the given name
     * @throws NullPointerException if this has no such attribute with the given name
     */
    public final String getAttributeString(String attributeName) throws NullPointerException {
        return getAttribute(attributeName).toString();
    }
    
    /**
     * 
     * @param attributeName the attribute to get
     * @return the given attribute, as a double
     * @throws NumberFormatException if the given attribute is not a number
     */
    public final double getAttributeDouble(String attributeName) throws NumberFormatException {
        String s = getAttributeString(attributeName);
        double ret = 0.0;
        try {
            ret = Double.valueOf(s);
        } catch(NumberFormatException ex){
            Logger.logError(String.format("Attribute with name \"%s\" cannot be formatted as a number. Its value is \"%s\"", attributeName, s));
            throw ex;
        }
        return ret;
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
