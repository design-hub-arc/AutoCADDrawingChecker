package autocadDrawingChecker.gui.chooseDataType;

import autocadDrawingChecker.gui.AbstractPage;
import autocadDrawingChecker.start.Application;
import java.awt.GridLayout;
import javax.swing.JOptionPane;

/**
 *
 * @author Matt
 */
public class ChooseDataTypePage extends AbstractPage {
    private final DataTypeList typeList;
    public ChooseDataTypePage() {
        super("Choose the type of data to grade");
        setLayout(new GridLayout(1, 1));
        typeList = new DataTypeList(Application.getInstance().getData().getGradeableDataTypes());
        add(typeList);
    }

    @Override
    protected boolean checkIfReadyForNextPage() {
        boolean ready = typeList.isAnySelected();
        if(!ready){
            JOptionPane.showMessageDialog(this, "Please choose a data type to grade");
        }
        return ready;
    }

}
