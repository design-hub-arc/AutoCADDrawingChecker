package autocadDrawingChecker.data.autoCADData;

import autocadDrawingChecker.data.core.RecordExtractor;
import java.util.HashMap;

/**
 * @author Matt
 */
class AutoCADElementExtractor extends RecordExtractor {    

    public AutoCADElementExtractor(HashMap<String, Integer> columns) {
        super(columns);
    }
    @Override
    protected AutoCADElement createNew(){
        return new AutoCADElement();
    }
}
