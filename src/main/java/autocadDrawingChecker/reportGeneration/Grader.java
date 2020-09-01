package autocadDrawingChecker.reportGeneration;

import autocadDrawingChecker.autocadData.AutoCADExcelParser;
import autocadDrawingChecker.autocadData.AutoCADExport;
import autocadDrawingChecker.comparison.AttributeToCompare;
import autocadDrawingChecker.comparison.ExportComparison;
import autocadDrawingChecker.files.ExcelFileLocator;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
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
    private final ArrayList<AttributeToCompare> criteria;
    
    public Grader(String src, String cmp, ArrayList<AttributeToCompare> gradeThese){
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
                ex.printStackTrace();
            }
            return r;
        }).filter((e)->e != null).collect(Collectors.toList());
    }
    
    public final void grade(){
        AutoCADExport trySrc = null;
        List<AutoCADExport> cmp = null;
        
        try {
            trySrc = getSrcFile();
        } catch (IOException ex) {
            ex.printStackTrace();
            System.err.printf("Failed to locate source file %s\n", srcPath);
        }
        
        AutoCADExport src = trySrc; // need this to be effectively final for lambda
        
        cmp = getCmpFiles();
        
        List<ExportComparison> grades = cmp.stream().map((exp)->{
            return new ExportComparison(src, exp, criteria);
        }).collect(Collectors.toList());
        
        grades.forEach((expComp)->{
            System.out.printf("Compare to %s: %f\n", expComp.getCmpFile().getFileName(), expComp.runComparison());
        });
    }
    
    public static void writeReportTo(AutoCADExport src, List<AutoCADExport> cmp, OutputStream out){
        StringBuilder sb = new StringBuilder();
        sb.append("Autograding report:");
        sb.append("\nSource file to compare to is ").append(src.getFileName());
        
        try (BufferedWriter buff = new BufferedWriter(new OutputStreamWriter(out))) {
            buff.write(sb.toString());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
