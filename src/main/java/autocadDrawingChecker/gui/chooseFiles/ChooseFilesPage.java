package autocadDrawingChecker.gui.chooseFiles;

import autocadDrawingChecker.gui.AbstractPage;
import java.awt.GridLayout;
import java.io.File;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * The ChooseFilesPage is where the user selects which files they want to grade.
 * 
 * @author Matt Crow
 */
public class ChooseFilesPage extends AbstractPage {
    private final SourceExcelFileChooser srcChooser;
    private final CompareExcelFileChooser cmpChooser;
    
    public ChooseFilesPage() {
        super("Choose files to compare");
        setLayout(new GridLayout(1, 1));
        
        JPanel choosers = new JPanel();
        choosers.setLayout(new GridLayout(1, 2, 20, 20));
        srcChooser = new SourceExcelFileChooser("Master Comparison File", "choose the master comparison Excel file");
        choosers.add(srcChooser);
        cmpChooser = new CompareExcelFileChooser("Student Files", "choose one or more student files, or a whole folder of them");
        choosers.add(cmpChooser);
        add(choosers);
    }
    
    public final void setSrcFile(File f){
        srcChooser.setSelected(f);
    }
    public final void setCmpFiles(File[] f){
        cmpChooser.setSelected(f);
    }
    
    public final File getSrcFile(){
        return srcChooser.getSelected();
    }
    
    public final File[] getCmpFiles(){
        return cmpChooser.getSelected();
    }

    @Override
    protected boolean checkIfReadyForNextPage() {
        boolean ready = srcChooser.isFileSelected() && cmpChooser.isFileSelected();
        if(!ready){
            JOptionPane.showMessageDialog(this, "Please choose both a master and grade file");
        }
        return ready;
    }
}
