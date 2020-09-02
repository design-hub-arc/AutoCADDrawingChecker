package autocadDrawingChecker.logging;

/**
 *
 * @author Matt
 */
public interface ErrorListener {
    public void errorLogged(String errMsg);
    public void errorLogged(Exception ex);
}
