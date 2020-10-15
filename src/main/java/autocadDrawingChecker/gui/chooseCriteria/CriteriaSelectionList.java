package autocadDrawingChecker.gui.chooseCriteria;

import autocadDrawingChecker.data.core.DataSet;
import autocadDrawingChecker.grading.criteria.AbstractGradingCriteria;
import autocadDrawingChecker.start.Application;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

/**
 *
 * @author Matt
 */
public class CriteriaSelectionList extends JComponent {
    private final HashMap<String, CriteriaToggle> criteriaList; // name to criteria toggle
    
    public CriteriaSelectionList(){
        super();
        criteriaList = new HashMap<>();
        setLayout(new BorderLayout());
        
        JPanel content = new JPanel();
        content.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 0.0; // this make things "stick" together
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.PAGE_START;
        
        Application.getInstance().getGradedCriteria().stream().map((AbstractGradingCriteria<? extends DataSet> criteria)->{
            return new CriteriaToggle(criteria);
        }).forEach((CriteriaToggle component)->{
            criteriaList.put(component.getCriteria().getName(), component);
            content.add(component, gbc.clone());
        });
        
        JScrollPane scroll = new JScrollPane(content);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        add(scroll, BorderLayout.CENTER);
    }
    
    public final void setCriteriaSelected(AbstractGradingCriteria crit, boolean isSelected){
        criteriaList.get(crit.getName()).setSelected(isSelected);
    }
    public final List<AbstractGradingCriteria> getSelectedCriteria(){
        return criteriaList.values().stream().filter((ct)->ct.isSelected()).map((ct)->ct.getCriteria()).collect(Collectors.toList());
    }
}
