package autocadDrawingChecker.gui.runPage;

import autocadDrawingChecker.util.FileChooserUtil;
import autocadDrawingChecker.util.FileType;
import autocadDrawingChecker.grading.GradingReport;
import autocadDrawingChecker.logging.Logger;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Consumer;

/**
 * The GradingReportSaver is a small helper class used to write
 * a GradingReport to an Excel file.
 * 
 * @author Matt Crow
 */
public class GradingReportSaver {
    public static final void saveReport(GradingReport report, Consumer<File> andThen){
        FileChooserUtil.askSaveFile("Where would you like to save this report to?", "Grading Report", "xlsx", (File f)->{
            try (FileOutputStream out = new FileOutputStream(f)) {
                report.getAsWorkBook().write(out);
            } catch (FileNotFoundException ex) {
                Logger.logError(ex);
            } catch (IOException ex) {
                Logger.logError(ex);
            }
            andThen.accept(f);
        });
    }
}
