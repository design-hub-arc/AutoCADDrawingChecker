package autocadDrawingChecker.files;

import java.io.File;
import java.nio.file.Path;
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
                xlFiles.addAll(locateExcelFilesInDir(subFile));
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
}
