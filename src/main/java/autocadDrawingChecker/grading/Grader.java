package autocadDrawingChecker.grading;

import autocadDrawingChecker.files.ExcelFileLocator;
import autocadDrawingChecker.autocadData.AutoCADExcelParser;
import autocadDrawingChecker.autocadData.AutoCADExport;
import autocadDrawingChecker.logging.Logger;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The Grader class performs all the grading of students' assignments.
 * It takes one instructor file, and one or more student files, as well
 * as one or more criteria to grade on. It computes each student's average
 * score from all the given criteria, and returns a comprehensive report
 * of everyone's grade.
 * 
 * @author Matt Crow
 */
public class Grader {
    private final String srcPath;
    private final String[] cmpPaths;
    private final List<AbstractGradingCriteria> criteria;
    
    /**
     * 
     * @param src the complete path to the instructor file to compare to.
     * @param cmp a series of complete paths to student files, or folders containing them.
     * @param gradeThese the criteria to grade on.
     */
    public Grader(String src, String[] cmp, List<AbstractGradingCriteria> gradeThese){
        srcPath = src;
        cmpPaths = cmp;
        criteria = gradeThese;
    }
    
    private AutoCADExport getSrcFile() throws IOException{
        return AutoCADExcelParser.parse(srcPath);
    }
    
    private List<AutoCADExport> getCmpFiles(){
        return Arrays.stream(cmpPaths).flatMap((cmpPath)->{
            // locate all Excel files in all given paths...
            return ExcelFileLocator.locateExcelFilesInDir(cmpPath).stream();
        }).map((fileName)->{
            // try to convert them to AutoCADExports...
            AutoCADExport r = null;
            try {
                r = AutoCADExcelParser.parse(fileName);
            } catch (IOException ex) {
                Logger.logError(ex);
            }
            return r;
        }).filter((e)->{
            // ignore any conversions that fail...
            return e != null;
        }).collect(Collectors.toList()); // and return those successful conversions as a list
    }
    
    /**
     * Attempts to locate all the files
     * this is supposed to grade, grades
     * them according to the given criteria,
     * and returns a report of how well everyone
     * did.
     * 
     * @return a GradingReport containing
     * each student's similarity score for
     * each of the given grading criteria.
     */
    public final GradingReport grade(){
        GradingReport report = new GradingReport();
        
        AutoCADExport trySrc = null;
        List<AutoCADExport> cmp = null;
        
        try {
            trySrc = getSrcFile();
        } catch (IOException ex) {
            Logger.logError(String.format("Failed to locate source file %s", srcPath));
            Logger.logError(ex);
        }
        
        AutoCADExport src = trySrc; // need this to be effectively final for lambda
        
        cmp = getCmpFiles();
        
        cmp.stream().forEach((exp)->{
            report.add(new ExportComparison(src, exp, criteria));
        });
        
        return report;
    }
}
