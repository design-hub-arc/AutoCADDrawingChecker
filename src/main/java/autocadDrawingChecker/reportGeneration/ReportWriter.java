package autocadDrawingChecker.reportGeneration;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Matt
 */
public class ReportWriter {
    public static void showSavePopup(GradingReport report) throws IOException{
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Where would you like to save this report to?");
        chooser.setDialogType(JFileChooser.SAVE_DIALOG);
        chooser.setFileFilter(new FileNameExtensionFilter("Text file", new String[]{"txt"}));
        if(chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION){
            writeReportTo(report, chooser.getSelectedFile());
        }
    }
    public static void writeReportTo(GradingReport report, File to) throws FileNotFoundException, IOException{
        try (BufferedWriter buff = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(to)))) {
            buff.write(report.toString());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
