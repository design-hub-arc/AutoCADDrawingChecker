package autocadDrawingChecker.start;

import autocadDrawingChecker.grading.criteria.AbstractGradingCriteria;
import java.util.Arrays;
import java.util.HashMap;
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
    private final HashMap<String, Boolean> selectedCriteria;
    private final HashMap<String, AbstractGradingCriteria> nameToCriteria;
    
    public DrawingCheckerData(){
        instructorFilePath = null;
        studentFilePaths = new String[0];
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
    public final boolean isCriteriaSelected(AbstractGradingCriteria criteria){
        //                      has this loaded the criteria?                       is it toggled to true?
        return selectedCriteria.containsKey(criteria.getName()) && selectedCriteria.get(criteria.getName());
    }
    
    public final String getInstructorFilePath(){
        return instructorFilePath;
    }
    public final String[] getStudentFilePaths(){
        return studentFilePaths;
    }
    
    public final void clearCriteria(){
        selectedCriteria.clear();
        nameToCriteria.clear();
    }
    
    public final void addCriteria(AbstractGradingCriteria crit){
        selectedCriteria.put(crit.getName(), Boolean.TRUE);
        nameToCriteria.put(crit.getName(), crit);
    }
    
    public final List<AbstractGradingCriteria> getSelectedCriteria(){
        return selectedCriteria.entrySet().stream().filter((Entry<String, Boolean> nameToIsSelected)->{
            return nameToIsSelected.getValue(); // the "isSelected" part of the entry
        }).map((Entry<String, Boolean> nameToIsSelected)->{
            return nameToCriteria.get(nameToIsSelected.getKey());
        }).filter((AbstractGradingCriteria criteria)->{
            return criteria != null;
        }).collect(Collectors.toList());
    }
    public final HashMap<AbstractGradingCriteria, Boolean> getGradingCriteria(){
        HashMap<AbstractGradingCriteria, Boolean> critToIsSel = new HashMap<>();
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
    public final DrawingCheckerData setCriteriaSelected(AbstractGradingCriteria criteria, boolean isSelected){
        selectedCriteria.put(criteria.getName(), isSelected);
        return this;
    }
}
