package autocadDrawingChecker.gui;

import autocadDrawingChecker.gui.runPage.OutputPage;
import autocadDrawingChecker.gui.chooseCriteria.ChooseCriteriaPage;
import autocadDrawingChecker.gui.chooseDataType.ChooseDataTypePage;
import autocadDrawingChecker.gui.chooseFiles.ChooseFilesPage;
import autocadDrawingChecker.logging.Logger;
import autocadDrawingChecker.start.Application;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
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
    
    private final ArrayList<String> pageNames; // used to remember page insertion order
    private final HashMap<String, AbstractPage> pages; // need this to map names to pages
    private int currPageIdx;
    
    public static final String CHOOSE_DATA_TYPE = "choose data type";
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
        nextButton.setBackground(Color.GREEN);
        nextButton.setOpaque(true);
        nextButton.addActionListener((e)->{
            tryNextPage();
        });
        buttons.add(nextButton);
        add(buttons, BorderLayout.PAGE_END);
        
        pages = new HashMap<>(); // populated by addPage
        pageNames = new ArrayList<>();
        currPageIdx = 0;
        addPage(CHOOSE_DATA_TYPE, new ChooseDataTypePage());
        addPage(CHOOSE_FILES, new ChooseFilesPage());
        addPage(CHOOSE_CRITERIA, new ChooseCriteriaPage());
        addPage(OUTPUT, new OutputPage());
        updateRenderedPage();
    }
    
    private void addPage(String title, AbstractPage ap){
        pages.put(title, ap);
        pageNames.add(title);
        content.add(ap, title); // for the card layout
    }
    
    public final AbstractPage getPage(String name){
        return pages.get(name);
    }
    
    private void updateRenderedPage(){
        if(currPageIdx < pageNames.size()){
            String pageName = pageNames.get(currPageIdx);
            cards.show(content, pageName);
            pageTitle.setText(pages.get(pageName).getTitle());
            prevButton.setVisible(currPageIdx > 0);
            nextButton.setVisible(currPageIdx < pageNames.size() - 1);
            pages.get(pageName).dataUpdated(Application.getInstance().getData());
            revalidate();
            repaint();
        } else {
            Logger.logError(String.format("Page index %d is out of the bounds 0 <= index < %d.", currPageIdx, pageNames.size()));
        }
    }
    
    private void tryPrevPage(){
        if(currPageIdx > 0){
            currPageIdx--;
            updateRenderedPage();
        }
    }
    private void tryNextPage(){
        if(currPageIdx < pageNames.size() && pages.get(pageNames.get(currPageIdx)).checkIfReadyForNextPage()){
            currPageIdx = (currPageIdx + 1) % pageNames.size(); // loop around to first page if we click next on the last one
            updateRenderedPage();
        }
    }
}
