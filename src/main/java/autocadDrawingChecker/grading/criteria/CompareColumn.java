package autocadDrawingChecker.grading.criteria;

import autocadDrawingChecker.data.core.DataSet;
import autocadDrawingChecker.data.core.Record;
import autocadDrawingChecker.grading.MathUtil;
import java.util.Objects;

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
    public boolean equals(Object obj){
        return obj != null && obj instanceof CompareColumn && ((CompareColumn)obj).column.equals(this.column);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.column);
        return hash;
    }

    @Override
    public double getMatchScore(Record e1, Record e2) {
        double ret = 0.0;
        if(e1.hasAttribute(column) && e2.hasAttribute(column)){
            ret = MathUtil.gradeSimilarity(e1.getAttribute(column), e2.getAttribute(column));
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
