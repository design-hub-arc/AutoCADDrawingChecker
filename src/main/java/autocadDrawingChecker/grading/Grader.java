package autocadDrawingChecker.grading;

import autocadDrawingChecker.data.AbstractGradableDataType;
import autocadDrawingChecker.grading.criteria.AbstractGradingCriteria;
import autocadDrawingChecker.data.core.DataSet;
import autocadDrawingChecker.grading.criteria.CompareColumn;
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
    private final AbstractGradableDataType dataType;
    private final String instrFilePath;
    private final String[] studentFilePaths;
    private final HashSet<AbstractGradingCriteria<? extends DataSet>> criteria;
    
    /**
     * 
     * @param dataType the data type the given files are in
     * @param pathToInstructorFile the complete path to the instructor file to compare to.
     * @param pathsToStudentFiles a series of complete paths to student files, or folders containing them.
     * @param gradeThese the criteria to grade on.
     */
    public Grader(AbstractGradableDataType dataType, String pathToInstructorFile, String[] pathsToStudentFiles, HashSet<AbstractGradingCriteria<? extends DataSet>> gradeThese){
        this.dataType = dataType;
        instrFilePath = pathToInstructorFile;
        studentFilePaths = pathsToStudentFiles;
        criteria = gradeThese;
    }
    
    private DataSet parseFile(String path) throws IOException {
        return dataType.parseFile(path);
    };
    
    private List<DataSet> getStudentFiles(){
        return Arrays.stream(studentFilePaths).flatMap((cmpPath)->{
            // locate all Excel files in all given paths...
            return ExcelFileLocator.locateExcelFilesInDir(cmpPath).stream();
        }).map((fileName)->{
            DataSet r = null;
            try {
                r = parseFile(fileName);
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
        
        DataSet trySrc = null;
        List<DataSet> cmp = null;
        
        try {
            trySrc = parseFile(this.instrFilePath);
        } catch (IOException ex) {
            Logger.logError(String.format("Failed to locate source file %s", instrFilePath));
            Logger.logError(ex);
        }
        
        DataSet src = trySrc; // need this to be effectively final for lambda
        
        cmp = getStudentFiles();
        
        /*
        see which columns exist in the instructor export,
        and add those columns to the list of criteria this
        should grade on. Don't directly add them to this.criteria
        though, as that could cause problems
        */
        Set<String> colsToGrade = (src == null) ? new HashSet<>() : src.getColumns();
        LinkedList<AbstractGradingCriteria<? extends DataSet>> colCriteria = new LinkedList<>();
        for(String column : colsToGrade){
            colCriteria.add(new CompareColumn(column));
        }
        
        List<AbstractGradingCriteria<? extends DataSet>> finalGradedCriteria = new ArrayList<>();
        finalGradedCriteria.addAll(criteria);
        finalGradedCriteria.addAll(colCriteria);
        finalGradedCriteria.forEach((c)->report.addCriteria(c));
        
        cmp.stream().forEach((exp)->{
            report.add(new GradedExport(src, exp, finalGradedCriteria));
        });
        
        return report;
    }
}
