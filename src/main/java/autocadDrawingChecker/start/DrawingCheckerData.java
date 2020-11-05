package autocadDrawingChecker.start;

import autocadDrawingChecker.data.AbstractGradableDataType;
import autocadDrawingChecker.data.core.DataSet;
import autocadDrawingChecker.grading.Grader;
import autocadDrawingChecker.grading.GradingReport;
import autocadDrawingChecker.grading.criteria.AbstractGradingCriteria;
import autocadDrawingChecker.grading.criteria.CompareColumn;
import autocadDrawingChecker.logging.Logger;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
/**
 * DrawingCheckerData stores all the parameters used by the program 
 * when grading. These parameters include:
 * <ul>
 * <li>The subclasses of AbstractGradableDataType the program can grade</li>
 * <li>The specific AbstractGradableDataType the user selected from the above list</li>
 * <li>The complete path to the instructor file the user selected</li>
 * <li>The complete file paths to the student files the user selected</li>
 * <li>The AbstractGradingCriteria the program can grade on</li>
 * <li>The AbstractGradingCriteria the user has chosen from the above list</li>
 * <li>How accurate student values must be to instructor values to be considered a match</li>
 * </ul>
 * An instance of this class can be obtained from the singleton Application class, so you should
 * work with those values if you want to change the program data.
 * 
 * @author Matt Crow
 */
public class DrawingCheckerData {
    private final List<AbstractGradableDataType> gradableDataTypes;
    private AbstractGradableDataType selectedDataType;
    
    private String instructorFilePath;
    private String[] studentFilePaths;
    
    private final HashSet<AbstractGradingCriteria<? extends DataSet>> availableCriteria;
    private final HashMap<AbstractGradingCriteria<? extends DataSet>, Boolean> selectedCriteria;
    
    /**
     * The maximum difference between
     * student and instructor values
     * that is considered correct.
     */
    private double criteriaThreshold;
    
    DrawingCheckerData(){
        gradableDataTypes = new LinkedList<>();
        selectedDataType = null;
        instructorFilePath = null;
        studentFilePaths = new String[0];
        availableCriteria = new HashSet<>();
        selectedCriteria = new HashMap<>();
        criteriaThreshold = 0.0;
    }
    
    
    
    /*
    GradableDataType methods
    */
    
    /**
     * 
     * @param type the type to add to the list of data types this can grade 
     */
    final void addGradableDataType(AbstractGradableDataType type){
        gradableDataTypes.add(type);
    }
    
    /**
     * 
     * @param type the data type this should read user files as 
     */
    public final void setSelectedDataType(AbstractGradableDataType type){
        selectedDataType = type;
    }
    
    /**
     * 
     * @return whether or not the user has chosen
     * a data type to grade.
     */
    public final boolean isDataTypeSelected(){
        return selectedDataType != null;
    }
    
    /**
     * 
     * @return the list of all data types this can grade 
     */
    public final List<AbstractGradableDataType> getGradableDataTypes(){
        return gradableDataTypes;
    }
    
    /**
     * 
     * @return the data type the user has selected to grade
     */
    public final AbstractGradableDataType getSelectedDataType(){
        return selectedDataType;
    }
    
    
    
    /*
    Instructor file methods
    */
    
    /**
     * 
     * @param path the complete file path to the instructor file to grade on.
     * @return this, for chaining purposes
     */
    public final DrawingCheckerData setInstructorFilePath(String path){
        instructorFilePath = path;
        return this;
    }
    
    /**
     * 
     * @return whether or not the user has selected
     * an instructor file.
     */
    public final boolean isInstructorFilePathSet(){
        return instructorFilePath != null;
    }
    
    /**
     * 
     * @return the complete file path to the instructor
     * file this will grade based on.
     */
    public final String getInstructorFilePath(){
        return instructorFilePath;
    }
    
    /**
     * 
     * @return the contents of the selected
     * instructor file
     */
    private DataSet parseInstructorFile(){
        DataSet ret = null;
        if(isInstructorFilePathSet() && isDataTypeSelected()){
            try {
                ret = this.selectedDataType.createParser().parseFirstSheet(instructorFilePath);
            } catch (IOException ex) {
                Logger.logError(ex);
            }
        } else {
            Logger.logError("Instructor file path or data type is not selected, so I can't parse the instructor file");
        }
        return ret; 
    }
    
    
    
    
    /*
    Student file methods
    */
    
