package autocadDrawingChecker.start;

import autocadDrawingChecker.grading.Grader;
import autocadDrawingChecker.grading.GradingReport;
import autocadDrawingChecker.gui.PageRenderer;
import autocadDrawingChecker.gui.ViewController;
import autocadDrawingChecker.gui.chooseCriteria.ChooseCriteriaPage;
import autocadDrawingChecker.gui.chooseFiles.ChooseFilesPage;
import java.io.File;
import java.util.Arrays;

/**
 *
 * @author Matt
 */
public class Application {
    private final DrawingCheckerData data;
    private String srcPath;
    private String[] cmpPaths;
    private ViewController window;
    
    private static Application instance;
    
    private Application(){
        if(instance != null){
            throw new ExceptionInInitializerError("Application is supposed to be a singleton: No more than one instance!");
        }
        data = new DrawingCheckerData();
        srcPath = null;
        cmpPaths = new String[0];
        
    }
    
    public static final Application getInstance(){
        if(instance == null){
            instance = new Application();
        }
        return instance;
    }
    
    public final Application createGui(){
        if(window != null){
            window.dispose();
        }
        window = new ViewController();
        PageRenderer pane = window.getAppPane();
        
        ChooseFilesPage chooseFiles = (ChooseFilesPage)pane.getPage(PageRenderer.CHOOSE_FILES);
        if(isSrcPathSet()){
            chooseFiles.setSrcFile(new File(srcPath));
        }
        if(isCmpPathsSet()){
            chooseFiles.setCmpFiles(Arrays.stream(cmpPaths).map((path)->{
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
    
    public final Application setSrcPath(String path){
        srcPath = path;
        return this;
    }
    
    public final Application setCmpPaths(String... paths){
        cmpPaths = paths;
        return this;
    }
    
    public final boolean isSrcPathSet(){
        return srcPath != null;
    }
    
    public final boolean isCmpPathsSet(){
        return cmpPaths != null && cmpPaths.length > 0;
    }
    
    
    
    public final String getSrcPath(){
        return srcPath;
    }
    
    public final String[] getCmpPaths(){
        return cmpPaths;
    }
    
    
    
    public final boolean isReadyToGrade(){
        return isSrcPathSet() && isCmpPathsSet() && data.isAnyCriteriaSelected();
    }
    
    public final GradingReport grade(){
        Grader g = new Grader(
            srcPath,
            cmpPaths,
            data.getSelectedCriteria()
        );
        
        return g.grade();
    }
}
