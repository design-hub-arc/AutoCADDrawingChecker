package autocadDrawingChecker.gui;

import autocadDrawingChecker.logging.Logger;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Matt
 */
public class AppWindow extends JFrame {
    public AppWindow(){
        super();
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            Logger.logError(ex);
        }
        setContentPane(new AppPane());
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
}
