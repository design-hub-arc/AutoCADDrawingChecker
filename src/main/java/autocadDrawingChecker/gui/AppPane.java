package autocadDrawingChecker.gui;

import autocadDrawingChecker.autocadData.AutoCADExcelParser;
import autocadDrawingChecker.autocadData.AutoCADExport;
import autocadDrawingChecker.comparison.AttributeToCompare;
import autocadDrawingChecker.comparison.ExportComparison;
import autocadDrawingChecker.files.FileChooser;
import java.awt.BorderLayout;
import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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
        
        JButton chooseCompare = new JButton("Choose file to grade");
        chooseCompare.addActionListener((e)->{
            FileChooser.chooseExcelFile("Select the file to grade", (path)->{
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
    
    private final boolean canCompare(){
        return masterFilePath != null && compareFilePath != null;
    }
    
    private final void runComparison(){
        try {
            AutoCADExport master = AutoCADExcelParser.parse(new FileInputStream(masterFilePath));
            AutoCADExport cmp = AutoCADExcelParser.parse(new FileInputStream(compareFilePath));
            ArrayList<AttributeToCompare> attrs = new ArrayList<>();
            for(AttributeToCompare c : AttributeToCompare.values()){
                attrs.add(c);
            }
            ExportComparison comparison = new ExportComparison(master, cmp, attrs);
            System.out.println(comparison);
            double score = comparison.runComparison();
            System.out.println("Score: " + score);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
