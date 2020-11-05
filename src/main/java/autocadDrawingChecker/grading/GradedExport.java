package autocadDrawingChecker.grading;

import autocadDrawingChecker.grading.criteria.AbstractGradingCriteria;
import autocadDrawingChecker.data.core.DataSet;
import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The GradedExport class
 handles grading a single student
 file to the instructor file.
 * <b>Note that this comparison may 
 * not be commutative:</b> 
 * <pre>GradedExport(x, y) != GradedExport(y, x)</pre>
 * 
 * @author Matt Crow.
 */
public class GradedExport {
    private final DataSet instructorExport;
    private final DataSet studentExport;
    private final HashMap<AbstractGradingCriteria, Double> grades;
    
    /**
     * Note that this constructor is is package-private,
     * so it can only be instantiated from within this package.
     * This is done by the Grader class.
     * 
     * @param instructorsExport the instructor's export
     * @param studentsExport the student's export
     */
    GradedExport(DataSet instructorsExport, DataSet studentsExport){
        instructorExport = instructorsExport;
        studentExport = studentsExport;
        grades = new HashMap<>();
    }
    
    /**
     * Grades the student file on the given criteria, and adds it to the list of criteria
     * this has graded.
     * 
     * @param criteria the criteria to grade on. 
     */
    public final void addGradeFor(AbstractGradingCriteria criteria){
        grades.put(criteria, criteria.grade(instructorExport, studentExport));
    }
    
    /**
     * 
     * @return the instructor file this grades based on. 
     */
    public final DataSet getInstructorFile(){
        return instructorExport;
    }
    
    /**
     * 
     * @return the student file this grades.
     */
    public final DataSet getStudentFile(){
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
        return grades.values().stream().collect(Collectors.averagingDouble((d)->d));
    }
    
    /**
     * 
     * @param criteria the criteria to get the grade for
     * @return the grade the student got for the given criteria,
     * or null if this didn't grade on the given criteria.
     */
    public final double getGradeFor(AbstractGradingCriteria criteria){
        return (grades.containsKey(criteria)) ? grades.get(criteria) : 0.0;
    }
    
    /**
     * 
     * @return the criteria this has graded on 
     */
    public final Set<AbstractGradingCriteria> getGradedCriteria(){
        return grades.keySet();
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
        sb.append(String.format("\nFinal Grade: %d%%", (int)(getFinalGrade() * 100)));
        return sb.toString();
    }
}
