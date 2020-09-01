package autocadDrawingChecker.gui;

import autocadDrawingChecker.comparison.AttributeToCompare;
import autocadDrawingChecker.files.FileChooser;
import autocadDrawingChecker.reportGeneration.Grader;
import autocadDrawingChecker.reportGeneration.GradingReport;
import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;

/**
 *
 * @author Matt
 */
public class AppPane extends JPanel {
    private String masterFilePath;
    private String compareFilePath;
    
    public AppPane(){
        super();
        setBackground(Color.GREEN);
        setLayout(new BorderLayout());
        
        JToolBar toolBar = new JToolBar();
        
        JButton chooseMaster = new JButton("Choose master comparison file");
        chooseMaster.addActionListener((e)->{
            FileChooser.chooseExcelFile("Select the master comparison file", (path)->{
                masterFilePath = path;
            });
        });
        toolBar.add(chooseMaster);
        
        JButton chooseCompare = new JButton("Choose files to grade");
        chooseCompare.addActionListener((e)->{
            FileChooser.chooseExcelFileOrDir("Select the files to grade", (path)->{
                compareFilePath = path;
            });
        });
        toolBar.add(chooseCompare);
        
        add(toolBar, BorderLayout.PAGE_START);
        
        JPanel end = new JPanel();
        JButton run = new JButton("Run Comparison");
        run.addActionListener((e)->{
            if(canCompare()){
                runComparison();
            } else {
                JOptionPane.showMessageDialog(chooseCompare, "Please choose both a master and grade file");
            }
        });
        end.add(run);
        add(end, BorderLayout.PAGE_END);
    }
    
    private boolean canCompare(){
        return masterFilePath != null && compareFilePath != null;
    }
    
    private void runComparison(){
        ArrayList<AttributeToCompare> attrs = new ArrayList<>();
        for(AttributeToCompare c : AttributeToCompare.values()){
            attrs.add(c);
        }
        Grader autoGrader = new Grader(masterFilePath, compareFilePath, attrs);
        
        GradingReport report = autoGrader.grade();
        System.out.println(report.toString());
    }
}
