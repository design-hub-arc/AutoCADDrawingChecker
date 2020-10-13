package autocadDrawingChecker.grading;

import autocadDrawingChecker.data.autoCADData.AutoCADExcelParser;
import autocadDrawingChecker.data.autoCADData.AutoCADExport;
import autocadDrawingChecker.data.core.ExtractedSpreadsheetContents;
import autocadDrawingChecker.grading.criteria.AbstractGradingCriteria;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author Matt
 */
public class AutoCADGrader extends Grader<AutoCADExport> {

    public AutoCADGrader(String pathToInstructorFile, String[] pathsToStudentFiles, List<AbstractGradingCriteria<? extends ExtractedSpreadsheetContents>> gradeThese) {
        super(pathToInstructorFile, pathsToStudentFiles, gradeThese);
    }

    @Override
    protected AutoCADExport parseFile(String path) throws IOException {
        return AutoCADExcelParser.parse(path);
    }

}
