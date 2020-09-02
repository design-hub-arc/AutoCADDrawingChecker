package autocadDrawingChecker.files;

import autocadDrawingChecker.logging.Logger;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Matt
 */
public class FileChooserUtil {
    public static void askCreateTextFile(String dialogTitle, String textFileContents){
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle(dialogTitle);
        chooser.setDialogType(JFileChooser.SAVE_DIALOG);
        //chooser.setFileFilter(new FileNameExtensionFilter("Text file", new String[]{"txt"}));
        if(chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION){
            // may move this to a file writer util
            try (BufferedWriter buff = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(chooser.getSelectedFile())))) {
                buff.write(textFileContents);
            } catch (IOException ex) {
                Logger.logError(ex);
            }
        }
    }
}
