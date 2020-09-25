package autocadDrawingChecker.util;

import autocadDrawingChecker.logging.Logger;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Consumer;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Matt
 */
public class FileChooserUtil {
    private final JFileChooser chooser;
    
    public FileChooserUtil(String dialogTitle, int dialogType, int fileSelectionMode){
        chooser = new JFileChooser();
        chooser.setDialogTitle(dialogTitle);
        chooser.setDialogType(dialogType);
        chooser.setFileSelectionMode(fileSelectionMode);
    }
    
    public static void askChooseFile(String dialogTitle, FileType allowedType, Consumer<File> andThen){
        FileChooserUtil fcu = new FileChooserUtil(dialogTitle, JFileChooser.OPEN_DIALOG, JFileChooser.FILES_ONLY);
        fcu.chooser.setFileFilter(new FileNameExtensionFilter(allowedType.getName(), allowedType.getExtensions()));
        fcu.chooser.setMultiSelectionEnabled(false);
        if(fcu.chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
            andThen.accept(fcu.chooser.getSelectedFile());
        }
    }
    
    public static void askChooseFiles(String dialogTitle, FileType allowedType, Consumer<File[]> andThen){
        FileChooserUtil fcu = new FileChooserUtil(dialogTitle, JFileChooser.OPEN_DIALOG, JFileChooser.FILES_AND_DIRECTORIES);
        fcu.chooser.setFileFilter(new FileNameExtensionFilter(allowedType.getName(), allowedType.getExtensions()));
        fcu.chooser.setMultiSelectionEnabled(true);
        if(fcu.chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
            andThen.accept(fcu.chooser.getSelectedFiles());
        }
    }
    public static void askCreateTextFile(String dialogTitle, String textFileContents){
        FileChooserUtil fcu = new FileChooserUtil(dialogTitle, JFileChooser.SAVE_DIALOG, JFileChooser.FILES_AND_DIRECTORIES);
        if(fcu.chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION){
            File f = fcu.chooser.getSelectedFile();
            String fStrPath = f.getAbsolutePath();
            if(!fStrPath.endsWith(".txt")){
                // rename it if it's extension is incorrect
                Path fPath = f.toPath();
                String pathStrIWant = fStrPath + ".txt";
                try {                    
                    if(Files.exists(fPath)){
                        // rename it if it already exists.
                        Files.move(fPath, Paths.get(pathStrIWant));
                    }
                    // regardless, redirect f to point to this new file path
                    f = new File(pathStrIWant);
                } catch(Exception ex){
                    Logger.logError(ex);
                }
            }
            // may move this to a file writer util
            try (BufferedWriter buff = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f)))) {
                buff.write(textFileContents);
            } catch (IOException ex) {
                Logger.logError(ex);
            }
        }
    }
}
