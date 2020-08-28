package autocadDrawingChecker.files;

/**
 * @author Matt
 */
public enum FileType {
    EXCEL("Excel Workbook", new String[]{"xlsx"});
    
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
}
