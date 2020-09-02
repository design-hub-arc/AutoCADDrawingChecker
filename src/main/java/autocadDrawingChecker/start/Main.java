package autocadDrawingChecker.start;

import autocadDrawingChecker.gui.AppWindow;

/**
 * Main servers as the starting point for the
 * application. Future versions may add support
 * for running the application from the command
 * line, with no GUI.
 * 
 * @author Matt Crow
 */
public class Main {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new AppWindow();
    }
}
