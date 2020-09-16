package autocadDrawingChecker.grading.implementations;

import autocadDrawingChecker.autocadData.AutoCADElement;
import autocadDrawingChecker.autocadData.AutoCADText;
import autocadDrawingChecker.grading.AbstractElementCriteria;

/**
 *
 * @author Matt
 */
public class TextMatches implements AbstractElementCriteria {
    
    @Override
    public double getMatchScore(AutoCADElement row1, AutoCADElement row2){
        double score = 0.0;
        // currently just a basic "if it matches exactly, you get 100%, else, 0.
        if(row1 instanceof AutoCADText && row2 instanceof AutoCADText){
            score = (((AutoCADText)row1).getTextContents().equals(((AutoCADText)row2).getTextContents())) ? 1.0 : 0.0;
        }
        return score;
    }

    @Override
    public String getDescription() {
        return "Checks to see if the text in a student file's text components matches that of the professor's export";
    }

    @Override
    public String getName() {
        return "Text Matches";
    }

}
