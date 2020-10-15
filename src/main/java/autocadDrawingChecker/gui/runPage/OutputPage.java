package autocadDrawingChecker.gui.runPage;

import autocadDrawingChecker.grading.GradingReport;
import autocadDrawingChecker.gui.AbstractPage;
import autocadDrawingChecker.logging.Logger;
import autocadDrawingChecker.start.Application;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Matt
 */
public class OutputPage extends AbstractPage {
    private final TextScrollPane output;
    private final JButton run;
    
    public OutputPage() {
        super("Click 'run' to run the autograder");
        
        setLayout(new BorderLayout());
        
        output = new TextScrollPane();
        Logger.addMessageListener(output);
        Logger.addErrorListener(output);
        add(output, BorderLayout.CENTER);
        
        JPanel bottom = new JPanel();
        run = new JButton("run");
        run.addActionListener((e)->{
            runAsync();
        });
        bottom.add(run);
        add(bottom, BorderLayout.PAGE_END);
    }
    
    private void runAsync(){
        run.setEnabled(false);
        Logger.log("Grading...");
        new Thread(){
            @Override
            public void run(){
                GradingReport report = Application.getInstance().grade();
                Logger.log(report.toString());
        
                GradingReportSaver.saveReport(report, (savedTo)->{
                    Logger.log("Successfully saved report to " + savedTo.getAbsolutePath());
                });
                
                run.setEnabled(true);
                Logger.log("Done grading!");
            }
        }.start();
    }

    @Override
    protected boolean checkIfReadyForNextPage() {
        boolean ready = run.isEnabled();
        if(!ready){
            JOptionPane.showMessageDialog(this, "Please wait for the program to finish");
        }
        return ready;
    }
}
