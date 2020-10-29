package autocadDrawingChecker.data.excel.autoCADData;

import autocadDrawingChecker.data.excel.ExcelRecordConverter;
import java.util.HashMap;

/**
 * @author Matt
 */
class AutoCADElementConverter extends ExcelRecordConverter {    

    public AutoCADElementConverter(HashMap<String, Integer> columns) {
        super(columns);
    }
    @Override
    protected AutoCADElement createNew(){
        return new AutoCADElement();
    }
}
