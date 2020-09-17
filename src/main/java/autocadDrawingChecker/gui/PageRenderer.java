package autocadDrawingChecker.gui;

import autocadDrawingChecker.gui.runPage.OutputPage;
import autocadDrawingChecker.gui.chooseCriteria.ChooseCriteriaPage;
import autocadDrawingChecker.gui.chooseFiles.ChooseFilesPage;
import autocadDrawingChecker.logging.Logger;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.util.HashMap;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Matt
 */
public class PageRenderer extends JPanel {
    private final JLabel pageTitle;
    private final JPanel content;
    private final CardLayout cards;
    private final JPanel buttons;
    private final HashMap<String, AbstractPage> pages;
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
    
    public final AbstractPage getPage(String name){
        return pages.get(name);
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
}
