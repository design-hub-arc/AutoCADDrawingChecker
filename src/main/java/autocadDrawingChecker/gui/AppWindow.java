package autocadDrawingChecker.gui;

import javax.swing.JFrame;

/**
 *
 * @author Matt
 */
public class AppWindow extends JFrame {
    public AppWindow(){
        super();
        setContentPane(new AppPane());
        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        revalidate();
        repaint();
    }
}
