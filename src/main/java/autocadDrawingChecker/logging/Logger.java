package autocadDrawingChecker.logging;

import java.util.LinkedList;

/**
 *
 * @author Matt
 */
public class Logger {
    private static final StringBuilder LOGGED_MSGS = new StringBuilder();
    private static final LinkedList<MessageListener> MSG_LISTENS = new LinkedList<>();
    private static final LinkedList<ErrorListener> ERR_LISTENS = new LinkedList<>();
    private static boolean ERROR_FLAG = false;
    
    public static final void addMessageListener(MessageListener listener){
        MSG_LISTENS.add(listener);
    }
    public static final void addErrorListener(ErrorListener listener){
        ERR_LISTENS.add(listener);
    }
    
    public static final void log(String msg){
        LOGGED_MSGS.append(msg);
        System.out.println(msg);
        MSG_LISTENS.forEach((ml)->ml.messageLogged(msg));
    }
    
    public static final void log(Object obj){
        log(obj.toString());
    }
    
    public static final void log(double d){
        log(Double.toString(d));
    }
    
    // moving this code out of logError prevents listeners from being alerted twice when logging stack traces
    private static void saveErrMsg(String errMsg){
        LOGGED_MSGS.append(errMsg);
        ERROR_FLAG = true;
        System.err.println(errMsg);
    }
    
    public static final void logError(String errMsg){
        saveErrMsg(errMsg);
        ERR_LISTENS.forEach((el)->el.errorLogged(errMsg));
    }
    
    public static final void logError(Exception ex){
        StringBuilder stackTrace = new StringBuilder();
        stackTrace.append(ex.getMessage());
        stackTrace.append("\n").append(ex.toString());
        for(StackTraceElement frame : ex.getStackTrace()){
            stackTrace.append("\n- ").append(frame.toString());
        }
        saveErrMsg(stackTrace.toString());
        ERR_LISTENS.forEach((el)->el.errorLogged(ex));
    }
    
    public static final boolean hasLoggedError(){
        return ERROR_FLAG;
    }
    
    public static String getLog(){
        return LOGGED_MSGS.toString();
    }
}
