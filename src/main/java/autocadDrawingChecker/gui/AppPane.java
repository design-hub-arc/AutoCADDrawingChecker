package autocadDrawingChecker.gui;

import autocadDrawingChecker.gui.runPage.OutputPage;
import autocadDrawingChecker.gui.chooseCriteria.ChooseCriteriaPage;
import autocadDrawingChecker.gui.chooseFiles.ChooseFilesPage;
import autocadDrawingChecker.files.FileChooserUtil;
import autocadDrawingChecker.files.FileType;
import autocadDrawingChecker.logging.Logger;
import autocadDrawingChecker.grading.Grader;
import autocadDrawingChecker.grading.GradingReport;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Matt
 */
public class AppPane extends JPanel {
    private final JLabel pageTitle;
    private final JPanel content;
    private final CardLayout cards;
    private final JPanel buttons;
    private final HashMap<String, AbstractPage> pages;
    private AbstractPage currentPage;
    public static final String CHOOSE_FILES = "choose files";
    public static final String CHOOSE_CRITERIA = "choose criteria";
    public static final String OUTPUT = "output";
    
    public AppPane(){
        super();
        setLayout(new BorderLayout());
        
        pageTitle = new JLabel("Page title goes here");
        add(pageTitle, BorderLayout.PAGE_START);
        
        content = new JPanel();
        cards = new CardLayout();
        content.setLayout(cards);
        add(content, BorderLayout.CENTER);
        
        buttons = new JPanel();
        add(buttons, BorderLayout.PAGE_END);
        
        pages = new HashMap<>(); // populated by addPage
        addPage(CHOOSE_FILES, new ChooseFilesPage(this));
        addPage(CHOOSE_CRITERIA, new ChooseCriteriaPage(this));
        addPage(OUTPUT, new OutputPage(this));
        
        switchToPage(CHOOSE_FILES);
    }
    
    private void addPage(String title, AbstractPage ap){
        pages.put(title, ap);
        content.add(ap, title); // for the card layout
    }
    
    public void switchToPage(String pageName){
        if(pages.containsKey(pageName)){
            cards.show(content, pageName);
            currentPage = pages.get(pageName);
            pageTitle.setText(currentPage.getTitle());
            buttons.removeAll();
            currentPage.getButtons().forEach(buttons::add);
            revalidate();
            repaint();
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
        
        FileChooserUtil.askChooseFile("Where would you like to save this report to?", FileType.EXCEL, (File f)->{
            try (FileOutputStream out = new FileOutputStream(f)) {
                report.getAsWorkBook().write(out);
            } catch (FileNotFoundException ex) {
                Logger.logError(ex);
            } catch (IOException ex) {
                Logger.logError(ex);
            }
        });
        //FileChooserUtil.askCreateTextFile("Where would you like to save this report to?", report.toString());
    }
}
