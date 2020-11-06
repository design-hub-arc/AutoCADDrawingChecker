package autocadDrawingChecker.gui;

import autocadDrawingChecker.start.DrawingCheckerData;
import javax.swing.JPanel;

/**
 *
 * @author Matt
 */
public abstract class AbstractPage extends JPanel {
    private final String pageTitle;
    
    public AbstractPage(String title){
        super();
        pageTitle = title;
    }
    
    public final String getTitle(){
        return pageTitle;
    }
    
    protected abstract boolean checkIfReadyForNextPage();
    
    /**
     * This method is invoked when switching to this page. Updates
     * the GUI of this page based on the data contained in newData.
     * 
     * @param newData the current set of DrawingCheckerData. 
     */
    protected abstract void dataUpdated(DrawingCheckerData newData);
}
