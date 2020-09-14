package autocadDrawingChecker.autocadData;

import java.util.Arrays;

/**
 *
 * @author Matt
 */
public class AutoCADText extends AutoCADElement {
    private final String contents;
    private final double[] position;
    
    public AutoCADText(String layerName, String textContents, double[] coords) {
        super(layerName);
        contents = textContents;
        position = Arrays.copyOf(coords, coords.length);
    }

}
