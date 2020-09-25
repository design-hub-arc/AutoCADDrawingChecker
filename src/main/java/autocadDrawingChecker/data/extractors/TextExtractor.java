package autocadDrawingChecker.data.extractors;

import autocadDrawingChecker.data.elements.AutoCADText;

/**
 *
 * @author Matt
 */
public class TextExtractor extends AbstractAutoCADElementExtractor<AutoCADText> {
    private static final AutoCADAttribute[] REQ_COL = new AutoCADAttribute[]{
        AutoCADAttribute.VALUE,
        AutoCADAttribute.ROTATION
    };
    public TextExtractor() {
        super("Text", REQ_COL);
    }

    @Override
    public AutoCADText doExtract() {
        return new AutoCADText(
            getCellString(AutoCADAttribute.VALUE),
            getCellInt(AutoCADAttribute.ROTATION)
        );
    }

}
