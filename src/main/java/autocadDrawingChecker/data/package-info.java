/**
 * The autocadData package contains classes
 * used to convert AutoCAD Excel exports into
 * a more useful format for the program.
 * 
 * This process involves several parts.
 * 
 * First, we have the sub-package autocadDrawingChecker.autocadData.elements.
 * This contains classes which represent the geometric interpretations of records in an AutoCAD export as
 * <ul>
 * <li>lines</li>
 * <li>polylines</li>
 * <li>text</li>
 * <li>and dimensions</li>
 * </ul>
 * These act as the data model for the application, and are made available to the whole program.
 * 
 * Second, we have the sub-package autocadDrawingChecker.autocadData.extractors.
 * These are used by the AutoCADExcelParser to essentially say 
 * "this record represents this AutoCAD Element, so I'll use this extractor to convert the record to an element"
 * 
 * There are several other utility classes in this package:
 * <ul>
 * <li>AutoCADAttribute: lists all the columns expected in an AutoCAD export Excel file</li>
 * <li>AutoCADExcelParser: takes a file path, and returns that file as an AutoCADExport</li>
 * <li>AutoCADExport: a collection of AutoCADElements. This is the class is very important</li>
 * </ul>
 * 
 * @see autocadDrawingChecker.autocadData.elements.AutoCADElement
 * @see autocadDrawingChecker.autocadData.extractors.AbstractAutoCADElementExtractor
 * @see autocadDrawingChecker.autocadData.extractors.ExtractorLoader
 */
package autocadDrawingChecker.data;