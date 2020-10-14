package autocadDrawingChecker.start;

import autocadDrawingChecker.data.AbstractGradeableDataType;
import autocadDrawingChecker.data.core.ExtractedSpreadsheetContents;
import autocadDrawingChecker.grading.AutoCADGrader;
import autocadDrawingChecker.grading.criteria.AbstractGradingCriteria;
import autocadDrawingChecker.grading.GradingReport;
import autocadDrawingChecker.gui.PageRenderer;
import autocadDrawingChecker.gui.ViewController;
import autocadDrawingChecker.gui.chooseCriteria.ChooseCriteriaPage;
import autocadDrawingChecker.gui.chooseDataType.ChooseDataTypePage;
import autocadDrawingChecker.gui.chooseFiles.ChooseFilesPage;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Matt
 */
public class Application {
    private final DrawingCheckerData data;
    private ViewController window;
    
    private List<AbstractGradingCriteria<? extends ExtractedSpreadsheetContents>> criteria;
    
    private static Application instance;
    public static final String APP_NAME = "AutoCAD Drawing Checker";
    
    private Application(){
        if(instance != null){
            throw new ExceptionInInitializerError("Application is supposed to be a singleton: No more than one instance!");
        }
        data = new DrawingCheckerData();
        criteria = new ArrayList<>();
    }
    
    public static final Application getInstance(){
        if(instance == null){
            instance = new Application();
        }
        return instance;
    }
    
    public final Application setLoadedCriteria(List<AbstractGradingCriteria<? extends ExtractedSpreadsheetContents>> criteria){
        this.criteria = criteria;
        data.clearCriteria();
        for(AbstractGradingCriteria<? extends ExtractedSpreadsheetContents> crit : criteria){
            data.addCriteria(crit);
        }
        return this;
    }
    
    public final List<AbstractGradingCriteria<? extends ExtractedSpreadsheetContents>> getGradedCriteria(){
        return criteria;
    }
    
    public final void addGradeableDataType(AbstractGradeableDataType t){
        data.addGradeableDataType(t);
    }
    
    public final Application createGui(){
        if(window != null){
            window.dispose();
        }
        window = new ViewController();
        PageRenderer pane = window.getAppPane();
        
        ChooseDataTypePage chooseDataType = (ChooseDataTypePage)pane.getPage(PageRenderer.CHOOSE_DATA_TYPE);
        if(data.isDataTypeSelected()){
            chooseDataType.setSelectedDataType(data.getSelectedDataType());
        }
        
        ChooseFilesPage chooseFiles = (ChooseFilesPage)pane.getPage(PageRenderer.CHOOSE_FILES);
        if(data.isInstructorFilePathSet()){
            chooseFiles.setSrcFile(new File(data.getInstructorFilePath()));
        }
        if(data.isStudentFilePathsSet()){
            chooseFiles.setCmpFiles(Arrays.stream(data.getStudentFilePaths()).map((path)->{
                return new File(path);
            }).toArray((size)->new File[size]));
        }
        
        ChooseCriteriaPage chooseCriteria = (ChooseCriteriaPage)pane.getPage(PageRenderer.CHOOSE_CRITERIA);
        data.getGradingCriteria().forEach((criteria, isSel)->{
            chooseCriteria.setCriteriaSelected(criteria, isSel);
        });
        
        return this;
    }
    
    public final DrawingCheckerData getData(){
        return data;
    }    
    
    public final boolean isReadyToGrade(){
        return 
            data.isInstructorFilePathSet() && 
            data.isStudentFilePathsSet() && 
            data.isAnyCriteriaSelected();
    }
    
    public final GradingReport gradeAutoCAD(){
        AutoCADGrader g = new AutoCADGrader(
            data.getInstructorFilePath(),
            data.getStudentFilePaths(),
            data.getSelectedCriteria()
        );
        
        return g.grade();
    }
}
