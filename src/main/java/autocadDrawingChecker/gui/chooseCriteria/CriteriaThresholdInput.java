package autocadDrawingChecker.gui.chooseCriteria;

import autocadDrawingChecker.start.Application;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.text.NumberFormatter;

/**
 * This is a widget allowing the user to select the criteria threshold as defined in DrawingCheckerData
 * 
 * @author Matt Crow
 */
public class CriteriaThresholdInput extends JComponent {
    private final JFormattedTextField threshInput;
    private final JPanel body;
    
    public CriteriaThresholdInput(){
        super();
        setLayout(new BorderLayout());
        
        JLabel title = new JLabel("Criteria Threshold");
        title.setToolTipText("How much student values can differ yet still be considered correct");
        add(title, BorderLayout.PAGE_START);
        
        body = new JPanel();
        // https://stackoverflow.com/questions/11093326/restricting-jtextfield-input-to-integers
        NumberFormat format = DecimalFormat.getInstance();
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setAllowsInvalid(true);
        formatter.setValueClass(Double.class);
        formatter.setMinimum(0.0);
        formatter.setMaximum(Double.MAX_VALUE);
        formatter.setCommitsOnValidEdit(true);
        threshInput = new JFormattedTextField(formatter);
        threshInput.setToolTipText("Enter a non-negative number (it can be a decimal number)");
        threshInput.setValue(Application.getInstance().getData().getCriteriaThreshold());
        threshInput.setColumns(20);
        threshInput.addFocusListener(new FocusListener(){
            @Override
            public void focusGained(FocusEvent e) {}

            @Override
            public void focusLost(FocusEvent e) {
                checkIfValid();
            }
        });
        threshInput.addKeyListener(new KeyListener(){
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    checkIfValid();
                }
            }
        });
        body.add(threshInput);
        add(body, BorderLayout.CENTER);
    }
    
    private void checkIfValid(){
        if(threshInput.isEditValid()){
            Application.getInstance().getData().setCriteriaThreshold((Double)threshInput.getValue());
            body.setBackground(Color.green);
        } else {
            // not valid
            body.setBackground(Color.red);
        }
    }
}
