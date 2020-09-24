package autocadDrawingChecker.autocadData.extractors;

import autocadDrawingChecker.util.AbstractLoader;

/**
 *
 * @author Matt
 */
public class ExtractorLoader extends AbstractLoader<AbstractAutoCADElementExtractor<?>>{
    /**
     * 
     * @return the default extractors I've created 
     */
    @Override
    public AbstractAutoCADElementExtractor<?>[] getAll(){
        return new AbstractAutoCADElementExtractor<?>[]{
            new LineExtractor(),
            new RotatedDimensionExtractor(),
            new DiametricDimensionExtractor(),
            new TextExtractor(),
            new MTextExtractor(),
            new PolylineExtractor()
        };
    }
}
