package autocadDrawingChecker.gui;

import autocadDrawingChecker.logging.Logger;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Matt
 */
public class OutputPage extends AbstractPage {
    private final TextScrollPane output;
    private final JButton run;
    
    public OutputPage(AppPane ap) {
        super(ap);
        
        setLayout(new BorderLayout());
        
        add(new JLabel("Step 3: Click 'run' to run the autograder"), BorderLayout.PAGE_START);
        
        output = new TextScrollPane();
        Logger.addMessageListener(output);
        Logger.addErrorListener(output);
        add(output, BorderLayout.CENTER);
        
        JPanel end = new JPanel();
        JButton back = new JButton("Go Back");
        back.addActionListener((e)->{
            getPaneParent().switchToPage(AppPane.CHOOSE_CRITERIA);
        });
        end.add(back);
        run = new JButton("run");
        run.addActionListener((e)->{
            runAsync();
        });
        end.add(run);
        add(end, BorderLayout.PAGE_END);
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
