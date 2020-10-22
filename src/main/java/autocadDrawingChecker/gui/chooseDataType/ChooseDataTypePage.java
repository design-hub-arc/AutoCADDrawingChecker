package autocadDrawingChecker.gui.chooseDataType;

import autocadDrawingChecker.data.AbstractGradeableDataType;
import autocadDrawingChecker.gui.AbstractPage;
import autocadDrawingChecker.start.DrawingCheckerData;
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
        typeList = new DataTypeList();
        add(typeList);
    }
    
    public final void setSelectedDataType(AbstractGradeableDataType type){
        typeList.setDataTypeSelected(type);
    }

    @Override
    protected boolean checkIfReadyForNextPage() {
        boolean ready = typeList.isAnySelected();
        if(!ready){
            JOptionPane.showMessageDialog(this, "Please choose a data type to grade");
        }
        return ready;
    }

    @Override
    protected void dataUpdated(DrawingCheckerData newData) {
        typeList.setDataTypeOptions(newData.getGradeableDataTypes());
        if(newData.isDataTypeSelected()){
            typeList.setDataTypeSelected(newData.getSelectedDataType());
        }
    }

}
