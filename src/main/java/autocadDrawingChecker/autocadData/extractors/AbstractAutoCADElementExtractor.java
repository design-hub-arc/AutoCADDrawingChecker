package autocadDrawingChecker.autocadData.extractors;

import autocadDrawingChecker.autocadData.AutoCADElement;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.poi.ss.usermodel.Row;

/**
 * This class is used to convert spreadsheet data
 * into an AutoCADElement. Ideally, this should allow
 * for more extensibility, as it moves the extraction
 * specifics out of the AutoCADExcelParse.
 * 
 * @author Matt Crow
 * @param <T> the type of AutoCADElement this extracts
 */
public abstract class AbstractAutoCADElementExtractor<T extends AutoCADElement> {
    private final String name;
    private final List<String> requiredColumns;
    
    /**
     * 
     * @param name if this name shows up in the "name" column of an AutoCAD export,
     * this will attempt to extract that line.
     * @param requiredColumns the columns this requires. Automatically converted to
     * upper case and trimmed.
     */
    public AbstractAutoCADElementExtractor(String name, String... requiredColumns){
        this.name = name.toUpperCase();
        this.requiredColumns = Arrays.asList(requiredColumns).stream().map((String col)->{
            return col.trim().toUpperCase();
        }).collect(Collectors.toList());
    }
    
    public final String getName(){
        return name;
    }
    
    /**
     * Checks to see if the given columns contain all the columns this needs.
     * @param columns
     * @return 
     */
    public final boolean hasRequiredColumns(HashMap<String, Integer> columns){
        return requiredColumns.stream().allMatch((String requiredColumn)->{
            return columns.containsKey(requiredColumn) && columns.get(requiredColumn) != -1;
        });
    }
    
    /**
     * Extracts data from the given row, and converts it to an AutoCAD element.
     * @param columns the mapping of column headers to the index of the column
     * in the given row.
     * @param currentRow the row to extract data from
     * @return the extracted AutoCADElement.
     */
    public abstract T extract(HashMap<String, Integer> columns, Row currentRow);
}
