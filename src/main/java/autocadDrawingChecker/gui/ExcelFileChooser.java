package autocadDrawingChecker.gui;

import autocadDrawingChecker.files.FileType;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Matt
 */
public class ExcelFileChooser extends JComponent implements ActionListener {
    private File selectedFile;
    private final JTextArea text;
    private final JFileChooser popup;
    
    public ExcelFileChooser(String title, String popupText, boolean allowDir){
        super();
        selectedFile = null;
        
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
        
        popup = new JFileChooser();
        popup.setDialogTitle(popupText);
        popup.setFileSelectionMode((allowDir) ? JFileChooser.FILES_AND_DIRECTORIES : JFileChooser.FILES_ONLY);
        popup.setFileFilter(new FileNameExtensionFilter(FileType.EXCEL.getName(), FileType.EXCEL.getExtensions()));
    }
    
    public final boolean isFileSelected(){
        return selectedFile != null;
    }
    
    public final File getSelectedFile(){
        return selectedFile;
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        if(popup.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
            selectedFile = popup.getSelectedFile();
            text.setText("Selected the file " + selectedFile.getAbsolutePath());
        }
    }
}
