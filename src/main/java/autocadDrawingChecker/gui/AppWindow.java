package autocadDrawingChecker.gui;

import autocadDrawingChecker.logging.Logger;
import java.awt.Toolkit;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

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
        
        JMenuBar menuBar = new JMenuBar();
        JMenu logMenu = new JMenu("Log");
        JMenuItem saveLog = new JMenuItem("Save Log");
        saveLog.addActionListener((e)->{
            saveLog();
        });
        logMenu.add(saveLog);
        menuBar.add(logMenu);
        setJMenuBar(menuBar);
        
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
    
    private void saveLog(){
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogType(JFileChooser.SAVE_DIALOG);
        chooser.setFileFilter(new FileNameExtensionFilter("Text file", new String[]{"txt"}));
        if(chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION){
            try (BufferedWriter buff = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(chooser.getSelectedFile())))) {
                buff.write(Logger.getLog());
            } catch (IOException ex) {
                Logger.logError(ex);
            }
        }
    }
}
