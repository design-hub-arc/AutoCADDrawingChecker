package autocadDrawingChecker.gui;

import autocadDrawingChecker.files.FileChooserUtil;
import autocadDrawingChecker.files.FileType;
import java.io.File;
import java.util.Arrays;

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
    protected void selectButtonPressed() {
        FileChooserUtil.askChooseFiles(getPopupTitle(), FileType.EXCEL, (File[] fs)->{
            setSelected(fs);
            String[] absPaths = Arrays.stream(fs).map((f)->f.getAbsolutePath()).toArray((size)->new String[size]);
            setText("Selected the files " + String.join(", ", absPaths));
        });
    }
}
