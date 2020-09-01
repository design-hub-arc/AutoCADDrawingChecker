package autocadDrawingChecker.files;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
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
    public static final void createFile(String msgText, Consumer<File> andThen){
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle(msgText);
        chooser.setDialogType(JFileChooser.SAVE_DIALOG);
        if(chooser.showSaveDialog(chooser) == JFileChooser.APPROVE_OPTION){
            andThen.accept(chooser.getSelectedFile());
        }
    }
    
    public static void main(String[] args){
        createFile("Test", (f)->{
            try {
                new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f))).write("Testing");
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            System.out.println(f.getAbsolutePath());
        });
    }
}
