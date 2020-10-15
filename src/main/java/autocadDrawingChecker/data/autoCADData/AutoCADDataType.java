package autocadDrawingChecker.data.autoCADData;

import autocadDrawingChecker.data.AbstractGradeableDataType;
import autocadDrawingChecker.data.core.DataSet;
import autocadDrawingChecker.data.core.RecordExtractor;

/**
 *
 * @author Matt
 */
public class AutoCADDataType implements AbstractGradeableDataType {

    @Override
    public RecordExtractor createExtractor() {
        return new AutoCADElementExtractor();
    }

    @Override
    public DataSet createExtractionHolder(String fileName) {
        return new AutoCADExport(fileName);
    }

    @Override
    public String getName() {
        return "AutoCAD";
    }

    @Override
    public String getDescription() {
        return "Data extracted from an AutoCAD file into Excel";
    }
}
