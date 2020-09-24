package autocadDrawingChecker.autocadData.extractors;

import autocadDrawingChecker.autocadData.AutoCADAttribute;
import autocadDrawingChecker.autocadData.elements.AutoCADLine;

/**
 *
 * @author Matt
 */
public class LineExtractor extends AbstractAutoCADElementExtractor<AutoCADLine> {
    private static final AutoCADAttribute[] REQ_COLS = new AutoCADAttribute[]{
        AutoCADAttribute.ANGLE,
        AutoCADAttribute.START_X,
        AutoCADAttribute.START_Y,
        AutoCADAttribute.START_Z,
        AutoCADAttribute.END_X,
        AutoCADAttribute.END_Y,
        AutoCADAttribute.END_Z,
        AutoCADAttribute.DELTA_X,
        AutoCADAttribute.DELTA_Y,
        AutoCADAttribute.DELTA_Z,
        AutoCADAttribute.LENGTH//,
        //AutoCADAttribute.THICKNESS
    };
    
    public LineExtractor() {
        super("Line", REQ_COLS);
    }

    @Override
    public AutoCADLine doExtract() {
        return new AutoCADLine(
            getCellInt(AutoCADAttribute.ANGLE),
            new double[]{
                getCellDouble(AutoCADAttribute.START_X),
                getCellDouble(AutoCADAttribute.START_Y),
                getCellDouble(AutoCADAttribute.START_Z)
            },
            new double[]{
                getCellDouble(AutoCADAttribute.END_X),
                getCellDouble(AutoCADAttribute.END_Y),
                getCellDouble(AutoCADAttribute.END_Z)
            },
            new double[]{
                getCellDouble(AutoCADAttribute.DELTA_X),
                getCellDouble(AutoCADAttribute.DELTA_Y),
                getCellDouble(AutoCADAttribute.DELTA_Z)
            },
            getCellDouble(AutoCADAttribute.LENGTH)//,
            //getCellDouble(AutoCADAttribute.THICKNESS)
        );
    }
}
