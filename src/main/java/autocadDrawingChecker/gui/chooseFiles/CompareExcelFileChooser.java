package autocadDrawingChecker.gui.chooseFiles;

import autocadDrawingChecker.util.FileChooserUtil;
import autocadDrawingChecker.util.FileType;
import autocadDrawingChecker.start.Application;
import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Matt
 */
public class CompareExcelFileChooser extends AbstractExcelFileChooser<File[]> {
    
    public CompareExcelFileChooser(String title, String popupText) {
        super(title, popupText);
    }

    @Override
    public boolean isFileSelected() {
        return getSelected() != null && getSelected().length != 0;
    }
    
    @Override
    protected final void setSelected(File[] fs){
        super.setSelected(fs);
        String[] absPaths = Arrays.stream(fs).map((f)->f.getAbsolutePath()).toArray((size)->new String[size]);
        setText("Selected the files " + String.join(", ", absPaths));
    }
    
    @Override
    protected void selectButtonPressed() {
        FileChooserUtil.askChooseFiles(getPopupTitle(), FileType.EXCEL_OR_FOLDER, (File[] fs)->{
            userSelectedFile(fs);
        });
    }

    @Override
    protected void filesDropped(List<File> files) {
        userSelectedFile(files.toArray(new File[files.size()]));
    }

    @Override
    protected void userSelectedFile(File[] sel) {
        setSelected(sel);
        String[] absPaths = Arrays.stream(sel).map((f)->f.getAbsolutePath()).toArray((s)->new String[s]);
        Application.getInstance().getData().setStudentFilePaths(absPaths);
    }

    @Override
    protected boolean canAccept(File f) {
        return f.isDirectory() || FileType.EXCEL.fileIsOfThisType(f);
    }
}
