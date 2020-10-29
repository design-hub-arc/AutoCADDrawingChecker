package autocadDrawingChecker.grading.criteria.surveyCriteria;

import autocadDrawingChecker.data.core.DataSet;
import autocadDrawingChecker.data.core.Record;
import autocadDrawingChecker.data.excel.surveyData.SurveyDataRecord;
import autocadDrawingChecker.data.excel.surveyData.SurveyDataSet;
import autocadDrawingChecker.grading.criteria.AbstractVectorCriteria;

/**
 *
 * @author Matt
 */
public class GradeCoords implements AbstractVectorCriteria<SurveyDataSet, SurveyDataRecord>{
    @Override
    public double[] extractVector(SurveyDataRecord e) {
        return new double[]{
            e.getX(),
            e.getY(),
            e.getZ()
        };
    }

    @Override
    public SurveyDataRecord tryCastRecord(Record rec) {
        return (rec instanceof SurveyDataRecord) ? (SurveyDataRecord)rec : null;
    }

    @Override
    public String getName() {
        return "Grade Survey Data Coordinates";
    }

    @Override
    public String getDescription() {
        return "Grades Survey Data based on how closely its coordinates match that of the instructor file";
    }

    @Override
    public SurveyDataSet tryCastDataSet(DataSet contents) {
        return (contents instanceof SurveyDataSet) ? (SurveyDataSet)contents : null;
    }

}
