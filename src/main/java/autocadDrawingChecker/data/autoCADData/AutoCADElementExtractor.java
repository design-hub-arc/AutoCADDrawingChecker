package autocadDrawingChecker.data.autoCADData;

import autocadDrawingChecker.data.core.RecordExtractor;

/**
 * @author Matt
 */
class AutoCADElementExtractor extends RecordExtractor {    
    @Override
    protected AutoCADElement createNew(){
        return new AutoCADElement();
    }
}
