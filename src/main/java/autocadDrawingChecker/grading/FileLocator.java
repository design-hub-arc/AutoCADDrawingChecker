package autocadDrawingChecker.grading;

import autocadDrawingChecker.util.FileType;
import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * The FileLocator is a static helper class used to get all files
 with a given extension within a given directory.
 * 
 * @author Matt Crow
 */
public class FileLocator {
    /**
     * Returns every file with the given extension under the given file or directory.
     * Not sure if I want this to return files instead of Strings, same with it's parameter
     * <ul>
     * <li>If the given path is an Excel file, returns that file</li>
     * <li>If it is a path to a non-excel file, returns an empty list</li>
     * <li>If it is the path to a folder, returns each Excel file in that folder
     * or any folder contained within it recursively.</li>
     * </ul>
     * @param rootPath the path to the file or directory to scour for Excel files
     * @param type the type of files to locate
     * @return a list of the complete path to each Excel file this locates
     */
    public static final ArrayList<String> locateFilesInDir(String rootPath, FileType type){ 
        ArrayList<String> xlFiles = new ArrayList<>();
        File root = Paths.get(rootPath).toFile();
        if(root.isDirectory()){
            for(String subFile : root.list()){
                // root.list() does not give the full path, so need Paths.get() here
                xlFiles.addAll(locateFilesInDir(Paths.get(rootPath, subFile).toString(), type));
            }
        } else {
            if(type.fileIsOfThisType(root)){
                xlFiles.add(rootPath);
            } else {
                //Logger.log("Not an excel file: " + rootPath);
            }
        }
        return xlFiles;
    }
    
    public static void main(String[] args){
        FileLocator.locateFilesInDir("C:\\Users\\Matt\\Desktop\\AutoCAD Drawing Checker", FileType.CSV).forEach(System.out::println);
    }
}
