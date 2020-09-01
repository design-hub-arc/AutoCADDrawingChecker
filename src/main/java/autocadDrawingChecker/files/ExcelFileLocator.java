package autocadDrawingChecker.files;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 *
 * @author Matt
 */
public class ExcelFileLocator {
    public static ArrayList<String> locateExcelFilesInDir(String rootPath){
        ArrayList<String> xlFiles = new ArrayList<>();
        File root = Paths.get(rootPath).toFile();
        if(root.isDirectory()){
            for(String subFile : root.list()){
                // root.list() does not give the full path, so need Paths.get() here
                xlFiles.addAll(locateExcelFilesInDir(Paths.get(rootPath, subFile).toString()));
            }
        } else {
            if(rootPath.endsWith("xlsx") || rootPath.endsWith("xls")){
                xlFiles.add(rootPath);
            } else {
                System.out.println("Not an excel file: " + rootPath);
            }
        }
        return xlFiles;
    }
    
    public static void main(String[] args){
        ExcelFileLocator.locateExcelFilesInDir("C:\\Users\\Matt\\Desktop\\AutoCAD Drawing Checker").forEach(System.out::println);
    }
}
