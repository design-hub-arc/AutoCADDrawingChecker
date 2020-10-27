package autocadDrawingChecker.gui.chooseCriteria;

import autocadDrawingChecker.data.core.DataSet;
import autocadDrawingChecker.grading.criteria.AbstractGradingCriteria;
import autocadDrawingChecker.gui.AbstractPage;
import autocadDrawingChecker.logging.Logger;
import autocadDrawingChecker.start.Application;
import autocadDrawingChecker.start.DrawingCheckerData;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.NumberFormatter;

/**
 *
 * @author Matt
 */
public class ChooseCriteriaPage extends AbstractPage {
    private final CriteriaSelectionList critList;
    
    public ChooseCriteriaPage() {
        super("Choose criteria to grade on");
        setLayout(new BorderLayout());
        
        critList = new CriteriaSelectionList();
        add(critList, BorderLayout.CENTER);
        
        JPanel bottom = new JPanel();
        
        /*
        // https://stackoverflow.com/questions/11093326/restricting-jtextfield-input-to-integers
        NumberFormat format = DecimalFormat.getInstance();
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setAllowsInvalid(true);
        formatter.setValueClass(Double.class);
        formatter.setMinimum(0);
        formatter.setMaximum(Double.MAX_VALUE);
        formatter.setCommitsOnValidEdit(true);
        JFormattedTextField threshInput = new JFormattedTextField(formatter);
        threshInput.setValue(Application.getInstance().getData().getCriteriaThreshold());
        threshInput.setColumns(20);
        threshInput.addFocusListener(new FocusListener(){
            @Override
            public void focusGained(FocusEvent e) {}

            @Override
            public void focusLost(FocusEvent e) {
                the formatter ALWAYS says the input is wrong, and resets it to 0
                try {
                    Application.getInstance().getData().setCriteriaThreshold((Double)threshInput.getValue());
                } catch (Exception ex){
                    Logger.logError(ex);
                }
            }
        });
        bottom.add(threshInput);
        for some reason, I cannot get this to work
        */
        
        JTextField text = new JTextField();
        text.setColumns(20);
        text.addFocusListener(new FocusListener(){
            @Override
            public void focusGained(FocusEvent e) {}

            @Override
            public void focusLost(FocusEvent e) {
                try{
                    Application.getInstance().getData().setCriteriaThreshold(Double.parseDouble(text.getText()));
                } catch (Exception ex){
                    Logger.logError(ex);
                }
            }
        });
        bottom.add(text);
        add(bottom, BorderLayout.PAGE_END);
    }
    
    public final void setCriteriaSelected(AbstractGradingCriteria<? extends DataSet> crit, boolean isSelected){
        critList.setCriteriaSelected(crit, isSelected);
    }
    
    public final List<AbstractGradingCriteria<? extends DataSet>> getSelectedCriteria(){
        return critList.getSelectedCriteria();
    }

    @Override
    protected boolean checkIfReadyForNextPage() {
        boolean ready = true || !critList.getSelectedCriteria().isEmpty();
        if(!ready){
            JOptionPane.showMessageDialog(this, "Please select at least 1 criteria to grade on");
        }
        return ready;
    }

    @Override
    protected void dataUpdated(DrawingCheckerData newData) {
        HashMap<AbstractGradingCriteria<? extends DataSet>, Boolean> critToIsSel = newData.getGradableCriteriaToIsSelected();
        critList.setCriteriaOptions(new LinkedList<>(critToIsSel.keySet()));
        critToIsSel.entrySet().forEach((entry)->{
            critList.setCriteriaSelected(entry.getKey(), entry.getValue());
        });
    }
}
