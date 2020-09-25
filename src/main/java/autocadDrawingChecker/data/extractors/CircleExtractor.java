package autocadDrawingChecker.data.extractors;

import autocadDrawingChecker.data.elements.AutoCADCircle;

/**
 *
 * @author Matt
 */
public class CircleExtractor extends AbstractAutoCADElementExtractor<AutoCADCircle> {
    private static final AutoCADAttribute[] REQ_COLS = new AutoCADAttribute[]{
        AutoCADAttribute.CENTER_X,
        AutoCADAttribute.CENTER_Y,
        AutoCADAttribute.CENTER_Z,
        AutoCADAttribute.DIAMETER
    };
    public CircleExtractor() {
        super("Circle", REQ_COLS);
    }

    @Override
    public AutoCADCircle doExtract() {
        return new AutoCADCircle(
            new double[]{
                getCellDouble(AutoCADAttribute.CENTER_X),
                getCellDouble(AutoCADAttribute.CENTER_Y),
                getCellDouble(AutoCADAttribute.CENTER_Z)
            },
            getCellDouble(AutoCADAttribute.DIAMETER) / 2
        );
    }

}
