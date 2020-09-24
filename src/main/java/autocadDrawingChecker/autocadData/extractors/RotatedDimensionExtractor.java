package autocadDrawingChecker.autocadData.extractors;

import autocadDrawingChecker.autocadData.AutoCADAttribute;
import autocadDrawingChecker.autocadData.elements.AutoCADRotatedDimension;

/**
 *
 * @author Matt
 */
public class RotatedDimensionExtractor extends AbstractAutoCADElementExtractor<AutoCADRotatedDimension> {
    private static final AutoCADAttribute[] REQ_COLS = new AutoCADAttribute[]{
        AutoCADAttribute.DIM_STYLE,
        //AutoCADAttribute.DYNAMIC_DIMENSION,
        AutoCADAttribute.TEXT_DEFINED_SIZE
    };
    
    public RotatedDimensionExtractor() {
        super("Rotated Dimension", REQ_COLS);
    }

    @Override
    public AutoCADRotatedDimension doExtract() {
        return new AutoCADRotatedDimension(
            getCellString(AutoCADAttribute.DIM_STYLE),
            //getCellInt(AutoCADAttribute.DYNAMIC_DIMENSION),
            getCellString(AutoCADAttribute.TEXT_DEFINED_SIZE)
        );
    }

}
