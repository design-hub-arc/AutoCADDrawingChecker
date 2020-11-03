package autocadDrawingChecker.data.excel;

import autocadDrawingChecker.data.core.AbstractRecordConverter;
import autocadDrawingChecker.logging.Logger;
import java.util.Map;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

/**
 *
 * @author Matt
 */
public class ExcelRecordConverter extends AbstractRecordConverter<Row> {

    /*
    @Override
    protected Object doGetCell(Row currRow, int idx){
        Object ret = null;
        Cell c = currRow.getCell(idx);
        switch(c.getCellType()){
            case BOOLEAN:
                ret = c.getBooleanCellValue();
                break;
            case NUMERIC:
                ret = (Double)c.getNumericCellValue();
                break;
            case STRING:
                ret = c.getStringCellValue();
                break;
            default:
                Logger.logError(String.format("ExcelRecordExtractor encountered cell with type %s", c.getCellType().name()));
                //ret = c.toString();
                break;
        }
        return ret;
    }
*/
    
}