    /**
     * 
     * @param paths a series of complete file paths to either student files
     * or folders containing them.
     * 
     * @return this, for chaining purposes 
     */
    public final DrawingCheckerData setStudentFilePaths(String... paths){
        studentFilePaths = paths;
        return this;
    }
    
    /**
     * 
     * @return whether or not at least one student file or folder is selected. 
     */
    public final boolean isStudentFilePathsSet(){
        return studentFilePaths != null && studentFilePaths.length > 0;
    }
    
    /**
     * 
     * @return an array of complete file paths to
     * files this should grade.
     */
    public final String[] getStudentFilePaths(){
        return studentFilePaths;
    }
    
    
    
    /*
    Grading criteria methods
    */ 
    
    /**
     * Adds a new criteria to the list of criteria this can grade on.
     * 
     * @param crit the criteria to add 
     */
    final void addCriteria(AbstractGradingCriteria<? extends DataSet> crit){
        selectedCriteria.put(crit, Boolean.TRUE);
        availableCriteria.add(crit);
    }
    
    /**
     * Sets whether or not to grade based on the given criteria.
     * 
     * @param criteria the criteria to set
     * @param isSelected whether or not the given criteria should be used when grading student files
     * @return this, for chaining purposes
     */
    public final DrawingCheckerData setCriteriaSelected(AbstractGradingCriteria<? extends DataSet> criteria, boolean isSelected){
        selectedCriteria.put(criteria, isSelected);
        return this;
    }
    
    /**
     * 
     * @return whether or not at least one grading criteria is selected 
     */
    public final boolean isAnyCriteriaSelected(){
        return selectedCriteria.values().contains(Boolean.TRUE);
    }
    
    /**
     * 
     * @param criteria the criteria to check if it is selected
     * @return whether or not the given criteria is selected
     */
    public final boolean isCriteriaSelected(AbstractGradingCriteria<? extends DataSet> criteria){
        //                      has this loaded the criteria?                 is it toggled to true?
        return selectedCriteria.containsKey(criteria) && selectedCriteria.get(criteria);
    }
    
    /**
     * If their is not instructor file selected, or the data type is not set,
     * returns all criteria this can grade on.
     * @return the list of criteria this has which can grade the current data type 
     */
    private List<AbstractGradingCriteria<? extends DataSet>> getGradableCriteria(){
        LinkedList<AbstractGradingCriteria<? extends DataSet>> crit = new LinkedList<>();
        DataSet instructorFile = parseInstructorFile();
        this.availableCriteria.forEach((availableCrit)->{
            // maybe just add a check for AbstractGradingCriteria.canGradeDataType(...)
            if(instructorFile == null || availableCrit.tryCastDataSet(instructorFile) != null){
                crit.add(availableCrit);
            }
        });
        if(instructorFile != null){
            instructorFile.getColumns().forEach((String colName)->{
                CompareColumn compCol = new CompareColumn(colName);
                crit.add(compCol);
                this.setCriteriaSelected(compCol, true);
            });
        }
        return crit;
    }
    
    /**
     * 
     * @return a mapping of criteria to whether or not they are selected and gradable for the currently selected 
     * instructor file
     */
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
    
    
    
    /*
    Criteria threshold methods
    */
    
    /**
     * Sets the maximum difference between student and instructor
     * files which is considered a match.
     * 
     * @param newValue the new maximum difference. Must be non-negative.
     */
    public final void setCriteriaThreshold(double newValue){
        if(newValue < 0){
            throw new IllegalArgumentException("criteria threshold must be non-negative");
        }
        this.criteriaThreshold = newValue;
    }
    
    /**
     * 
     * @return the maximum difference between
     * numerical values in a student export and
     * instructor export which the program should
     * consider a match.
     */
    public final double getCriteriaThreshold(){
        return this.criteriaThreshold;
    }
    
    
    
    /**
     * 
     * @return whether or not this is ready
     * to grade the students' files.
     */
    public final boolean isReadyToGrade(){
        return 
            isDataTypeSelected() &&
            isInstructorFilePathSet() && 
            isStudentFilePathsSet();
    }
    
    /**
     * Grades the student files the user has selected,
     * comparing them to the instructor file the user selected,
     * grading each criteria the user has selected,
     * and returning a comprehensive grading report.
     * 
     * @return a report of all the students' grades. 
     */
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
