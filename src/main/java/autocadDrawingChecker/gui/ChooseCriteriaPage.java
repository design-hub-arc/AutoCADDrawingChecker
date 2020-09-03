package autocadDrawingChecker.gui;

import autocadDrawingChecker.comparison.AbstractGradingCriteria;
import java.awt.BorderLayout;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Matt
 */
public class ChooseCriteriaPage extends AbstractPage {
    private final CriteriaSelectionList critList;
    
    public ChooseCriteriaPage(AppPane ap) {
        super(ap);
        setLayout(new BorderLayout());
        
        add(new JLabel("Step 2: Choose criteria to grade on"), BorderLayout.PAGE_START);
        
        critList = new CriteriaSelectionList();
        add(critList, BorderLayout.CENTER);
        
        JPanel bottom = new JPanel();
        JButton prev = new JButton("Go back");
        prev.addActionListener((e)->{
            getPaneParent().switchToPage(AppPane.CHOOSE_FILES);
        });
        bottom.add(prev);
        JButton next = new JButton("Next step");
        next.addActionListener((e)->{
            if(critList.getSelectedCriteria().isEmpty()){
                JOptionPane.showMessageDialog(next, "Please select at least 1 criteria to grade on");
            } else {
                getPaneParent().switchToPage(AppPane.OUTPUT);
            }
        });
        bottom.add(next);
        add(bottom, BorderLayout.PAGE_END);
    }
    
    public final List<AbstractGradingCriteria> getSelectedCriteria(){
        return critList.getSelectedCriteria();
    }
}
