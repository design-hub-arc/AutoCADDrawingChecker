package autocadDrawingChecker.autocadData.extractors;

/**
 *
 * @author Matt
 */
public class ExtractorLoader {
    /**
     * 
     * @return the default extractors I've created 
     */
    public static AbstractAutoCADElementExtractor<?>[] getAll(){
        return new AbstractAutoCADElementExtractor<?>[]{
            new LineExtractor(),
            new DimensionExtractor(),
            new TextExtractor(),
            new PolylineExtractor()
        };
    }
}
