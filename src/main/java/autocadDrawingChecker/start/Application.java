package autocadDrawingChecker.start;

import autocadDrawingChecker.grading.AbstractGradingCriteria;
import autocadDrawingChecker.grading.Grader;
import autocadDrawingChecker.grading.GradingCriteriaLoader;
import autocadDrawingChecker.grading.GradingReport;
import autocadDrawingChecker.gui.PageRenderer;
import autocadDrawingChecker.gui.ViewController;
import autocadDrawingChecker.gui.chooseFiles.ChooseFilesPage;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Matt
 */
public class Application {
    private String srcPath;
    private String[] cmpPaths;
    private final HashMap<AbstractGradingCriteria, Boolean> criteriaIsSelected;
    private ViewController window;
    
    private static Application instance;
    
    private Application(){
        if(instance != null){
            throw new ExceptionInInitializerError("Application is supposed to be a singleton: No more than one instance!");
        }
        srcPath = null;
        cmpPaths = new String[0];
        criteriaIsSelected = new HashMap<>();
        GradingCriteriaLoader.getAllCriteria().forEach((c) -> {
            criteriaIsSelected.put(c, Boolean.TRUE);
        });
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
        // do stuff with window
        return this;
    }
    
    public final Application setSrcPath(String path){
        srcPath = path;
        return this;
    }
    
    public final Application setCmpPaths(String... paths){
        cmpPaths = paths;
        return this;
    }
    
    public final Application setCriteria(AbstractGradingCriteria crit, boolean isSelected){
        criteriaIsSelected.put(crit, isSelected);
        return this;
    }
    
    public final boolean isSrcPathSet(){
        return srcPath != null;
    }
    
    public final boolean isCmpPathsSet(){
        return cmpPaths != null && cmpPaths.length > 0;
    }
    
    public final boolean isCriteriaSelected(AbstractGradingCriteria crit){
        return criteriaIsSelected.containsKey(crit) && criteriaIsSelected.get(crit);
    }
    
    public final boolean isAnyCriteriaSet(){
        return criteriaIsSelected.values().contains(Boolean.TRUE); // at least one criteria selected
    }
    
    public final String getSrcPath(){
        return srcPath;
    }
    
    public final String[] getCmpPaths(){
        return cmpPaths;
    }
    
    public final List<AbstractGradingCriteria> getCriteria(){
        return criteriaIsSelected.entrySet().stream().filter((e)->e.getValue()).map((e)->e.getKey()).collect(Collectors.toList());
    }
    
    public final boolean isReadyToGrade(){
        return isSrcPathSet() && isCmpPathsSet() && isAnyCriteriaSet();
    }
    
    public final GradingReport grade(){
        Grader g = new Grader(
            srcPath,
            cmpPaths,
            getCriteria()
        );
        
        return g.grade();
    }
}
