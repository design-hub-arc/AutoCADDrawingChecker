package autocadDrawingChecker.data;

import autocadDrawingChecker.data.core.AbstractTableParser;
import autocadDrawingChecker.util.FileType;

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
     * Use this method to read an Excel file, then return a subclass of ExcelParser
     * 
     * @return the autocadDrawingChecker.data.core.AbstractTableParser
     */
    public AbstractTableParser createParser();
    
    /**
     * 
     * @return the types of files this expects
     * to parse.
     */
    public FileType getRequiredFileType();
}
