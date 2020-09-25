package autocadDrawingChecker.data.extractors;

import autocadDrawingChecker.data.elements.AutoCADDiametricDimension;

/**
 *
 * @author Matt
 */
public class DiametricDimensionExtractor extends AbstractAutoCADElementExtractor<AutoCADDiametricDimension> {
    private static final AutoCADAttribute[] REQ_COLS = new AutoCADAttribute[]{
        AutoCADAttribute.DIM_STYLE,
        //AutoCADAttribute.DYNAMIC_DIMENSION,
        AutoCADAttribute.TEXT_DEFINED_SIZE
    };
    
    public DiametricDimensionExtractor() {
        super("Diametric Dimension", REQ_COLS);
    }

    @Override
    public AutoCADDiametricDimension doExtract() {
        return new AutoCADDiametricDimension(
            getCellString(AutoCADAttribute.DIM_STYLE),
            //getCellInt(AutoCADAttribute.DYNAMIC_DIMENSION),
            getCellString(AutoCADAttribute.TEXT_DEFINED_SIZE)
        );
    }
}
