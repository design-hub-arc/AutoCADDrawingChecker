package autocadDrawingChecker.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Matt
 */
public class ChooseFilesPage extends AbstractPage implements ActionListener {
    private final SourceExcelFileChooser srcChooser;
    private final CompareExcelFileChooser cmpChooser;
    
    public ChooseFilesPage(AppPane ap) {
        super(ap);
        setLayout(new BorderLayout());
        
        add(new JLabel("Step 1: Choose files to compare"), BorderLayout.PAGE_START);
        
        JPanel choosers = new JPanel();
        choosers.setLayout(new GridLayout(1, 2, 20, 20));
        srcChooser = new SourceExcelFileChooser("Master Comparison File", "choose the master comparison Excel file");
        choosers.add(srcChooser);
        cmpChooser = new CompareExcelFileChooser("Student Files", "choose one or more student files, or a whole folder of them");
        choosers.add(cmpChooser);
        add(choosers, BorderLayout.CENTER);
        
        JPanel end = new JPanel();
        JButton next = new JButton("Next");
        next.addActionListener(this);
        end.add(next);
        add(end, BorderLayout.PAGE_END);
    }
    
    public final File getSrcFile(){
        return srcChooser.getSelected();
    }
    
    public final File[] getCmpFiles(){
        return cmpChooser.getSelected();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(srcChooser.isFileSelected() && cmpChooser.isFileSelected()){
            // next page
        } else {
            JOptionPane.showMessageDialog(this, "Please choose both a master and grade file");
        }
    }
}
