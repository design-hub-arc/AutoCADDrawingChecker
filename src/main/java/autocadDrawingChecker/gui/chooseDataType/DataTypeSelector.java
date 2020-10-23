package autocadDrawingChecker.gui.chooseDataType;

import autocadDrawingChecker.data.AbstractGradableDataType;
import autocadDrawingChecker.start.Application;
import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;

/**
 *
 * @author Matt
 */
public class DataTypeSelector extends JComponent implements MouseListener {
    private final JRadioButton button;
    private final AbstractGradableDataType dataType;
    
    DataTypeSelector(AbstractGradableDataType dataType){
        super();
        this.dataType = dataType;
        setLayout(new BorderLayout());
        add(new JLabel(dataType.getName()), BorderLayout.PAGE_START);
        button = new JRadioButton();
        button.addActionListener((e)->{
            setSelected(true);
        });
        
        add(button, BorderLayout.LINE_START);
        JTextArea desc = new JTextArea(dataType.getDescription());
        desc.setLineWrap(true);
        desc.setWrapStyleWord(true);
        desc.setEditable(false);
        add(desc, BorderLayout.CENTER);
        desc.addMouseListener(this);
        
        this.addMouseListener(this);
    }
    
    final JRadioButton getButton(){
        return button;
    }
    
    final boolean isSelected(){
        return button.isSelected();
    }
    
    final void setSelected(boolean b){
        button.setSelected(b);
        if(b){
            Application.getInstance().getData().setSelectedDataType(dataType);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        setSelected(true);
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
