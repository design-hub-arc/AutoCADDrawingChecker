package autocadDrawingChecker.data.extractors;

import autocadDrawingChecker.data.elements.AutoCADMultilineText;

/**
 *
 * @author Matt
 */
public class MTextExtractor extends AbstractAutoCADElementExtractor<AutoCADMultilineText> {
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
    
    public MTextExtractor() {
        super("MText", REQ_COLS);
    }

    @Override
    public AutoCADMultilineText doExtract() {
        return new AutoCADMultilineText(
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
