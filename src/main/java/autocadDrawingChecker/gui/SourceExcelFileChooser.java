package autocadDrawingChecker.gui;

import autocadDrawingChecker.files.FileChooserUtil;
import autocadDrawingChecker.files.FileType;
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
    protected void selectButtonPressed() {
        FileChooserUtil.askChooseFile(getPopupTitle(), FileType.EXCEL, (File f)->{
            setSelected(f);
            setText("Selected the file " + f.getAbsolutePath());
        });
    }
}
