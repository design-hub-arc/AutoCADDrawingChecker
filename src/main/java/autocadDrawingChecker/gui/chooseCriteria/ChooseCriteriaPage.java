package autocadDrawingChecker.gui.chooseCriteria;

import autocadDrawingChecker.grading.AbstractGradingCriteria;
import autocadDrawingChecker.gui.AbstractPage;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Matt
 */
public class ChooseCriteriaPage extends AbstractPage {
    private final CriteriaSelectionList critList;
    
    public ChooseCriteriaPage() {
        super("Choose criteria to grade on");
        setLayout(new GridLayout(1, 1));
        
        critList = new CriteriaSelectionList();
        add(critList);
    }
    
    public final void setCriteriaSelected(AbstractGradingCriteria crit, boolean isSelected){
        critList.setCriteriaSelected(crit, isSelected);
    }
    
    public final List<AbstractGradingCriteria> getSelectedCriteria(){
        return critList.getSelectedCriteria();
    }

    @Override
    protected boolean checkIfReadyForNextPage() {
        boolean ready = !critList.getSelectedCriteria().isEmpty();
        if(!ready){
            JOptionPane.showMessageDialog(this, "Please select at least 1 criteria to grade on");
        }
        return ready;
    }
}
