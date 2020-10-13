package autocadDrawingChecker.grading.criteria.autoCADCriteria;

import autocadDrawingChecker.data.autoCADData.AutoCADElement;
import autocadDrawingChecker.data.autoCADData.AutoCADExport;
import autocadDrawingChecker.data.core.SpreadsheetRecord;
import autocadDrawingChecker.grading.criteria.AbstractElementCriteria;

/**
 *
 * @author Matt
 */
public class TextMatches implements AbstractElementCriteria<AutoCADElement, AutoCADExport> {
    
    @Override
    public double getMatchScore(AutoCADElement row1, AutoCADElement row2){
        // "if it matches exactly, you get 100%, else, 0."
        String text1 = null;
        String text2 = null;
        try {
            text1 = row1.getAttributeString("contents");
        } catch (NullPointerException ex){
            text1 = row1.getAttributeString("value");
        }
        try {
            text2 = row2.getAttributeString("contents");
        } catch (NullPointerException ex){
            text2 = row2.getAttributeString("value");
        }
        
        return (text1.equals(text2)) ? 1.0 : 0.0;
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
    public String[] getAllowedTypes() {
        return new String[]{"Text", "MText"};
    }

    @Override
    public AutoCADElement tryCast(SpreadsheetRecord rec) {
        return (rec instanceof AutoCADElement) ? (AutoCADElement)rec : null;
    }

}
