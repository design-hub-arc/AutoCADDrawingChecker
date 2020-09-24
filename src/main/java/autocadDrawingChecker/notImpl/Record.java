package autocadDrawingChecker.notImpl;

import java.util.HashMap;

/**
 *
 * @author Matt
 */
public class Record {
    private final HashMap<String, Object> attributes;
    
    public Record(){
        this.attributes = new HashMap<>();
    }
    
    private String sanitizeAttributeName(String name){
        return name.trim().toUpperCase();
    }
    
    public final Record setAttribute(String attrName, Object value){
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
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("RECORD");
        attributes.forEach((k, v)->{
            sb.append(String.format("\n- %s : %s", k, v.toString()));
        });
        sb.append("\nEND OF RECORD");
        return sb.toString();
    }
}
