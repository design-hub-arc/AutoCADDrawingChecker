package autocadDrawingChecker.gui;

import autocadDrawingChecker.comparison.GradingCriteriaLoader;
import autocadDrawingChecker.files.FileChooserUtil;
import autocadDrawingChecker.logging.Logger;
import autocadDrawingChecker.reportGeneration.Grader;
import autocadDrawingChecker.reportGeneration.GradingReport;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.Arrays;
import java.util.HashMap;
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
    private final CriteriaSelectionList critList;
    
    private final HashMap<String, AbstractPage> pages;
    public static final String CHOOSE_FILES = "choose files";
    public static final String CHOOSE_CRITERIA = "choose criteria";
    public static final String OUTPUT = "output";
    
    public AppPane(){
        super();
        pages = new HashMap<>(); // populated by addPage
        
        CardLayout layout = new CardLayout();
        setLayout(layout);
        addPage(CHOOSE_FILES, new ChooseFilesPage(this));
        addPage(CHOOSE_CRITERIA, new ChooseCriteriaPage(this));
        addPage(OUTPUT, new OutputPage(this));
        layout.show(this, CHOOSE_FILES);
        
        
        //JPanel center = new JPanel();
        
        
        //center.setLayout(new GridLayout(1, 1));
        output = new TextScrollPane();
        Logger.addMessageListener(output);
        Logger.addErrorListener(output);
        //center.add(output);
        //center.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        
        //add(center, BorderLayout.CENTER);
        
        critList = new CriteriaSelectionList();
        //center.add(critList);
        /*
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
        add(end, BorderLayout.PAGE_END);*/
    }
    
    private void addPage(String title, AbstractPage ap){
        pages.put(title, ap);
        add(ap, title); // for the card layout
    }
    
    private void runComparison(){
        Grader autoGrader = new Grader(
            ((ChooseFilesPage)pages.get(CHOOSE_FILES)).getSrcFile().getAbsolutePath(), 
            Arrays.stream(((ChooseFilesPage)pages.get(CHOOSE_FILES)).getCmpFiles()).map((f)->f.getAbsolutePath()).toArray((size)->new String[size]), 
            critList.getSelectedCriteria()
        );
        
        GradingReport report = autoGrader.grade();
        Logger.log(report.toString());
        
        FileChooserUtil.askCreateTextFile("Where would you like to save this report to?", report.toString());
    }
}
