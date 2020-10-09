package autocadDrawingChecker.util;

import autocadDrawingChecker.logging.Logger;
import autocadDrawingChecker.start.Application;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Matt
 */
public class FileChooserUtil {
    private final JFileChooser chooser;
    
    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("MM-dd-uuuu_hh_mm_a");
    
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
    
    public static void askSaveFile(String dialogTitle, String fileNameMiddle, String ext, Consumer<File> andThen){
        FileChooserUtil fcu = new FileChooserUtil(dialogTitle, JFileChooser.SAVE_DIALOG, JFileChooser.DIRECTORIES_ONLY);
        if(fcu.chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION){
            File folder = fcu.chooser.getSelectedFile();
            LocalDateTime currDate = LocalDateTime.now();
            String dateStr = currDate.format(DATE_FORMAT);
            String desiredFilePath = Paths.get(
                folder.getAbsolutePath(), 
                String.format("%s %s %s.%s", Application.APP_NAME, dateStr, fileNameMiddle, ext)
            ).toString();
            File newFile = new File(desiredFilePath);
            
            int nextNum = 1;
            while(newFile.exists()){
                newFile = new File(desiredFilePath.replace(String.format(".%s", ext), String.format("-%d.%s", nextNum, ext)));
                nextNum++;
            }
            andThen.accept(newFile);
        }
    }
    
    public static void askWhereSaveLog(String dialogTitle, String textFileContents){
        askSaveFile(dialogTitle, "log", "txt", (f)->{
            // may move this to a file writer util
            try (BufferedWriter buff = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f)))) {
                buff.write(textFileContents);
            } catch (IOException ex) {
                Logger.logError(ex);
            }
        });
    }
}
