package autocadDrawingChecker.grading.criteria;

import autocadDrawingChecker.data.core.DataSet;
import autocadDrawingChecker.data.core.Record;

/**
 *
 * @author Matt
 */
public class CompareColumn implements AbstractElementCriteria<DataSet, Record> {
    private final String column;
    
    /**
     * 
     * @param column the column to compare
     */
    public CompareColumn(String column){
        this.column = column;
    }

    @Override
    public double getMatchScore(Record e1, Record e2) {
        double ret = 0.0;
        if(e1.hasAttribute(column) && e2.hasAttribute(column)){
            ret = (e1.getAttribute(column).equals(e2.getAttribute(column))) ? 1.0 : 0.0;
        }
        return ret;
    }

    @Override
    public String getName() {
        //                    Easier to read when it simply starts with the column name
        return String.format("%s column", column);
    }

    @Override
    public String getDescription() {
        return String.format("Grades the student file based on how closely its %s column matches with that of the instructor file", column);
    }

    @Override
    public Record tryCastRecord(Record rec) {
        return (rec != null) ? rec : null;
    }

    @Override
    public DataSet tryCastDataSet(DataSet contents) {
        return (contents != null) ? contents : null;
    }
}
