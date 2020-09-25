package autocadDrawingChecker.grading.criteria.implementations;

import autocadDrawingChecker.data.elements.AutoCADElement;
import autocadDrawingChecker.data.elements.AbstractAutoCADText;
import autocadDrawingChecker.grading.criteria.AbstractElementCriteria;

/**
 *
 * @author Matt
 */
public class TextMatches implements AbstractElementCriteria<AbstractAutoCADText> {
    
    @Override
    public double getMatchScore(AbstractAutoCADText row1, AbstractAutoCADText row2){
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
    public AbstractAutoCADText cast(AutoCADElement e) {
        return (e instanceof AbstractAutoCADText) ? (AbstractAutoCADText)e : null;
    }

}
