package autocadDrawingChecker.reportGeneration;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 *
 * @author Matt
 */
public class ReportWriter {
    public static void writeReportTo(GradingReport report, File to) throws FileNotFoundException, IOException{
        try (BufferedWriter buff = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(to)))) {
            buff.write(report.toString());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
