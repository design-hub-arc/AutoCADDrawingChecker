package autocadDrawingChecker.gui.chooseCriteria;

import autocadDrawingChecker.grading.AbstractGradingCriteria;
import autocadDrawingChecker.start.Application;
import java.awt.BorderLayout;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextArea;

/**
 * A CriteriaToggle allow the user to choose whether 
 * or not they want to grade a student's submission
 * based on a given criteria.
 * 
 * @author Matt Crow
 */
public class CriteriaToggle extends JComponent {
    private final AbstractGradingCriteria criteria;
    private final JCheckBox selectBox;
    
    public CriteriaToggle(AbstractGradingCriteria forCriteria){
        super();
        criteria = forCriteria;
        
        setLayout(new BorderLayout());
        add(new JLabel(criteria.getName()), BorderLayout.PAGE_START);
        selectBox = new JCheckBox("Grade this");
        selectBox.setSelected(true);
        selectBox.addActionListener((e)->{
            Application.getInstance().setCriteria(criteria, selectBox.isSelected());
        });
        add(selectBox, BorderLayout.LINE_START);
        
        JTextArea desc = new JTextArea(criteria.getDescription());
        desc.setLineWrap(true);
        desc.setWrapStyleWord(true);
        desc.setEditable(false);
        add(desc, BorderLayout.CENTER);
    }
    
    public final boolean isSelected(){
        return selectBox.isSelected();
    }
    
    public final AbstractGradingCriteria getCriteria(){
        return criteria;
    }
}
