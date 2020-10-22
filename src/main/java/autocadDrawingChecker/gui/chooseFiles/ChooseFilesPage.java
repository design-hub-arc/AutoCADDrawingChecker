package autocadDrawingChecker.gui.chooseFiles;

import autocadDrawingChecker.gui.AbstractPage;
import autocadDrawingChecker.start.DrawingCheckerData;
import java.awt.GridLayout;
import java.io.File;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Arrays;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;

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
        setBackground(Color.CYAN);
        
        srcChooser = new SourceExcelFileChooser("Instructor File", "choose the instructor Excel file");
        cmpChooser = new CompareExcelFileChooser("Student Files", "choose one or more student files, or a whole folder of them");
        
        Dimension d = new Dimension(50, 50);
        srcChooser.setMinimumSize(d);
        cmpChooser.setMinimumSize(d);
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, srcChooser, cmpChooser);
        split.setResizeWeight(0.5);
        
        add(split);
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
            JOptionPane.showMessageDialog(this, "Please choose both an instructor file and at least one student file");
        }
        return ready;
    }

    @Override
    protected void dataUpdated(DrawingCheckerData newData) {
        if(newData.isInstructorFilePathSet()){
            srcChooser.setSelected(new File(newData.getInstructorFilePath()));
        }
        if(newData.isStudentFilePathsSet()){
            File[] fs = Arrays.stream(newData.getStudentFilePaths()).map((p)->new File(p)).toArray((s)->new File[s]);
            cmpChooser.setSelected(fs);
        }
    }
}
