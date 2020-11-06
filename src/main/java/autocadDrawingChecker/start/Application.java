package autocadDrawingChecker.start;

import autocadDrawingChecker.gui.ViewController;

/**
 *
 * @author Matt
 */
public class Application {
    private final DrawingCheckerData data;
    private ViewController window;
    
    private static Application instance;
    public static final String APP_NAME = "AutoCAD Drawing Checker";
    
    private Application(){
        if(instance != null){
            throw new ExceptionInInitializerError("Application is supposed to be a singleton: No more than one instance!");
        }
        data = new DrawingCheckerData();
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
        
        return this;
    }
    
    public final DrawingCheckerData getData(){
        return data;
    }
}
