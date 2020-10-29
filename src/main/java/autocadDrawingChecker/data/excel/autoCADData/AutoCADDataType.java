package autocadDrawingChecker.data.excel.autoCADData;

import autocadDrawingChecker.data.AbstractGradableDataType;
import autocadDrawingChecker.data.excel.ExcelParser;

/**
 *
 * @author Matt
 */
public class AutoCADDataType implements AbstractGradableDataType {
    @Override
    public String getName() {
        return "AutoCAD";
    }

    @Override
    public String getDescription() {
        return "Data extracted from an AutoCAD file into Excel";
    }

    @Override
    public ExcelParser createParser(String fileName) {
        return new AutoCADExcelParser(fileName);
    }
}
