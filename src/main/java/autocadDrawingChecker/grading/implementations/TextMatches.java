package autocadDrawingChecker.grading.implementations;

import autocadDrawingChecker.autocadData.elements.AutoCADElement;
import autocadDrawingChecker.autocadData.AutoCADText;
import autocadDrawingChecker.grading.AbstractElementCriteria;

/**
 *
 * @author Matt
 */
public class TextMatches implements AbstractElementCriteria<AutoCADText> {
    
    @Override
    public double getMatchScore(AutoCADText row1, AutoCADText row2){
        // currently just a basic "if it matches exactly, you get 100%, else, 0.
        return (row1.getTextContents().equals(row2.getTextContents())) ? 1.0 : 0.0;
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
    public AutoCADText cast(AutoCADElement e) {
        return (e instanceof AutoCADText) ? (AutoCADText)e : null;
    }

}
