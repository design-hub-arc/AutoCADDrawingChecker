package autocadDrawingChecker.logging;

import java.util.LinkedList;

/**
 *
 * @author Matt
 */
public class Logger {
    private static final LinkedList<String> LOGGED_MSGS = new LinkedList<>();
    private static boolean ERROR_FLAG = false;
    
    public static final void log(String msg){
        LOGGED_MSGS.add(msg);
        System.out.println(msg);
    }
    
    public static final void log(double d){
        log(Double.toString(d));
    }
    
    public static final void logError(String errMsg){
        LOGGED_MSGS.add(errMsg);
        System.err.println(errMsg);
        ERROR_FLAG = true;
    }
    
    public static final void logError(Exception ex){
        StringBuilder stackTrace = new StringBuilder();
        stackTrace.append(ex.getMessage());
        stackTrace.append("\n").append(ex.toString());
        for(StackTraceElement frame : ex.getStackTrace()){
            stackTrace.append("\n- ").append(frame.toString());
        }
        logError(stackTrace.toString());
    }
    
    public static final boolean hasLoggedError(){
        return ERROR_FLAG;
    }
    
    public static String getLog(){
        return LOGGED_MSGS.toString();
    }
}
