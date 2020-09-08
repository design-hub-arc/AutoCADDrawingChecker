package autocadDrawingChecker.grading;

import autocadDrawingChecker.autocadData.AutoCADExport;
import java.util.Objects;

/**
 * The AbstractGradingCriteria class is used to
 * provided an interface for describing how students
 * should be graded on certain aspects of their AutoCAD
 * exports.
 * 
 * @author Matt Crow
 */
public abstract class AbstractGradingCriteria {
    private final String name;
    
    /**
     * 
     * @param criteriaName a short name for this criteria
     */
    public AbstractGradingCriteria(String criteriaName){
        name = criteriaName;
    }
    
    /**
     * Note that AbstractGradingCriteria are considered equal if they share the same name.
     * @param obj
     * @return 
     */
    @Override
    public boolean equals(Object obj){
        return obj != null && obj instanceof AbstractGradingCriteria && ((AbstractGradingCriteria)obj).hashCode() == this.hashCode();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.name);
        return hash;
    }
    
    /**
     * 
     * @return the name of this criteria. 
     */
    public final String getName(){
        return name;
    }
    
    /**
     * This method should compare the given AutoCADExports to each other, and
     * return a double between 0.0 and 1.0. A value of 1.0 denotes the student's
     * file meets this criteria perfectly, while a 0.0 means it fails entirely.
     * @param exp1 The instructor file to compare to.
     * @param exp2 The student file to grade.
     * @return a double from 0.0 to 1.0.
     */
    public abstract double computeScore(AutoCADExport exp1, AutoCADExport exp2);
    
    /**
     * This method should return a helpful
     * description of what this criteria grades
     * on. It should be long enough to provide
     * an understanding of what this does, but
     * should not be so laden in technobabble
     * that is rendered incomprehensible.
     * 
     * @return a helpful description of this
     * grading criteria.
     */
    public abstract String getDescription();
}
