package autocadDrawingChecker.util;

import java.util.List;

/**
 * I will want to implement this
 * class to explicitly load classes,
 * not implicitly. Essentially, this
 * will handle stuff like "get all
 * GradingCriteria we want" and
 * "get all AutoCAD elements" etc.
 * @author Matt Crow
 * @param <T> the type of object
 * this will load
 */
public abstract class AbstractLoader<T> {
    /**
     * This method should return constructed 
     * objects of all subclasses
     * of T that are relevant to this loader.
     * These objects returned should not have
     * mutable states, as they will be accessible
     * by the whole program.
     * 
     * @return a list of objects of T.
     */
    public abstract T[] getAll();
}
