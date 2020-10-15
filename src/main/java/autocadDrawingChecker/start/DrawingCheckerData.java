package autocadDrawingChecker.start;

import autocadDrawingChecker.data.AbstractGradeableDataType;
import autocadDrawingChecker.data.core.DataSet;
import autocadDrawingChecker.grading.criteria.AbstractGradingCriteria;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

/**
 *
 * @author Matt
 */
public class DrawingCheckerData {
    private String instructorFilePath;
    private String[] studentFilePaths;
    private AbstractGradeableDataType selectedDataType;
    private final List<AbstractGradeableDataType> gradeableDataTypes; 
    private final HashMap<String, Boolean> selectedCriteria;
    private final HashMap<String, AbstractGradingCriteria<? extends DataSet>> nameToCriteria;
    
    public DrawingCheckerData(){
        instructorFilePath = null;
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
    
    public final List<AbstractGradingCriteria<? extends DataSet>> getSelectedCriteria(){
        return selectedCriteria.entrySet().stream().filter((Entry<String, Boolean> nameToIsSelected)->{
            return nameToIsSelected.getValue(); // the "isSelected" part of the entry
        }).map((Entry<String, Boolean> nameToIsSelected)->{
            return nameToCriteria.get(nameToIsSelected.getKey());
        }).filter((AbstractGradingCriteria<? extends DataSet> criteria)->{
            return criteria != null;
        }).collect(Collectors.toList());
    }
    public final HashMap<AbstractGradingCriteria<? extends DataSet>, Boolean> getGradingCriteria(){
        HashMap<AbstractGradingCriteria<? extends DataSet>, Boolean> critToIsSel = new HashMap<>();
        selectedCriteria.entrySet().forEach((entry)->{
            critToIsSel.put(nameToCriteria.get(entry.getKey()), entry.getValue());
        });
        return critToIsSel;
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
}
