package autocadDrawingChecker.data.autoCADData;

import autocadDrawingChecker.data.core.ExcelRecordExtractor;
import java.util.HashMap;

/**
 * @author Matt
 */
class AutoCADElementExtractor extends ExcelRecordExtractor {    

    public AutoCADElementExtractor(HashMap<String, Integer> columns) {
        super(columns);
    }
    @Override
    protected AutoCADElement createNew(){
        return new AutoCADElement();
    }
}
