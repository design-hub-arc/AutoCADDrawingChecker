package autocadDrawingChecker.gui;

import autocadDrawingChecker.comparison.GradingCriteriaLoader;
import autocadDrawingChecker.files.FileChooserUtil;
import autocadDrawingChecker.logging.Logger;
import autocadDrawingChecker.reportGeneration.Grader;
import autocadDrawingChecker.reportGeneration.GradingReport;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

/**
 *
 * @author Matt
 */
public class AppPane extends JPanel {
    private final TextScrollPane output;
    private final ExcelFileChooser srcChooser;
    private final ExcelFileChooser cmpChooser;
    
    public AppPane(){
        super();
        setBackground(Color.GREEN);
        setLayout(new BorderLayout());
        
        JPanel choosers = new JPanel();
        choosers.setLayout(new GridLayout(1, 2, 20, 20));
        srcChooser = new ExcelFileChooser("Master Comparison File", "choose the master comparison Excel file", false);
        choosers.add(srcChooser);
        cmpChooser = new ExcelFileChooser("Student Files", "choose a single student file, or a whole folder of them", true);
        choosers.add(cmpChooser);
        add(choosers, BorderLayout.PAGE_START);
        
        JPanel center = new JPanel();
        center.setLayout(new GridLayout(1, 1));
        output = new TextScrollPane();
        Logger.addMessageListener(output);
        Logger.addErrorListener(output);
        center.add(output);
        center.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        add(center, BorderLayout.CENTER);
        
        JPanel end = new JPanel();
        JButton run = new JButton("Run Comparison");
        run.addActionListener((e)->{
            if(canCompare()){
                new Thread(){
                    @Override
                    public void run(){
                        runComparison();
                    }
                }.start();
            } else {
                JOptionPane.showMessageDialog(run, "Please choose both a master and grade file");
            }
        });
        end.add(run);
        add(end, BorderLayout.PAGE_END);
    }
    
    private boolean canCompare(){
        return srcChooser.isFileSelected() && cmpChooser.isFileSelected();
    }
    
    private void runComparison(){
        Grader autoGrader = new Grader(
            srcChooser.getSelectedFile().getAbsolutePath(), 
            cmpChooser.getSelectedFile().getAbsolutePath(), 
            GradingCriteriaLoader.getAllCriteria()
        );
        
        GradingReport report = autoGrader.grade();
        Logger.log(report.toString());
        
        FileChooserUtil.askCreateTextFile("Where would you like to save this report to?", report.toString());
    }
}
