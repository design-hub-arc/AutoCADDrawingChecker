package autocadDrawingChecker.gui;

import autocadDrawingChecker.logging.ErrorListener;
import autocadDrawingChecker.logging.MessageListener;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;

/**
 *
 * @author Matt
 */
public class TextScrollPane extends JComponent implements MessageListener, ErrorListener {
    private final JScrollPane scroll;
    private final JTextArea textArea;
    
    public TextScrollPane(){
        super();
        setLayout(new BorderLayout());
        
        JPanel tools = new JPanel();
        JButton clearBtn = new JButton("Clear");
        clearBtn.addActionListener((e)->{
            clearText();
        });
        tools.add(clearBtn);
        add(tools, BorderLayout.PAGE_END);
        
        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        scroll = new JScrollPane(textArea);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        add(scroll, BorderLayout.CENTER);
    }
    
    public final void clearText(){
        setText("");
    }
    
    public final void appendText(String appendMe){
        setText(getText() + appendMe);
    }
    
    public final void setText(String newText){
        textArea.setText(newText);
        SwingUtilities.invokeLater(()->{
            JScrollBar bar = scroll.getVerticalScrollBar();
            bar.setValue(bar.getMaximum());
            repaint();
        });
    }
    
    public final String getText(){
        return textArea.getText();
    }

    @Override
    public void messageLogged(String msg) {
        appendText(msg + "\n");
    }

    @Override
    public void errorLogged(String errMsg) {
        messageLogged("Error: " + errMsg);
    }

    @Override
    public void errorLogged(Exception ex) {
        errorLogged(ex.getLocalizedMessage());
    }
}
