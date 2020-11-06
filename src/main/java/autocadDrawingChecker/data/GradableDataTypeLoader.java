package autocadDrawingChecker.data;

import autocadDrawingChecker.data.csv.GenericCsvDataType;
import autocadDrawingChecker.data.excel.autoCADData.AutoCADDataType;
import autocadDrawingChecker.data.excel.GenericExcelDataType;
import autocadDrawingChecker.data.excel.surveyData.SurveyDataType;
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
        ret.add(new GenericExcelDataType());
        ret.add(new GenericCsvDataType(true));
        ret.add(new GenericCsvDataType(false));
        ret.add(new AutoCADDataType());
        ret.add(new SurveyDataType());
        return ret;
    }

}
