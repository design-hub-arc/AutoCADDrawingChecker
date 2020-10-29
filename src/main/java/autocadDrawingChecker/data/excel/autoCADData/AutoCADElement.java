package autocadDrawingChecker.data.excel.autoCADData;

import autocadDrawingChecker.data.core.Record;

/**
 * An AutoCADElement represents
 a single record in an AutoCADExport.
 * 
 * @author Matt Crow
 */
public class AutoCADElement extends Record {
    
    public static final String NAME_COL = "Name";
    public static final String LAYER_COL = "Layer";
    
    // no access modifier, so only classes in this package can invoke this
    AutoCADElement(){
        super();
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
    
    @Override
    public String toString(){
        return String.format("AutoCAD Element: \n%s", super.toString());
    }
}
