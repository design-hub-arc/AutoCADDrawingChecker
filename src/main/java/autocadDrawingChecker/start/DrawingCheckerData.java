package autocadDrawingChecker.start;

import autocadDrawingChecker.data.AbstractGradableDataType;
import autocadDrawingChecker.data.core.DataSet;
import autocadDrawingChecker.grading.Grader;
import autocadDrawingChecker.grading.GradingReport;
import autocadDrawingChecker.grading.criteria.AbstractGradingCriteria;
import autocadDrawingChecker.logging.Logger;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
/**
 *
 * @author Matt
 */
public class DrawingCheckerData {
    private final List<AbstractGradableDataType> gradableDataTypes; 
    private final HashMap<String, AbstractGradingCriteria<? extends DataSet>> nameToCriteria;
    
    private AbstractGradableDataType selectedDataType;
    
    private String instructorFilePath;
    private String[] studentFilePaths;
    
    private final HashMap<String, Boolean> selectedCriteria;
    
    public DrawingCheckerData(){
        instructorFilePath = null;
        studentFilePaths = new String[0];
        selectedDataType = null;
        gradableDataTypes = new LinkedList<>();
        selectedCriteria = new HashMap<>();
        nameToCriteria = new HashMap<>();
    }
    
    public final boolean isInstructorFilePathSet(){
        return instructorFilePath != null;
    }
    public final boolean isStudentFilePathsSet(){
        return studentFilePaths != null && studentFilePaths.length > 0;
    }
    public final boolean isAnyCriteriaSelected(){
        return selectedCriteria.values().contains(Boolean.TRUE);
    }
    public final boolean isCriteriaSelected(AbstractGradingCriteria<? extends DataSet> criteria){
        //                      has this loaded the criteria?                       is it toggled to true?
        return selectedCriteria.containsKey(criteria.getName()) && selectedCriteria.get(criteria.getName());
    }
    
    public final String getInstructorFilePath(){
        return instructorFilePath;
    }
    public final String[] getStudentFilePaths(){
        return studentFilePaths;
    }
    
    public final void addGradableDataType(AbstractGradableDataType type){
        gradableDataTypes.add(type);
    }
    
    public final List<AbstractGradableDataType> getGradableDataTypes(){
        return gradableDataTypes;
    }
    
    public final void setSelectedDataType(AbstractGradableDataType type){
        selectedDataType = type;
    }
    
    public final boolean isDataTypeSelected(){
        return selectedDataType != null;
    }
    
    public final AbstractGradableDataType getSelectedDataType(){
        return selectedDataType;
    }
    
    public final void clearCriteria(){
        selectedCriteria.clear();
        nameToCriteria.clear();
    }
    
    public final void addCriteria(AbstractGradingCriteria<? extends DataSet> crit){
        selectedCriteria.put(crit.getName(), Boolean.TRUE);
        nameToCriteria.put(crit.getName(), crit);
    }
    
    private DataSet parseInstructorFile(){
        DataSet ret = null;
        if(isInstructorFilePathSet() && isDataTypeSelected()){
            try {
                ret = this.selectedDataType.parseFile(instructorFilePath);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return ret; 
    }
    
    
    /**
     * If their is not instructor file selected, or the data type is not set,
     * returns all criteria this can grade on.
     * @return the list of criteria this has which can grade the current data type 
     */
    private List<AbstractGradingCriteria<? extends DataSet>> getGradableCriteria(){
        LinkedList<AbstractGradingCriteria<? extends DataSet>> crit = new LinkedList<>();
        DataSet instructorFile = parseInstructorFile();
        this.nameToCriteria.values().forEach((availableCrit)->{
            // maybe just add a check for AbstractGradingCriteria.canGradeDataType(...)
            if(instructorFile == null || availableCrit.tryCastDataSet(instructorFile) != null){
                crit.add(availableCrit);
            }
        });
        return crit;
    }
    
    public final HashMap<AbstractGradingCriteria<? extends DataSet>, Boolean> getGradableCriteriaToIsSelected(){
        HashMap<AbstractGradingCriteria<? extends DataSet>, Boolean> critToIsSel = new HashMap<>();
        // filters based on if the current set is gradable by the criteria
        getGradableCriteria().forEach((crit)->{
            critToIsSel.put(crit, this.isCriteriaSelected(crit));
        });
        return critToIsSel;
    }
    
    /**
     * This method determines which criteria the user has chosen to grade on, and the
     * instructor file provided is gradable on.
     * 
     * @return the criteria this can and should grade based on
     */
    private HashSet<AbstractGradingCriteria<? extends DataSet>> getCriteriaToGradeOn(){
        HashSet<AbstractGradingCriteria<? extends DataSet>> criteriaToGradeOn = new HashSet<>();
        getGradableCriteriaToIsSelected().entrySet().stream().filter((entry)->{
            return entry.getValue();
        }).map((entry)->entry.getKey()).forEach(criteriaToGradeOn::add);
        return criteriaToGradeOn;
    }
    
    
    
    
    public final DrawingCheckerData setInstructorFilePath(String path){
        instructorFilePath = path;
        return this;
    }
    public final DrawingCheckerData setStudentFilePaths(String... paths){
        studentFilePaths = paths;
        return this;
    }
    public final DrawingCheckerData setCriteriaSelected(AbstractGradingCriteria<? extends DataSet> criteria, boolean isSelected){
        selectedCriteria.put(criteria.getName(), isSelected);
        return this;
    }
    
    public final boolean isReadyToGrade(){
        return 
            isDataTypeSelected() &&
            isInstructorFilePathSet() && 
            isStudentFilePathsSet();// && 
            //isAnyCriteriaSelected();
    }
    
    public final GradingReport grade(){
        Grader g = new Grader(
            getSelectedDataType(),
            getInstructorFilePath(),
            getStudentFilePaths(),
            getCriteriaToGradeOn()
        );
        
        return g.grade();
    }
}
