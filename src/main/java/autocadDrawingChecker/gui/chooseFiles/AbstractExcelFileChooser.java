package autocadDrawingChecker.gui.chooseFiles;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * @author Matt
 * @param <T> the type returned by AbstractExcelFileChooser::getSelected()
 */
public abstract class AbstractExcelFileChooser<T> extends JComponent implements ActionListener {
    private T selected;
    private final JTextArea text;
    private final String popupTitle;
    
    public AbstractExcelFileChooser(String title, String popupText){
        super();
        
        setLayout(new BorderLayout());
        
        JPanel top = new JPanel();
        top.add(new JLabel(title));
        add(top, BorderLayout.PAGE_START);
        
        text = new JTextArea("No file selected yet");
        text.setEditable(false);
        text.setLineWrap(true);
        text.setWrapStyleWord(true);
        add(text, BorderLayout.CENTER);
        
        JPanel bottom = new JPanel();
        JButton button = new JButton("Select a file");
        button.addActionListener(this);
        bottom.add(button);
        add(bottom, BorderLayout.PAGE_END);
        popupTitle = popupText;
        
        selected = null;
    }
    
    protected final String getPopupTitle(){
        return popupTitle;
    }
    
    protected final void setText(String newText){
        text.setText(newText);
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        selectButtonPressed();
    }
    
    protected void setSelected(T sel){
        selected = sel;
    }
    
    public final T getSelected(){
        return selected;
    }
    
    public abstract boolean isFileSelected();
    protected abstract void selectButtonPressed(); 
}
