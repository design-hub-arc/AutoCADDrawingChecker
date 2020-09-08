# AutoCADDrawingChecker
A simple, extendable Java program used to compare vector exports from AutoCAD.

## Project Summary

## How to Use the Application
The application is stored in the JAR file under ```/build/libs/AutoCADDrawingChecker.jar```.
This file is a self-contained application, so you can move it wherever you want on your computer:
it doesn't rely on the other project files to run. Simply double-click the JAR file to run it.
### Steps
* Step 1: Choosing instructor and student files. The first page of the application has you choose at least 2 files: 
the instructor file, and 1 or more student files. The instructor file is what the student files will be graded on: 
The more similar their file is to the instructor file, the higher their grade will be. When selecting student files, you have several options:
    * Choose 1 or more Excel files
    * Choose 1 or more folders. Note that the program locates all Excel files under this folder, so it's OK if it has other files in there, the program will just ignore them.
    * A combination of the above.
* Step 2: Choosing Grading Criteria. You can choose what the program grades students on by toggling these check boxes on or off.
* Step 3: Running the Autograder. Once the program is done grading, it will ask you where you want to save the grading report. 
Make sure you give it a ```.txt``` extension when you save it. 

## To Do
* Do we have a set of icons we can use? Like warning and "you're good" icons?
* Command line mode for easier testing (see start.Main)
* Can I use the ARC stylesheet in the application?
* Change AppPane to GUIController type of thing
* Separate front end from back end
* Add Line Sorter to match most similar lines in two exports
* Finish line length
* finish DrawingCheckerProperties

## Helpful Links
* [Excel Library](https://poi.apache.org/apidocs/4.1/)
