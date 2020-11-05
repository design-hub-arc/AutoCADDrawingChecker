package autocadDrawingChecker.gui.chooseCriteria;

import autocadDrawingChecker.grading.criteria.AbstractGradingCriteria;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Collections;
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
    private final JPanel content;
    private final GridBagConstraints gbc;
    
    public CriteriaSelectionList(){
        super();
        criteriaList = new HashMap<>();
        setLayout(new BorderLayout());
        
        content = new JPanel();
        content.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 0.0; // this make things "stick" together
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.PAGE_START;
        
        JScrollPane scroll = new JScrollPane(content);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.getVerticalScrollBar().setUnitIncrement(10);
        add(scroll, BorderLayout.CENTER);
    }
    
    final void setCriteriaOptions(List<AbstractGradingCriteria> criteria){
        criteriaList.clear();
        content.removeAll();
        
        CriteriaToggle toggle = null;
        // sort criteria by name
        criteria = criteria.subList(0, criteria.size()); // copy to avoid changing the original
        Collections.sort(criteria, (crit1, crit2)->crit1.getName().compareTo(crit2.getName()));
        for(AbstractGradingCriteria crit : criteria){
            toggle = new CriteriaToggle(crit);
            criteriaList.put(toggle.getCriteria().getName(), toggle);
            content.add(toggle, gbc.clone());
        }
    }
    
    public final void setCriteriaSelected(AbstractGradingCriteria crit, boolean isSelected){
        criteriaList.get(crit.getName()).setSelected(isSelected);
    }
    public final List<AbstractGradingCriteria> getSelectedCriteria(){
        return criteriaList.values().stream().filter((ct)->ct.isSelected()).map((ct)->ct.getCriteria()).collect(Collectors.toList());
    }
}
