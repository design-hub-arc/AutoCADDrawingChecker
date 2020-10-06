package autocadDrawingChecker.grading;

import autocadDrawingChecker.grading.criteria.AbstractGradingCriteria;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * The GradingReport class is used to bundle a set of graded exports
 * together so they can be outputted together into one file. This
 * class will likely be rewritten in later versions to write the
 * report in a more easily readable format.
 * 
 * @author Matt Crow
 */
public class GradingReport extends LinkedList<GradedExport> {
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
        XSSFSheet reportSheet = workbook.createSheet("Grading Report");
        
        XSSFRow headerRow = reportSheet.createRow(0);
        for(int i = 0; i < headers.size(); i++){
            headerRow.createCell(i, CellType.STRING).setCellValue(headers.get(i));
        }
        
        XSSFRow newRow = null;
        XSSFCell newCell = null;
        GradedExport currExp = null;
        String currHeader = null;
        Object data = null;
        
        XSSFDataFormat format = workbook.createDataFormat();
        CellStyle style = workbook.createCellStyle();
        style.setDataFormat(format.getFormat("###%")); // format as 0-3 integer digits
        
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
                        newCell.setCellStyle(null);
                        break;
                    case CMP_FILE_HEADER:
                        data = currExp.getStudentFile().getFileName();
                        newCell.setCellValue((String)data);
                        break;
                    case FINAL_GRADE_HEADER:
                        CellAddress oneToTheRight = new CellAddress(newCell.getRowIndex(), newCell.getColumnIndex() + 1);
                        CellAddress endOfRow = new CellAddress(newCell.getRowIndex(), headers.size() - 1);
                        newCell.setCellFormula(String.format("AVERAGE(%s:%s)", oneToTheRight.formatAsString(), endOfRow.formatAsString()));
                        newCell.setCellStyle(style);
                        break;
                    default:
                        data = currExp.getGradeFor(gradedCriteria.get(currHeader));
                        newCell.setCellValue((double)data);
                        newCell.setCellStyle(style);
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
