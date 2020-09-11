package autocadDrawingChecker.grading;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * The GradingReport class is used to bundle a set of graded exports
 * together so they can be outputted together into one file. This
 * class will likely be rewritten in later versions to write the
 * report in a more easily readable format.
 * 
 * @author Matt Crow
 */
public class GradingReport extends LinkedList<ExportComparison> {
    private final HashMap<String, Integer> headerToCol;
    private final ArrayList<String> headers;
    private final HashMap<String, AbstractGradingCriteria> gradedCriteria;
    
    private static final String SRC_FILE_HEADER = "Instructor File";
    private static final String CMP_FILE_HEADER = "Student File";
    private static final String FINAL_GRADE_HEADER = "Final Grade";
    
    public GradingReport(){
        super();
        headerToCol = new HashMap<>();
        headers = new ArrayList<>();
        gradedCriteria = new HashMap<>();
        addHeader(SRC_FILE_HEADER);
        addHeader(CMP_FILE_HEADER);
        addHeader(FINAL_GRADE_HEADER);
    }
    
    private void addHeader(String header){
        headerToCol.put(header, headers.size());
        headers.add(header);
    }
    
    public final void addCriteria(AbstractGradingCriteria criteria){
        gradedCriteria.put(criteria.getName(), criteria);
        addHeader(criteria.getName());
    }
    
    public final Workbook getAsWorkBook(){
        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet reportSheet = workbook.createSheet("Grading Report");
        
        Row headerRow = reportSheet.createRow(0);
        for(int i = 0; i < headers.size(); i++){
            headerRow.createCell(i, CellType.STRING).setCellValue(headers.get(i));
        }
        
        Row newRow = null;
        Cell newCell = null;
        ExportComparison currExp = null;
        String currHeader = null;
        Object data = null;
        for(int row = 0; row < size(); row++){
            currExp = get(row);
            newRow = reportSheet.createRow(row + 1);
            for(int col = 0; col < headers.size(); col++){
                newCell = newRow.createCell(col);
                currHeader = headers.get(col);
                switch(currHeader){
                    case SRC_FILE_HEADER:
                        data = currExp.getInstructorFile().getFileName();
                        newCell.setCellValue((String)data);
                        break;
                    case CMP_FILE_HEADER:
                        data = currExp.getCmpFile().getFileName();
                        newCell.setCellValue((String)data);
                        break;
                    case FINAL_GRADE_HEADER:
                        data = currExp.getFinalGrade();
                        newCell.setCellValue((double)data);
                        break;
                    default:
                        data = currExp.getGradeFor(gradedCriteria.get(currHeader));
                        newCell.setCellValue((double)data);
                        break;
                }
            }
        }
        
        return workbook;
    }
    
    /**
     * Use this method to get the text
     * to write as a .txt file when the user
     * saves it.
     * 
     * @return the contents of all the ExportComparisons
     * contained herein. 
     */
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Grading report:");
        forEach((expComp)->{
            sb.append("\n").append(expComp.toString());
        });
        return sb.toString();
    }
}
