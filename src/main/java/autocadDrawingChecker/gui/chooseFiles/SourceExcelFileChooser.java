package autocadDrawingChecker.gui.chooseFiles;

import autocadDrawingChecker.files.FileChooserUtil;
import autocadDrawingChecker.files.FileType;
import autocadDrawingChecker.start.Application;
import java.io.File;

/**
 *
 * @author Matt
 */
public class SourceExcelFileChooser extends AbstractExcelFileChooser<File> {
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
        FileChooserUtil.askChooseFile(getPopupTitle(), FileType.EXCEL, (File f)->{
            setSelected(f);
            Application.getInstance().getData().setInstructorFilePath(f.getAbsolutePath());
        });
    }
}
