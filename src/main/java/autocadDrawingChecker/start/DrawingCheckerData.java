package autocadDrawingChecker.start;

import autocadDrawingChecker.data.AbstractGradeableDataType;
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
import java.util.Map.Entry;
import java.util.stream.Collectors;

/**
 *
 * @author Matt
 */
public class DrawingCheckerData {
    private AbstractGradeableDataType selectedDataType;
    private String instructorFilePath;
    private DataSet instructorFile;
    private String[] studentFilePaths;
    private final List<AbstractGradeableDataType> gradeableDataTypes; 
    private final HashMap<String, Boolean> selectedCriteria;
    private final HashMap<String, AbstractGradingCriteria<? extends DataSet>> nameToCriteria;
    
    public DrawingCheckerData(){
        instructorFilePath = null;
        instructorFile = null;
        studentFilePaths = new String[0];
        selectedDataType = null;
        gradeableDataTypes = new LinkedList<>();
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
    
    public final void addGradeableDataType(AbstractGradeableDataType type){
        gradeableDataTypes.add(type);
    }
    
    public final List<AbstractGradeableDataType> getGradeableDataTypes(){
        return gradeableDataTypes;
    }
    
    public final void setSelectedDataType(AbstractGradeableDataType type){
        selectedDataType = type;
    }
    
    public final boolean isDataTypeSelected(){
        return selectedDataType != null;
    }
    
    public final AbstractGradeableDataType getSelectedDataType(){
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
    
    
    /**
     * 
     * @return the list of criteria this has which can grade the current data type 
     */
    private List<AbstractGradingCriteria<? extends DataSet>> getGradeableCriteria(){
        LinkedList<AbstractGradingCriteria<? extends DataSet>> crit = new LinkedList<>();
        if(isDataTypeSelected() && isInstructorFilePathSet()){
            this.nameToCriteria.values().forEach((availableCrit)->{
                // maybe just add a check for AbstractGradingCriteria.canGradeDataType(...)
                if(availableCrit.tryCastDataSet(this.instructorFile) != null){
                    crit.add(availableCrit);
                }
            });
        }
        return crit;
    }
    
    public final HashMap<AbstractGradingCriteria<? extends DataSet>, Boolean> getGradeableCriteriaToIsSelected(){
        HashMap<AbstractGradingCriteria<? extends DataSet>, Boolean> critToIsSel = new HashMap<>();
        // filters based on if the current set is gradeable by the criteria
        getGradeableCriteria().forEach((crit)->{
            critToIsSel.put(crit, this.isCriteriaSelected(crit));
        });
        return critToIsSel;
    }
    
    /**
     * This method determines which criteria the user has chosen to grade on, and the
     * instructor file provided is gradeable on.
     * 
     * @return the criteria this can and should grade based on
     */
    private HashSet<AbstractGradingCriteria<? extends DataSet>> getCriteriaToGradeOn(){
        HashSet<AbstractGradingCriteria<? extends DataSet>> criteriaToGradeOn = new HashSet<>();
        getGradeableCriteriaToIsSelected().entrySet().stream().filter((entry)->{
            return entry.getValue();
        }).map((entry)->entry.getKey()).forEach(criteriaToGradeOn::add);
        return criteriaToGradeOn;
    }
    
    
    
    
    public final DrawingCheckerData setInstructorFilePath(String path){
        instructorFilePath = path;
        if(this.isDataTypeSelected()){
            try {
                instructorFile = selectedDataType.parseFile(path);
            } catch(IOException ex){
                Logger.logError(ex);
                instructorFile = null;
                instructorFilePath = null;
            }
        }
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
