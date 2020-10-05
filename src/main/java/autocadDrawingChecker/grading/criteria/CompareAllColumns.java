package autocadDrawingChecker.grading.criteria;

import autocadDrawingChecker.data.AutoCADElement;
import autocadDrawingChecker.data.AutoCADExport;
import autocadDrawingChecker.grading.AutoCADElementMatcher;
import autocadDrawingChecker.grading.MatchingAutoCADElements;
import autocadDrawingChecker.logging.Logger;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Matt
 */
public class CompareAllColumns implements AbstractGradingCriteria {

    private double getElementScore(AutoCADElement e1, AutoCADElement e2){
        return 0.0;
    }
    
    @Override
    public double computeScore(AutoCADExport exp1, AutoCADExport exp2) {
        /*
        Step 1: Split elements based on their name. Elements with the same name tend to have the same attributes, so
            this will likely save time when checking. I will likely have to change this in the future.
        */
        HashMap<Object, LinkedList<AutoCADElement>> exp1Elements = exp1.sortRecordsByColumn(AutoCADElement.NAME_COL);
        HashMap<Object, LinkedList<AutoCADElement>> exp2Elements = exp2.sortRecordsByColumn(AutoCADElement.NAME_COL);
        
        /*
        Step 2: match everything. May change this to just attempt to maximize
            the student's score by comparing column-wise instead of element-wise
        */
        LinkedList<MatchingAutoCADElements> matches = new LinkedList<>();
        exp1Elements.keySet().forEach((Object autoCADName)->{
            matches.addAll(new AutoCADElementMatcher(
                exp1Elements.get(autoCADName), 
                exp2Elements.getOrDefault(autoCADName, new LinkedList<>()),
                (e)->e.getName().equals(autoCADName),
                this::getElementScore
            ).findMatches());
        });
        
        Logger.logError("CompareAllColumns not implemented yet");
        return 0.0;
    }

    @Override
    public String getName() {
        return "Compare All Columns";
    }

    @Override
    public String getDescription() {
        return "Grades all columns in the student files";
    }
}
