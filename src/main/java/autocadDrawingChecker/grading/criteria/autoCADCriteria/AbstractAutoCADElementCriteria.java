/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autocadDrawingChecker.grading.criteria.autoCADCriteria;

import autocadDrawingChecker.data.autoCADData.AutoCADElement;
import autocadDrawingChecker.data.autoCADData.AutoCADExport;
import autocadDrawingChecker.data.core.SpreadsheetRecord;
import autocadDrawingChecker.grading.criteria.AbstractElementCriteria;

/**
 *
 * @author Matt
 */
public interface AbstractAutoCADElementCriteria extends AbstractElementCriteria<AutoCADElement, AutoCADExport> {
    @Override
    public default AutoCADElement tryCast(SpreadsheetRecord rec){
        return (rec instanceof AutoCADElement) ? (AutoCADElement)rec : null;
    }
}
