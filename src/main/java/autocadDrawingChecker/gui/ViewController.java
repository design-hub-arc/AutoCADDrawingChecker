package autocadDrawingChecker.gui;

import autocadDrawingChecker.util.FileChooserUtil;
import autocadDrawingChecker.logging.Logger;
import autocadDrawingChecker.start.Application;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.UIManager;

/**
 *
 * @author Matt
 */
public class ViewController extends JFrame {
    private final PageRenderer pane;
    
    public ViewController(){
        super();
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            Logger.logError(ex);
        }
        
        setTitle(Application.APP_NAME);
        
        JMenuBar menuBar = new JMenuBar();
        JMenu logMenu = new JMenu("Log");
        JMenuItem saveLog = new JMenuItem("Save Log");
        saveLog.addActionListener((e)->{
            FileChooserUtil.askCreateTextFile("Where do you want to save this log? (don't forget to name it)", Logger.getLog());
        });
        logMenu.add(saveLog);
        menuBar.add(logMenu);
        setJMenuBar(menuBar);
        
        pane = new PageRenderer();
        setContentPane(pane);
        // fullscreen
        setSize(
            (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(), 
            (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() - Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration()).bottom
        );
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        revalidate();
        repaint();
    }
    
    public final PageRenderer getAppPane(){
        return pane;
    }
}
