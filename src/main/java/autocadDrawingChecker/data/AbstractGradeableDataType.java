/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autocadDrawingChecker.data;

import autocadDrawingChecker.data.core.DataSet;
import autocadDrawingChecker.data.core.RecordExtractor;
import java.io.IOException;
import java.util.HashMap;

/**
 *
 * @author Matt
 */
public interface AbstractGradeableDataType {
    public String getName();
    public String getDescription();
    public RecordExtractor createExtractor(HashMap<String, Integer> columns);
    public DataSet createExtractionHolder(String fileName);
    public DataSet parseFile(String fileName) throws IOException;
}
