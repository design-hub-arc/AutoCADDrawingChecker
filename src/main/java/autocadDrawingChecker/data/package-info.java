/**
 * The data package provides ways of converting Excel files into objects which the program can interact with.
 * In this way, this package serves as the Model component of the standard Model-View-Controller archetype.
 * 
 * Each sub-folder contained herein is a specific Gradable Data Type.
 * A Gradable Data Type (or simply "data type") represents a type of Excel export that should be treated differently
 * from other data types. For example, the AutoCAD data type is a subclass of the Generic Excel data type: the program
 * can treat AutoCAD data as though it were just Generic Excel data, but AutoCAD data has special properties, such as
 * "layer" and "name" columns, and should therefore be treated differently than Generic Excel data.
 * 
 */
package autocadDrawingChecker.data;
