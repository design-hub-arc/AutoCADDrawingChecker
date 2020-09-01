package autocadDrawingChecker.gui;

import autocadDrawingChecker.comparison.AttributeToCompare;
import autocadDrawingChecker.reportGeneration.Grader;
import autocadDrawingChecker.reportGeneration.GradingReport;
import autocadDrawingChecker.reportGeneration.ReportWriter;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Matt
 */
public class AppPane extends JPanel {
    private final ExcelFileChooser srcChooser;
    private final ExcelFileChooser cmpChooser;
    
    public AppPane(){
        super();
        setBackground(Color.GREEN);
        setLayout(new BorderLayout());
        
        JPanel choosers = new JPanel();
        choosers.setLayout(new GridLayout(1, 2));
        srcChooser = new ExcelFileChooser("Master Comparison File", "choose the master comparison Excel file", false);
        choosers.add(srcChooser);
        cmpChooser = new ExcelFileChooser("Student Files", "choose a single student file, or a whole folder of them", true);
        choosers.add(cmpChooser);
        add(choosers, BorderLayout.PAGE_START);
        
        JPanel end = new JPanel();
        JButton run = new JButton("Run Comparison");
        run.addActionListener((e)->{
            if(canCompare()){
                runComparison();
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
        ArrayList<AttributeToCompare> attrs = new ArrayList<>();
        for(AttributeToCompare c : AttributeToCompare.values()){
            attrs.add(c);
        }
        Grader autoGrader = new Grader(srcChooser.getSelectedFile().getAbsolutePath(), cmpChooser.getSelectedFile().getAbsolutePath(), attrs);
        
        GradingReport report = autoGrader.grade();
        System.out.println(report.toString());
        
        try {
            ReportWriter.showSavePopup(report);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
