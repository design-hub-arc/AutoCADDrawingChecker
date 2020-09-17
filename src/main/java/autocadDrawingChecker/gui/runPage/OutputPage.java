package autocadDrawingChecker.gui.runPage;

import autocadDrawingChecker.files.FileChooserUtil;
import autocadDrawingChecker.files.FileType;
import autocadDrawingChecker.grading.GradingReport;
import autocadDrawingChecker.gui.AbstractPage;
import autocadDrawingChecker.gui.PageRenderer;
import autocadDrawingChecker.logging.Logger;
import autocadDrawingChecker.start.Application;
import java.awt.GridLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.JButton;

/**
 *
 * @author Matt
 */
public class OutputPage extends AbstractPage {
    private final TextScrollPane output;
    private final JButton run;
    
    public OutputPage(PageRenderer ap) {
        super(ap, "Step 3: Click 'run' to run the autograder");
        
        setLayout(new GridLayout(1, 1));
        
        output = new TextScrollPane();
        Logger.addMessageListener(output);
        Logger.addErrorListener(output);
        add(output);
        
        JButton back = new JButton("Go Back");
        back.addActionListener((e)->{
            getPaneParent().switchToPage(PageRenderer.CHOOSE_CRITERIA);
        });
        addButton(back);
        
        run = new JButton("run");
        run.addActionListener((e)->{
            runAsync();
        });
        addButton(run);
    }
    
    private void runAsync(){
        run.setEnabled(false);
        Logger.log("Grading...");
        new Thread(){
            @Override
            public void run(){
                GradingReport report = Application.getInstance().grade();
                Logger.log(report.toString());
        
                FileChooserUtil.askChooseFile("Where would you like to save this report to?", FileType.EXCEL, (File f)->{
                    try (FileOutputStream out = new FileOutputStream(f)) {
                        report.getAsWorkBook().write(out);
                    } catch (FileNotFoundException ex) {
                        Logger.logError(ex);
                    } catch (IOException ex) {
                        Logger.logError(ex);
                    }
                });
                run.setEnabled(true);
                Logger.log("Done grading!");
            }
        }.start();
    }
}
