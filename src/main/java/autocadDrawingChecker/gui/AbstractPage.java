package autocadDrawingChecker.gui;

import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author Matt
 */
public abstract class AbstractPage extends JPanel {
    private final AppPane parent;
    private final String pageTitle;
    private final ArrayList<JButton> buttons;
    
    public AbstractPage(AppPane ap, String title){
        super();
        parent = ap;
        pageTitle = title;
        buttons = new ArrayList<>();
    }
    
    public final String getTitle(){
        return pageTitle;
    }
    
    protected final AppPane getPaneParent(){
        return parent;
    }
    
    protected final void addButton(JButton b){
        buttons.add(b);
    }
    
    public final ArrayList<JButton> getButtons(){
        return buttons;
    }
}
