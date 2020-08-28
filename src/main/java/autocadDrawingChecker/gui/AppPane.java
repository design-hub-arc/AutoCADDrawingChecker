package autocadDrawingChecker.gui;

import autocadDrawingChecker.files.FileChooser;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;

/**
 *
 * @author Matt
 */
public class AppPane extends JPanel {
    public AppPane(){
        super();
        setBackground(Color.GREEN);
        setLayout(new BorderLayout());
        
        JToolBar toolBar = new JToolBar();
        JButton open = new JButton("Choose master comparison file");
        open.addActionListener((e)->{
            FileChooser.chooseExcelFile("Select the master comparison file", (path)->{
                System.out.println(path);
            });
        });
        toolBar.add(open);
        add(toolBar, BorderLayout.PAGE_START);
    }
}
