package autocadDrawingChecker.grading;

import autocadDrawingChecker.data.AbstractGradableDataType;
import autocadDrawingChecker.data.core.AbstractTableParser;
import autocadDrawingChecker.grading.criteria.AbstractGradingCriteria;
import autocadDrawingChecker.data.core.DataSet;
import autocadDrawingChecker.logging.Logger;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
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
    
    private List<DataSet> getStudentFiles(AbstractTableParser parser){
        return Arrays.stream(studentFilePaths).flatMap((cmpPath)->{
            // locate all relevant files in all given paths...
            return FileLocator.locateFilesInDir(cmpPath, dataType.getRequiredFileType()).stream();
        }).flatMap((fileName)->{
            List<DataSet> r = new LinkedList<>();
            try {
                r = parser.parseAllSheets(fileName);
            } catch (IOException ex) {
                Logger.logError(ex);
            }
            return r.stream();
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
        AbstractTableParser parser = dataType.createParser();
        DataSet trySrc = null;
        List<DataSet> cmp = null;
        
        try {
            trySrc = parser.parseFirstSheet(this.instrFilePath);
        } catch (IOException ex) {
            Logger.logError(String.format("Failed to locate source file %s", instrFilePath));
            Logger.logError(ex);
        }
        
        DataSet src = trySrc; // need this to be effectively final for lambda
        
        cmp = getStudentFiles(parser);
        
        criteria.forEach((c)->report.addCriteria(c));
        
        for(DataSet studentData : cmp){
            GradedExport graded = new GradedExport(src, studentData);
            for(AbstractGradingCriteria<? extends DataSet> currCriteria : criteria){
                graded.addGradeFor(currCriteria);
            }
            report.add(graded);
        }
        
        return report;
    }
}
