package autocadDrawingChecker.grading.criteria.implementations;

import autocadDrawingChecker.data.elements.AutoCADElement;
import autocadDrawingChecker.grading.criteria.AbstractElementCriteria;

/**
 *
 * @author Matt
 */
public class TextMatches implements AbstractElementCriteria<AutoCADElement> {
    
    @Override
    public double getMatchScore(AutoCADElement row1, AutoCADElement row2){
        // "if it matches exactly, you get 100%, else, 0."
        return (row1.getAttributeString("contents").equals(row2.getAttributeString("contents"))) ? 1.0 : 0.0;
    }

    @Override
    public String getDescription() {
        return "Checks to see if the text in a student file's text components matches that of the professor's export";
    }

    @Override
    public String getName() {
        return "Text Matches";
    }

    @Override
    public AutoCADElement cast(AutoCADElement e) {
        return e;
    }

    @Override
    public String[] getAllowedTypes() {
        return new String[]{"Text", "MText"};
    }

}
