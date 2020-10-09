package autocadDrawingChecker.util;

import java.io.File;

/**
 * @author Matt
 */
public enum FileType {
    EXCEL("Excel Workbook", new String[]{"xlsx", "xls"}),
    EXCEL_OR_FOLDER("Excel Workbook or a folder", new String[]{"xlsx", "xls"});
    
    private final String name;
    private final String[] extensions;
    
    private FileType(String n, String[] ext){
        name = n;
        extensions = ext;
    }
    
    public final String getName(){
        return name;
    }
    
    public final String[] getExtensions(){
        return extensions;
    }
    
    public final boolean fileIsOfThisType(File f){
        String path = f.getAbsolutePath();
        String[] split = path.split("\\.");
        boolean isType = false;
        if(split.length != 0){
            String ext = split[split.length - 1];
            for(int i = 0; i < this.extensions.length && !isType; i++){
                isType = ext.equalsIgnoreCase(this.extensions[i]);
            }
        }
        return isType;
    }
}
