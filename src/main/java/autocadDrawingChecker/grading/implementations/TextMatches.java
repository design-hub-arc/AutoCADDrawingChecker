package autocadDrawingChecker.grading.implementations;

import autocadDrawingChecker.autocadData.elements.AutoCADElement;
import autocadDrawingChecker.autocadData.elements.AutoCADMultilineText;
import autocadDrawingChecker.grading.AbstractElementCriteria;

/**
 *
 * @author Matt
 */
public class TextMatches implements AbstractElementCriteria<AutoCADMultilineText> {
    
    @Override
    public double getMatchScore(AutoCADMultilineText row1, AutoCADMultilineText row2){
        // "if it matches exactly, you get 100%, else, 0.
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
    public AutoCADMultilineText cast(AutoCADElement e) {
        return (e instanceof AutoCADMultilineText) ? (AutoCADMultilineText)e : null;
    }

}
