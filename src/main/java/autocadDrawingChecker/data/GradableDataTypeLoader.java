package autocadDrawingChecker.data;

import autocadDrawingChecker.data.autoCADData.AutoCADDataType;
import autocadDrawingChecker.data.core.GenericExcelDataType;
import autocadDrawingChecker.data.surveyData.SurveyDataType;
import autocadDrawingChecker.util.AbstractLoader;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to get the various data types defined in this package.
 * When creating new data types, you will want to add them to the list returned
 * by this class' getAll() method.
 * 
 * @author Matt Crow
 */
public class GradableDataTypeLoader extends AbstractLoader<AbstractGradableDataType> {

    @Override
    public List<AbstractGradableDataType> getAll() {
        ArrayList<AbstractGradableDataType> ret = new ArrayList<>();
        ret.add(new AutoCADDataType());
        ret.add(new GenericExcelDataType());
        ret.add(new SurveyDataType());
        return ret;
    }

}
