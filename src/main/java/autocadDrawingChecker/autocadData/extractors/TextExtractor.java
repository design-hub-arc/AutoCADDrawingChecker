package autocadDrawingChecker.autocadData.extractors;

import autocadDrawingChecker.autocadData.AutoCADAttribute;
import autocadDrawingChecker.autocadData.elements.AutoCADText;

/**
 *
 * @author Matt
 */
public class TextExtractor extends AbstractAutoCADElementExtractor<AutoCADText> {
    private static final AutoCADAttribute[] REQ_COLS = new AutoCADAttribute[]{
        AutoCADAttribute.CONTENTS,
        AutoCADAttribute.CONTENTS_RTF,
        AutoCADAttribute.POSITION_X,
        AutoCADAttribute.POSITION_Y,
        AutoCADAttribute.POSITION_Z,
        AutoCADAttribute.ROTATION,
        AutoCADAttribute.SHOW_BORDERS,
        AutoCADAttribute.WIDTH
    };
    
    public TextExtractor() {
        super("MText", REQ_COLS);
    }

    @Override
    public AutoCADText doExtract() {
        return new AutoCADText(
            getCellString(AutoCADAttribute.CONTENTS),
            getCellString(AutoCADAttribute.CONTENTS_RTF),
            new double[]{
                getCellDouble(AutoCADAttribute.POSITION_X),
                getCellDouble(AutoCADAttribute.POSITION_Y),
                getCellDouble(AutoCADAttribute.POSITION_Z)
            },
            getCellInt(AutoCADAttribute.ROTATION),
            getCellInt(AutoCADAttribute.SHOW_BORDERS),
            getCellDouble(AutoCADAttribute.WIDTH)
        );
    }

}
