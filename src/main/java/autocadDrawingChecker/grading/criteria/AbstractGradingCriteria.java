package autocadDrawingChecker.grading.criteria;

import autocadDrawingChecker.data.core.DataSet;

/**
 * The AbstractGradingCriteria interface is used to
 * provided an interface for describing how students
 * should be graded on certain aspects of their AutoCAD
 * exports.
 * 
 * @author Matt Crow
 * @param <DataSetType> the type of data export this can grade
 */
public interface AbstractGradingCriteria<DataSetType extends DataSet> {
    /**
     * This method should compare the given DataSet to each other, and
 return a double between 0.0 and 1.0. A value of 1.0 denotes the student's
     * file meets this criteria perfectly, while a 0.0 means it fails entirely.
     * @param exp1 The instructor file to compare to.
     * @param exp2 The student file to grade.
     * @return a double from 0.0 to 1.0.
     */
    public abstract double computeScore(DataSetType exp1, DataSetType exp2);
    
    /**
     * 
     * @return the name of this criteria. 
     */
    public abstract String getName();
    
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
    
    public abstract DataSetType tryCastDataSet(DataSet contents);
}
