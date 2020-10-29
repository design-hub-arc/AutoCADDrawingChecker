package autocadDrawingChecker.data.csv;

import autocadDrawingChecker.data.core.AbstractRecordConverter;
import java.util.HashMap;
import org.apache.commons.csv.CSVRecord;

/**
 *
 * @author Matt
 */
public class CsvRecordConverter extends AbstractRecordConverter<CSVRecord> {

    public CsvRecordConverter(HashMap<String, Integer> columns) {
        super(columns);
    }

    @Override
    protected Object doGetCell(CSVRecord currRow, int idx) {
        Object ret = currRow.get(idx);
        boolean foundType = false;
        // see if it's numeric
        try {
            ret = Double.parseDouble(ret.toString());
            foundType = true;
        } catch(NumberFormatException ex){
            // not a number
        }
        
        if(!foundType){
            // first, convert to a string
            ret = ret.toString();
            if(ret.toString().equalsIgnoreCase("true")){
                ret = Boolean.TRUE;
                foundType = true; // it's a boolean
            } else if(ret.toString().equalsIgnoreCase("false")){
                ret = Boolean.FALSE;
                foundType = true;
            }
            // don't use Boolean.parseString methods, as they don't account for values other than true or false
            // this way, if it is neither true nor false, this method returns it as a string
        }
        return ret;
    }

    @Override
    protected boolean doesRowHaveCell(CSVRecord currRow, int idx) {
        return currRow.isSet(idx) && currRow.get(idx) != null;
    }

}
