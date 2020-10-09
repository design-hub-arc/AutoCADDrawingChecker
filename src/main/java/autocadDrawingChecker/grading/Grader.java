package autocadDrawingChecker.grading;

import autocadDrawingChecker.grading.criteria.AbstractGradingCriteria;
import autocadDrawingChecker.data.AutoCADExcelParser;
import autocadDrawingChecker.data.AutoCADExport;
import autocadDrawingChecker.grading.criteria.implementations.CompareColumn;
import autocadDrawingChecker.logging.Logger;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
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
    private final String instrFilePath;
    private final String[] studentFilePaths;
    private final List<AbstractGradingCriteria> criteria;
    
    /**
     * 
     * @param pathToInstructorFile the complete path to the instructor file to compare to.
     * @param pathsToStudentFiles a series of complete paths to student files, or folders containing them.
     * @param gradeThese the criteria to grade on.
     */
    public Grader(String pathToInstructorFile, String[] pathsToStudentFiles, List<AbstractGradingCriteria> gradeThese){
        instrFilePath = pathToInstructorFile;
        studentFilePaths = pathsToStudentFiles;
        criteria = gradeThese;
    }
    
    private AutoCADExport getInstructorFile() throws IOException{
        return AutoCADExcelParser.parse(instrFilePath);
    }
    
    private List<AutoCADExport> getStudentFiles(){
        return Arrays.stream(studentFilePaths).flatMap((cmpPath)->{
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
            trySrc = getInstructorFile();
        } catch (IOException ex) {
            Logger.logError(String.format("Failed to locate source file %s", instrFilePath));
            Logger.logError(ex);
        }
        
        AutoCADExport src = trySrc; // need this to be effectively final for lambda
        
        cmp = getStudentFiles();
        
        /*
        see which columns exist in the instructor export,
        and add those columns to the list of criteria this
        should grade on. Don't directly add them to this.criteria
        though, as that could cause problems
        */
        Set<String> colsToGrade = (src == null) ? new HashSet<>() : src.getColumns();
        LinkedList<AbstractGradingCriteria> colCriteria = new LinkedList<>();
        for(String column : colsToGrade){
            colCriteria.add(new CompareColumn(column));
        }
        
        List<AbstractGradingCriteria> finalGradedCriteria = new ArrayList<>();
        finalGradedCriteria.addAll(criteria);
        finalGradedCriteria.addAll(colCriteria);
        finalGradedCriteria.forEach((c)->report.addCriteria(c));
        
        cmp.stream().forEach((exp)->{
            report.add(new GradedExport(src, exp, finalGradedCriteria));
        });
        
        return report;
    }
}
