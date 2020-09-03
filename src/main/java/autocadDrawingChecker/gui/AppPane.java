package autocadDrawingChecker.gui;

import autocadDrawingChecker.files.FileChooserUtil;
import autocadDrawingChecker.logging.Logger;
import autocadDrawingChecker.reportGeneration.Grader;
import autocadDrawingChecker.reportGeneration.GradingReport;
import java.awt.CardLayout;
import java.util.Arrays;
import java.util.HashMap;
import javax.swing.JPanel;

/**
 *
 * @author Matt
 */
public class AppPane extends JPanel {
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
    }
    
    private void addPage(String title, AbstractPage ap){
        pages.put(title, ap);
        add(ap, title); // for the card layout
    }
    
    public void switchToPage(String pageName){
        if(pages.containsKey(pageName)){
            ((CardLayout)getLayout()).show(this, pageName);
        } else {
            Logger.logError("Cannot switchToPage with name " + pageName);
        }
    }
    
    public void runComparison(){
        Grader autoGrader = new Grader(
            ((ChooseFilesPage)pages.get(CHOOSE_FILES)).getSrcFile().getAbsolutePath(), 
            Arrays.stream(((ChooseFilesPage)pages.get(CHOOSE_FILES)).getCmpFiles()).map((f)->f.getAbsolutePath()).toArray((size)->new String[size]), 
            ((ChooseCriteriaPage)pages.get(CHOOSE_CRITERIA)).getSelectedCriteria()
        );
        
        GradingReport report = autoGrader.grade();
        Logger.log(report.toString());
        
        FileChooserUtil.askCreateTextFile("Where would you like to save this report to?", report.toString());
    }
}
