package autocadDrawingChecker.gui.runPage;

import autocadDrawingChecker.gui.AbstractPage;
import autocadDrawingChecker.gui.AppPane;
import autocadDrawingChecker.logging.Logger;
import java.awt.GridLayout;
import javax.swing.JButton;

/**
 *
 * @author Matt
 */
public class OutputPage extends AbstractPage {
    private final TextScrollPane output;
    private final JButton run;
    
    public OutputPage(AppPane ap) {
        super(ap, "Step 3: Click 'run' to run the autograder");
        
        setLayout(new GridLayout(1, 1));
        
        output = new TextScrollPane();
        Logger.addMessageListener(output);
        Logger.addErrorListener(output);
        add(output);
        
        JButton back = new JButton("Go Back");
        back.addActionListener((e)->{
            getPaneParent().switchToPage(AppPane.CHOOSE_CRITERIA);
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
                getPaneParent().runComparison();
                run.setEnabled(true);
                Logger.log("Done grading!");
            }
        }.start();
    }
}
