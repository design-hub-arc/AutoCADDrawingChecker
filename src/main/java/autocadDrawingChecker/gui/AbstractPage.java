package autocadDrawingChecker.gui;

import javax.swing.JPanel;

/**
 *
 * @author Matt
 */
public abstract class AbstractPage extends JPanel {
    private final AppPane parent;
    
    public AbstractPage(AppPane ap){
        super();
        parent = ap;
    }
    
    protected final AppPane getPaneParent(){
        return parent;
    }
}
