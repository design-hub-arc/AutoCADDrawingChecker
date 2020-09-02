package autocadDrawingChecker.reportGeneration;

import autocadDrawingChecker.autocadData.AutoCADExcelParser;
import autocadDrawingChecker.autocadData.AutoCADExport;
import autocadDrawingChecker.comparison.AbstractGradingCriteria;
import autocadDrawingChecker.comparison.ExportComparison;
import autocadDrawingChecker.logging.Logger;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Matt
 */
public class Grader {
    private final String srcPath;
    private final String cmpPath;
    private final ArrayList<AbstractGradingCriteria> criteria;
    
    public Grader(String src, String cmp, ArrayList<AbstractGradingCriteria> gradeThese){
        srcPath = src;
        cmpPath = cmp;
        criteria = gradeThese;
    }
    
    private AutoCADExport getSrcFile() throws IOException{
        return AutoCADExcelParser.parse(srcPath);
    }
    private List<AutoCADExport> getCmpFiles(){
        return ExcelFileLocator.locateExcelFilesInDir(cmpPath).stream().map((fileName) -> {
            AutoCADExport r = null;
            try {
                r = AutoCADExcelParser.parse(fileName);
            } catch (IOException ex) {
                Logger.logError(ex);
            }
            return r;
        }).filter((e)->e != null).collect(Collectors.toList());
    }
    
    public final GradingReport grade(){
        GradingReport report = new GradingReport();
        
        AutoCADExport trySrc = null;
        List<AutoCADExport> cmp = null;
        
        try {
            trySrc = getSrcFile();
        } catch (IOException ex) {
            Logger.logError(String.format("Failed to locate source file %s", srcPath));
            Logger.logError(ex);
        }
        
        AutoCADExport src = trySrc; // need this to be effectively final for lambda
        
        cmp = getCmpFiles();
        
        cmp.stream().forEach((exp)->{
            report.add(new ExportComparison(src, exp, criteria));
        });
        
        return report;
    }
}
