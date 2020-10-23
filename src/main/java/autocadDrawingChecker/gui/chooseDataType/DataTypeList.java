package autocadDrawingChecker.gui.chooseDataType;

import autocadDrawingChecker.data.AbstractGradableDataType;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.HashMap;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

/**
 *
 * @author Matt
 */
public class DataTypeList extends JComponent {
    private final JPanel content;
    private final ButtonGroup buttons;
    private final GridBagConstraints gbc;
    private final HashMap<AbstractGradableDataType, DataTypeSelector> typeToSel;
    
    public DataTypeList(){
        super();
        buttons = new ButtonGroup();
        typeToSel = new HashMap<>();
        setLayout(new BorderLayout());
        
        // copy-pasted from CriteriaSelectionList
        content = new JPanel();
        content.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 0.0; // this make things "stick" together
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.PAGE_START;
        
        JScrollPane scroll = new JScrollPane(content);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        add(scroll, BorderLayout.CENTER);
    }
    
    final void setDataTypeOptions(List<AbstractGradableDataType> dataTypes){
        typeToSel.values().stream().map((sel)->sel.getButton()).forEach(buttons::remove);
        typeToSel.clear();
        content.removeAll();
        
        dataTypes.stream().map((type)->{
            typeToSel.put(type, new DataTypeSelector(type));
            return typeToSel.get(type);
        }).forEach((sel)->{
            content.add(sel, gbc.clone());
            buttons.add(sel.getButton());
        });
    }
    
    public final void setDataTypeSelected(AbstractGradableDataType type){
        typeToSel.get(type).getButton().setSelected(true);
    }
    
    public final boolean isAnySelected(){
        return typeToSel.values().stream().anyMatch((sel)->{
            return sel.isSelected();
        });
    }
}
