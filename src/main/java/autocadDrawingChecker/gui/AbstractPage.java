package autocadDrawingChecker.gui;

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
}
