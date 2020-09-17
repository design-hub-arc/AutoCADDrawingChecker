package autocadDrawingChecker.gui.chooseCriteria;

import autocadDrawingChecker.grading.AbstractGradingCriteria;
import autocadDrawingChecker.gui.AbstractPage;
import autocadDrawingChecker.gui.PageRenderer;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 *
 * @author Matt
 */
public class ChooseCriteriaPage extends AbstractPage {
    private final CriteriaSelectionList critList;
    
    public ChooseCriteriaPage(PageRenderer ap) {
        super(ap, "Step 2: Choose criteria to grade on");
        setLayout(new GridLayout(1, 1));
        
        critList = new CriteriaSelectionList();
        add(critList);
        
        JButton prev = new JButton("Go back");
        prev.addActionListener((e)->{
            getPaneParent().switchToPage(PageRenderer.CHOOSE_FILES);
        });
        addButton(prev);
        
        JButton next = new JButton("Next step");
        next.addActionListener((e)->{
            if(critList.getSelectedCriteria().isEmpty()){
                JOptionPane.showMessageDialog(next, "Please select at least 1 criteria to grade on");
            } else {
                getPaneParent().switchToPage(PageRenderer.OUTPUT);
            }
        });
        addButton(next);
    }
    
    public final List<AbstractGradingCriteria> getSelectedCriteria(){
        return critList.getSelectedCriteria();
    }
}
