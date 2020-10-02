/**
 * The autocadData package contains classes
 * used to convert AutoCAD Excel exports into
 * a more useful format for the program.
 * 
 * <table>
 * <tr><th>Class</th>                  <th>Purpose</th>                                                                    </tr>
 * <tr><td>AutoCADElement</td>         <td>Stores the data from one row in the AutoCAD Excel file.</td>                    </tr>
 * <tr><td>AutoCADElementExtractor</td><td>Package-private. Used to convert rows in the Excel file to AutoCADElements.</td></tr>
 * <tr><td>AutoCADExcelParser</td>     <td>Reads an Excel file, and extracts an AutoCADExport from it.</td>                </tr>
 * <tr><td>AutoCADExport</td>          <td>Stores a list of all AutoCADElements stored in a file.</td>                     </tr>
 * </table>
 */
package autocadDrawingChecker.data;