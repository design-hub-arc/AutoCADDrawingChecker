package autocadDrawingChecker.autocadData.extractors;

import autocadDrawingChecker.autocadData.AutoCADAttribute;
import autocadDrawingChecker.autocadData.elements.AutoCADDimension;

/**
 *
 * @author Matt
 */
public class DimensionExtractor extends AbstractAutoCADElementExtractor<AutoCADDimension> {
    private static final AutoCADAttribute[] REQ_COLS = new AutoCADAttribute[]{
        AutoCADAttribute.DIM_STYLE,
        //AutoCADAttribute.DYNAMIC_DIMENSION,
        AutoCADAttribute.TEXT_DEFINED_SIZE
    };
    
    public DimensionExtractor() {
        super("Rotated Dimension", REQ_COLS);
    }

    @Override
    public AutoCADDimension doExtract() {
        return new AutoCADDimension(
            getCellString(AutoCADAttribute.DIM_STYLE),
            //getCellInt(AutoCADAttribute.DYNAMIC_DIMENSION),
            getCellString(AutoCADAttribute.TEXT_DEFINED_SIZE)
        );
    }

}
