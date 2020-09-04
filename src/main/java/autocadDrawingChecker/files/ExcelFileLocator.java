package autocadDrawingChecker.files;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * The ExcelFileLocator is a static helper class used to get all Excel files
 * within a given directory.
 * 
 * @author Matt Crow
 */
public class ExcelFileLocator {
    /**
     * Returns every Excel file under the given file or directory.
     * Not sure if I want this to return files instead of Strings, same with it's parameter
     * <ul>
     * <li>If the given path is an Excel file, returns that file</li>
     * <li>If it is a path to a non-excel file, returns an empty list</li>
     * <li>If it is the path to a folder, returns each Excel file in that folder
     * or any folder contained within it recursively.</li>
     * </ul>
     * @param rootPath the path to the file or directory to scour for Excel files
     * @return a list of the complete path to each Excel file this locates
     */
    public static final ArrayList<String> locateExcelFilesInDir(String rootPath){
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
                //Logger.log("Not an excel file: " + rootPath);
            }
        }
        return xlFiles;
    }
    
    public static void main(String[] args){
        ExcelFileLocator.locateExcelFilesInDir("C:\\Users\\Matt\\Desktop\\AutoCAD Drawing Checker").forEach(System.out::println);
    }
}
