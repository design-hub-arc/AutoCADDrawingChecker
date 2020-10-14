package autocadDrawingChecker.data;

import autocadDrawingChecker.data.autoCADData.AutoCADDataType;
import autocadDrawingChecker.data.core.GenericExcelDataType;
import autocadDrawingChecker.data.surveyData.SurveyDataType;
import autocadDrawingChecker.util.AbstractLoader;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Matt
 */
public class GradeableDataTypeLoader extends AbstractLoader<AbstractGradeableDataType> {

    @Override
    public List<AbstractGradeableDataType> getAll() {
        ArrayList<AbstractGradeableDataType> ret = new ArrayList<>();
        ret.add(new AutoCADDataType());
        ret.add(new GenericExcelDataType());
        ret.add(new SurveyDataType());
        return ret;
    }

}
