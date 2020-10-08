package autocadDrawingChecker.gui.chooseFiles;

import java.awt.BorderLayout;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * @author Matt
 * @param <T> the type returned by AbstractExcelFileChooser::getSelected()
 */
public abstract class AbstractExcelFileChooser<T> extends JComponent implements ActionListener, DropTargetListener {
    private T selected;
    private final JTextArea text;
    private final String popupTitle;
    
    public AbstractExcelFileChooser(String title, String popupText){
        super();
        
        setLayout(new BorderLayout());
        
        JPanel top = new JPanel();
        top.add(new JLabel(title));
        add(top, BorderLayout.PAGE_START);
        
        text = new JTextArea("No file selected yet");
        text.setEditable(false);
        text.setLineWrap(true);
        text.setWrapStyleWord(true);
        add(text, BorderLayout.CENTER);
        
        JPanel bottom = new JPanel();
        JButton button = new JButton("Select a file");
        button.addActionListener(this);
        bottom.add(button);
        add(bottom, BorderLayout.PAGE_END);
        popupTitle = popupText;
        
        new DropTarget(this, this);
        
        selected = null;
    }
    
    protected final String getPopupTitle(){
        return popupTitle;
    }
    
    protected final void setText(String newText){
        text.setText(newText);
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        selectButtonPressed();
    }
    
    protected void setSelected(T sel){
        selected = sel;
    }
    
    public final T getSelected(){
        return selected;
    }
    
    public abstract boolean isFileSelected();
    protected abstract void selectButtonPressed();
    protected abstract void filesDropped(List<File> files);

    @Override
    public void dragEnter(DropTargetDragEvent dtde) {}

    @Override
    public void dragOver(DropTargetDragEvent dtde) {}

    @Override
    public void dropActionChanged(DropTargetDragEvent dtde) {}

    @Override
    public void dragExit(DropTargetEvent dte) {}

    @Override
    public void drop(DropTargetDropEvent dtde) {
        dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
        Transferable data = dtde.getTransferable();
        DataFlavor[] flavors = data.getTransferDataFlavors();
        for(DataFlavor flavor : flavors){
            if(flavor.isFlavorJavaFileListType()){
                try {
                    List<File> files = (List<File>)data.getTransferData(flavor);
                    filesDropped(files);
                } catch (UnsupportedFlavorException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
