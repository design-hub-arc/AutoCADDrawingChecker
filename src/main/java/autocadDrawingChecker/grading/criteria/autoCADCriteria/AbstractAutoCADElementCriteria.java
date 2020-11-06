/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autocadDrawingChecker.grading.criteria.autoCADCriteria;

import autocadDrawingChecker.data.excel.autoCADData.AutoCADElement;
import autocadDrawingChecker.data.excel.autoCADData.AutoCADExport;
import autocadDrawingChecker.data.core.DataSet;
import autocadDrawingChecker.data.core.Record;
import autocadDrawingChecker.grading.criteria.AbstractElementCriteria;
import java.util.Arrays;

/**
 *
 * @author Matt
 */
public interface AbstractAutoCADElementCriteria extends AbstractElementCriteria<AutoCADExport, AutoCADElement> {
    public static final String[] ANY_TYPE = new String[0];
    /**
     * Checks to see if the given AutoCADElement can -or should-
     * be graded by this criteria. This is used by tryCastRecord
 to decide if the given element should be graded.
     * @param e the AutoCADElement to check
     * @return whether or not this criteria can grade e
     */
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
    public default AutoCADElement tryCastRecord(Record rec){
        AutoCADElement ret = null;
        //                                                 only cast elements this can accept
        if(rec != null && rec instanceof AutoCADElement && canAccept((AutoCADElement)rec)){
            ret = (AutoCADElement)rec;
        }
        return ret;
    }
    
    @Override
    public default AutoCADExport tryCastDataSet(DataSet contents){
        return (contents != null && contents instanceof AutoCADExport) ? (AutoCADExport)contents : null;
    }
    
}
