package autocadDrawingChecker.autocadData.extractors;

import autocadDrawingChecker.autocadData.AutoCADDimension;
import java.util.HashMap;
import org.apache.poi.ss.usermodel.Row;

/**
 *
 * @author Matt
 */
public class DimensionExtractor extends AbstractAutoCADElementExtractor<AutoCADDimension> {

    public DimensionExtractor() {
        super("Rotated Dimension", "Dim Style", "DynamicDimension", "TextDefinedSize");
    }

    @Override
    public AutoCADDimension extract(HashMap<String, Integer> columns, Row currentRow) {
        return new AutoCADDimension(
            currentRow.getCell(columns.get("Dim Style")).toString(),
            (int)currentRow.getCell(columns.get("DynamicDimension")).getNumericCellValue(),
            currentRow.getCell(columns.get("TextDefinedSize")).toString()
        );
    }

}
