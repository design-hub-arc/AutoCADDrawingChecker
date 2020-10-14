/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autocadDrawingChecker.grading.criteria.autoCADCriteria;

import autocadDrawingChecker.data.autoCADData.AutoCADElement;
import autocadDrawingChecker.data.autoCADData.AutoCADExport;
import autocadDrawingChecker.data.core.ExtractedSpreadsheetContents;
import autocadDrawingChecker.data.core.SpreadsheetRecord;
import autocadDrawingChecker.grading.criteria.AbstractElementCriteria;
import java.util.Arrays;

/**
 *
 * @author Matt
 */
public interface AbstractAutoCADElementCriteria extends AbstractElementCriteria<AutoCADElement> {
    public static final String[] ANY_TYPE = new String[0];
    /**
     * Checks to see if the given AutoCADElement can -or should-
     * be graded by this criteria. This is used by the ElementMatcher
 to decide if the given element should be graded.
     * @see ElementMatcher
     * @param e the AutoCADElement to check
     * @return whether or not this criteria can grade e
     */
    @Override
    public default boolean canAccept(AutoCADElement e){
        String[] types = getAllowedTypes();
        String eType = e.getName();
        boolean acceptable = Arrays.equals(types, ANY_TYPE);
        for(int i = 0; i < types.length && !acceptable; i++){
            acceptable = eType.equalsIgnoreCase(types[i]);
        }        
        return acceptable;
    }

    /**
     * 
     * @return a list of AutoCAD Name column values.
     * This is meant to filter out unwanted rows.
     * May remove later.
     * 
     * You can make this return AbstractAutoCADElementCriteria.ANY_TYPE
     * to allow all record types.
     */
    public abstract String[] getAllowedTypes();
    
    @Override
    public default AutoCADElement tryCast(SpreadsheetRecord rec){
        return (rec instanceof AutoCADElement) ? (AutoCADElement)rec : null;
    }
    
    @Override
    public default boolean canGrade(ExtractedSpreadsheetContents contents){
        return contents != null && contents instanceof AutoCADExport;
    }
    
    @Override
    public default double computeScore(ExtractedSpreadsheetContents exp1, ExtractedSpreadsheetContents exp2){
        return computeScore((AutoCADExport)exp1, (AutoCADExport)exp2);
    }
    
    public abstract double computeScore(AutoCADExport exp1, AutoCADExport exp2);
    
}
