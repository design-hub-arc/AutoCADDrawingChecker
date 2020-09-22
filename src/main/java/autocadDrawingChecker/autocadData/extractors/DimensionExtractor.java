package autocadDrawingChecker.autocadData.extractors;

import autocadDrawingChecker.autocadData.AutoCADDimension;

/**
 *
 * @author Matt
 */
public class DimensionExtractor extends AbstractAutoCADElementExtractor<AutoCADDimension> {

    public DimensionExtractor() {
        super("Rotated Dimension", "Dim Style", "DynamicDimension", "TextDefinedSize");
    }

    @Override
    public AutoCADDimension doExtract() {
        return new AutoCADDimension(
            getCellString("Dim Style"),
            getCellInt("DynamicDimension"),
            getCellString("TextDefinedSize")
        );
    }

}
