package autocadDrawingChecker.gui.chooseFiles;

import autocadDrawingChecker.gui.AbstractPage;
import autocadDrawingChecker.gui.AppPane;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * The ChooseFilesPage is where the user selects which files they want to grade.
 * 
 * @author Matt Crow
 */
public class ChooseFilesPage extends AbstractPage implements ActionListener {
    private final SourceExcelFileChooser srcChooser;
    private final CompareExcelFileChooser cmpChooser;
    
    public ChooseFilesPage(AppPane ap) {
        super(ap, "Step 1: Choose files to compare");
        setLayout(new GridLayout(1, 1));
        
        JPanel choosers = new JPanel();
        choosers.setLayout(new GridLayout(1, 2, 20, 20));
        srcChooser = new SourceExcelFileChooser("Master Comparison File", "choose the master comparison Excel file");
        choosers.add(srcChooser);
        cmpChooser = new CompareExcelFileChooser("Student Files", "choose one or more student files, or a whole folder of them");
        choosers.add(cmpChooser);
        add(choosers);
        
        JButton next = new JButton("Next");
        next.addActionListener(this);
        addButton(next);
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
            getPaneParent().switchToPage(AppPane.CHOOSE_CRITERIA);
        } else {
            JOptionPane.showMessageDialog(this, "Please choose both a master and grade file");
        }
    }
}
