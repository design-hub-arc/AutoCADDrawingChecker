package autocadDrawingChecker.data.autoCADData;

import autocadDrawingChecker.data.AbstractGradeableDataType;
import autocadDrawingChecker.data.core.DataSet;
import autocadDrawingChecker.data.core.RecordExtractor;
import java.io.IOException;
import java.util.HashMap;

/**
 *
 * @author Matt
 */
public class AutoCADDataType implements AbstractGradeableDataType {

    @Override
    public RecordExtractor createExtractor(HashMap<String, Integer> columns) {
        return new AutoCADElementExtractor(columns);
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

    @Override
    public DataSet parseFile(String fileName) throws IOException {
        return new AutoCADExcelParser(fileName).parse();
    }
}
