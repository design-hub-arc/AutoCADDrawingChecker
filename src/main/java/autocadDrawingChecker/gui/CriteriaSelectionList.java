package autocadDrawingChecker.gui;

import autocadDrawingChecker.comparison.AbstractGradingCriteria;
import autocadDrawingChecker.comparison.GradingCriteriaLoader;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

/**
 *
 * @author Matt
 */
public class CriteriaSelectionList extends JComponent {
    private final ArrayList<CriteriaToggle> criteriaList;
    
    public CriteriaSelectionList(){
        super();
        criteriaList = new ArrayList<>();
        setLayout(new BorderLayout());
        add(new JLabel("Select which criteria to grade on"), BorderLayout.PAGE_START);
        
        JPanel content = new JPanel();
        content.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH;
        GradingCriteriaLoader.getAllCriteria().stream().map((AbstractGradingCriteria criteria)->{
            return new CriteriaToggle(criteria);
        }).forEach((CriteriaToggle component)->{
            criteriaList.add(component);
            content.add(component, gbc.clone());
            gbc.gridy = gbc.gridy + 1;
        });
        
        JScrollPane scroll = new JScrollPane(content);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        add(scroll, BorderLayout.CENTER);
    }
    
    public final List<AbstractGradingCriteria> getSelectedCriteria(){
        return criteriaList.stream().filter((ct)->ct.isSelected()).map((ct)->ct.getCriteria()).collect(Collectors.toList());
    }
}
