/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autocadDrawingChecker.data;

import autocadDrawingChecker.data.core.ExtractedSpreadsheetContents;
import autocadDrawingChecker.data.core.RecordExtractor;

/**
 *
 * @author Matt
 */
public interface AbstractGradeableDataType {
    public String getName();
    public String getDescription();
    public RecordExtractor createExtractor();
    public ExtractedSpreadsheetContents createExtractionHolder(String fileName);
}
