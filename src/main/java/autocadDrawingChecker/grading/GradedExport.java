package autocadDrawingChecker.grading;

import autocadDrawingChecker.grading.criteria.AbstractGradingCriteria;
import autocadDrawingChecker.data.core.ExtractedSpreadsheetContents;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The GradedExport class
 handles grading a single student
 file to the instructor file.
 * <b>Note that this comparison may 
 * not be commutative:</b> 
 * <pre>GradedExport(x, y) != GradedExport(y, x)</pre>
 * 
 * @author Matt Crow.
 * @param <T> The type of export being graded
 */
public class GradedExport<T extends ExtractedSpreadsheetContents> {
    private final T instructorExport;
    private final T studentExport;
    private final Set<AbstractGradingCriteria<T>> gradedCriteria;
    private final HashMap<AbstractGradingCriteria<T>, Double> grades;
    private final double finalGrade;
    
    /**
     * Note that this constructor is is package-private,
     * so it can only be instantiated from within this package.
     * This is done by the Grader class.
     * 
     * @param instructorsExport the instructor's export
     * @param studentsExport the student's export
     * @param gradeOnThese the criteria to grade on. 
     */
    GradedExport(T instructorsExport, T studentsExport, List<AbstractGradingCriteria<T>> gradeOnThese){
        instructorExport = instructorsExport;
        studentExport = studentsExport;
        gradedCriteria = new HashSet<>(gradeOnThese);
        grades = new HashMap<>();
        finalGrade = runComparison();
    }
    
    /**
     * Computes the grade for the student
     * file. It grades the export on each
     * provided criteria, and gives the final
     * grade as the average of the grades for
     * each criteria.
     * 
     * @return the student's final grade. 
     */
    private double runComparison(){
        double similarityScore = 0.0;
        double newScore = 0.0;
        for(AbstractGradingCriteria<T> attr : gradedCriteria){
            newScore = attr.computeScore(instructorExport, studentExport);
            grades.put(attr, newScore);
            similarityScore += newScore;
        }
        
        return similarityScore / (gradedCriteria.size()); // average similarity score
    }
    
    /**
     * 
     * @return the instructor file this grades based on. 
     */
    public final T getInstructorFile(){
        return instructorExport;
    }
    
    /**
     * 
     * @return the student file this grades.
     */
    public final T getStudentFile(){
        return studentExport;
    }
    
    /**
     * Note that the GradingReport uses
     * a formula to dynamically calculate
     * this in the spreadsheet it generates,
     * so this method isn't used.
     * 
     * @return the final grade for this export. 
     */
    public final double getFinalGrade(){
        return finalGrade;
    }
    
    /**
     * 
     * @param criteria the criteria to get the grade for
     * @return the grade the student got for the given criteria,
     * or null if this didn't grade on the given criteria.
     */
    public final double getGradeFor(AbstractGradingCriteria<T> criteria){
        return grades.get(criteria);
    }
    
    /**
     * 
     * @return a string representation of this
     * graded export. If you wish to save this
     * information to a text file, you may, but
     * you can also save to an Excel file using
     * the GradingReport class, which may be more
     * helpful.
     * @see GradingReport
     */
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Comparing %s to \n%s:", instructorExport.getFileName(), studentExport.getFileName()));
        grades.forEach((attr, score)->{
            sb.append(String.format("\n* %s: %d%%", attr.getName(), (int)(score * 100)));
        });
        sb.append(String.format("\nFinal Grade: %d%%", (int)(finalGrade * 100)));
        return sb.toString();
    }
}
