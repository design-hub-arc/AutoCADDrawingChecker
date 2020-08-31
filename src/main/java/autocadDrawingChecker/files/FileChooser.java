package autocadDrawingChecker.files;

import java.util.function.Consumer;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Matt
 */
public class FileChooser {
    
    public static final void chooseExcelFileOrDir(String text, Consumer<String> andThen){
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle(text);
        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        chooser.setFileFilter(new FileNameExtensionFilter(FileType.EXCEL.getName(), FileType.EXCEL.getExtensions()));
        if(chooser.showOpenDialog(chooser) == JFileChooser.APPROVE_OPTION){
            andThen.accept(chooser.getSelectedFile().getAbsolutePath());
        }
    }
    public static final void chooseExcelFile(String text, Consumer<String> andThen){
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle(text);
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setFileFilter(new FileNameExtensionFilter(FileType.EXCEL.getName(), FileType.EXCEL.getExtensions()));
        if(chooser.showOpenDialog(chooser) == JFileChooser.APPROVE_OPTION){
            andThen.accept(chooser.getSelectedFile().getAbsolutePath());
        }
    }
}
