package autocadDrawingChecker.data.extractors;

import autocadDrawingChecker.data.elements.AutoCADPolyline;

/**
 *
 * @author Matt
 */
public class PolylineExtractor extends AbstractAutoCADElementExtractor<AutoCADPolyline>{
    private static final AutoCADAttribute[] REQ_COLS = new AutoCADAttribute[]{
        AutoCADAttribute.LENGTH//,
        //AutoCADAttribute.THICKNESS,
        //AutoCADAttribute.AREA,
        //AutoCADAttribute.CLOSED,
        //AutoCADAttribute.GLOBAL_WIDTH
    };
    public PolylineExtractor() {
        super("Polyline", REQ_COLS);
    }

    @Override
    public AutoCADPolyline doExtract() {
        return new AutoCADPolyline(
            getCellDouble(AutoCADAttribute.LENGTH)//,
            //getCellDouble(AutoCADAttribute.THICKNESS),
            //getCellDouble(AutoCADAttribute.AREA),
            //getCellString(AutoCADAttribute.CLOSED),
            //getCellDouble(AutoCADAttribute.GLOBAL_WIDTH)
        );
    }
}
