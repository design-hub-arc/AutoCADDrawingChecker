package autocadDrawingChecker.gui.chooseFiles;

import autocadDrawingChecker.util.FileChooserUtil;
import autocadDrawingChecker.util.FileType;
import autocadDrawingChecker.start.Application;
import java.io.File;
import java.util.List;

/**
 *
 * @author Matt
 */
public class SourceExcelFileChooser extends AbstractFileChooser<File>{
    public SourceExcelFileChooser(String title, String popupText) {
        super(title, popupText);
    }

    @Override
    public boolean isFileSelected() {
        return getSelected() != null;
    }
    
    @Override
    protected final void setSelected(File f){
        super.setSelected(f);
        setText("Selected the file " + f.getAbsolutePath());
    }

    @Override
    protected void selectButtonPressed() {
        FileChooserUtil.askChooseFile(getPopupTitle(), FileType.NON_FOLDER, (File f)->{
            userSelectedFile(f);
        });
    }

    @Override
    protected void filesDropped(List<File> files) {
        if(!files.isEmpty()){
            userSelectedFile(files.get(0));
        }
    }

    @Override
    protected void userSelectedFile(File sel) {
        setSelected(sel);
        Application.getInstance().getData().setInstructorFilePath(sel.getAbsolutePath());
    }

    @Override
    protected boolean canAccept(File f) {
        return FileType.NON_FOLDER.fileIsOfThisType(f);
    }
}
