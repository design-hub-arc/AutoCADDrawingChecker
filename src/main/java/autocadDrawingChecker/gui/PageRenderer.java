package autocadDrawingChecker.gui;

import autocadDrawingChecker.gui.runPage.OutputPage;
import autocadDrawingChecker.gui.chooseCriteria.ChooseCriteriaPage;
import autocadDrawingChecker.gui.chooseFiles.ChooseFilesPage;
import autocadDrawingChecker.logging.Logger;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * The PageRenderer handles rendering and
 * switching between pages.
 * 
 * @author Matt Crow
 */
public class PageRenderer extends JPanel {
    private final JLabel pageTitle;
    private final JPanel content;
    private final CardLayout cards;
    private final JPanel buttons;
    private final JButton prevButton;
    private final JButton nextButton;
    private final HashMap<String, AbstractPage> pages; // need this to map names to pages
    private final ArrayList<String> pageNames;
    private final ArrayList<AbstractPage> pageList;
    private int currPageIdx;
    private AbstractPage currentPage;
    public static final String CHOOSE_FILES = "choose files";
    public static final String CHOOSE_CRITERIA = "choose criteria";
    public static final String OUTPUT = "output";
    
    public PageRenderer(){
        super();
        setLayout(new BorderLayout());
        
        pageTitle = new JLabel("Page title goes here");
        add(pageTitle, BorderLayout.PAGE_START);
        
        content = new JPanel();
        cards = new CardLayout();
        content.setLayout(cards);
        add(content, BorderLayout.CENTER);
        
        buttons = new JPanel();
        prevButton = new JButton("Go Back");
        prevButton.addActionListener((e)->{
            tryPrevPage();
        });
        buttons.add(prevButton);
        nextButton = new JButton("Next Step");
        nextButton.addActionListener((e)->{
            tryNextPage();
        });
        buttons.add(nextButton);
        add(buttons, BorderLayout.PAGE_END);
        
        pages = new HashMap<>(); // populated by addPage
        pageNames = new ArrayList<>();
        pageList = new ArrayList<>();
        currPageIdx = -1;
        addPage(CHOOSE_FILES, new ChooseFilesPage());
        addPage(CHOOSE_CRITERIA, new ChooseCriteriaPage());
        addPage(OUTPUT, new OutputPage());
        
        switchToPage(CHOOSE_FILES);
    }
    
    private void addPage(String title, AbstractPage ap){
        pages.put(title, ap);
        pageNames.add(title);
        pageList.add(ap);
        content.add(ap, title); // for the card layout
    }
    
    public final AbstractPage getPage(String name){
        return pages.get(name);
    }
    
    public void switchToPage(String pageName){
        if(pages.containsKey(pageName)){
            currPageIdx = pageNames.indexOf(pageName);
            cards.show(content, pageName);
            currentPage = pages.get(pageName);
            pageTitle.setText(currentPage.getTitle());
            prevButton.setVisible(currPageIdx > 0);
            nextButton.setVisible(currPageIdx < pageNames.size() - 1);
            revalidate();
            repaint();
        } else {
            Logger.logError("Cannot switchToPage with name " + pageName);
        }
    }
    
    private void tryPrevPage(){
        if(currPageIdx > 0){
            currPageIdx--;
            switchToPage(pageNames.get(currPageIdx));
        }
    }
    private void tryNextPage(){
        if(currPageIdx < pageNames.size()){
            currPageIdx = (currPageIdx + 1) % pageNames.size(); // loop around to first page if we click next on the last one
            switchToPage(pageNames.get(currPageIdx));
        }
    }
}
