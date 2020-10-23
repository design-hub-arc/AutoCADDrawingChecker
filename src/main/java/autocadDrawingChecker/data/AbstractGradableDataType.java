package autocadDrawingChecker.data;

import autocadDrawingChecker.data.core.DataSet;
import java.io.IOException;

/**
 * AbstractGradableDataType represents a type of data the program can grade.
 * This class serves as a bridge between the Application and data classes.
 * The application will use the AbstractGradableDataType.parseFile method to
 * read files the user provides, without needing to know exactly what subclass
 * of DataSet the method returns.
 * 
 * @author Matt Crow
 */
public interface AbstractGradableDataType {
    /**
     * @return a brief name for this data type, which will be presented to the user.
     */
    public String getName();
    
    /**
     * 
     * @return a short description of how files for this data type are formatted.
     */
    public String getDescription();
    
    /**
     * Use this method to read an Excel file, then return its contents,
     * parsed using a subclass of ExcelParser. 
     * 
     * @param fileName the complete path to the file to parse.
     * 
     * @return the parsed contents of the file. Note that the value 
     * returned will almost always be a subclass of DataSet, rather 
     * than just an instance of DataSet.
     * 
     * @throws IOException if any errors occur when parsing the file
     */
    public DataSet parseFile(String fileName) throws IOException;
}
