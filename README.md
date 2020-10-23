# AutoCAD Drawing Checker
A simple, extendable Java program used to compare Excel exports from AutoCAD.

## Project Summary
Instructors and teachers in college classes are expected to teach their students by giving lectures and providing learning resources.
One of the most common elements of college classes is the use of homework to practice the concepts taught in class.
Of course, homework can be tedious and time consuming for the instructor to grade, and is an inefficient process when a single objective solution exists for any given problem.
Fortunately, computers are very good at doing tedious processes with objective solutions.

This program is meant to compare data exports from AutoCAD to each other, grading them based upon how similar they both are.
These AutoCAD exports are in either XLS or XLSX format, which is easily parse-able by the Apache POI Library.

This application is currently being extended to support a wider variety of Excel files, including:
* any Excel file with headers in the first row
* GPS data

## Required Software
* Users need only ensure they have [Java](https://java.com/en/) installed on their computer to run the application.
* Developers wishing to change this application for their own use will need [Gradle](https://gradle.org/) installed to build the application.

## How to Use the Application
The application is stored in the JAR file under ```/build/libs/AutoCADDrawingChecker.jar```.
This file is a self-contained application, so you can move it wherever you want on your computer:
it doesn't rely on the other project files to run. Simply double-click the JAR file to run it.

### Steps
* Step 1: choose the file format. This tells the program the format of the data you will provide, and determines which criteria it can grade.
* Step 2: Choosing instructor and student files. You will choose at least 2 files: 
the instructor file, and 1 or more student files. The instructor file is what the student files will be graded on: 
The more similar their file is to the instructor file, the higher their grade will be. When selecting student files, you have several options:
    * Choose 1 or more Excel files
    * Choose 1 or more folders. Note that the program locates all Excel files under this folder, so it's OK if it has other files in there, the program will just ignore them.
    * A combination of the above.
Also of interest: you can drag and drop files into the two selectors instead of clicking the button.
* Step 3: Choosing Grading Criteria. You can choose what the program grades students on by toggling these check boxes on or off. Regardless of what you select, the program will still grade every column in the instructor file.
* Step 4: Running the Autograder. The program will automatically run the grader when you get to the last page.
Once the program is done grading, it will ask you where you want to save the grading report. 
Simply choose a folder, and the program will automatically name the file for you.
If you forget to save the file, just click "Run" to rerun the autograder.

### Troubleshooting
If anything goes wrong, and you are unsure what to do about it, you'll want to click Log -> Save Log in the program menu bar along the top.
You can contact Matt if you need help, and you provide him with the Log file you just created: This will help him figure out what went wrong.

## Matt's To Do List
* see AutoCADElementMatcher
* Should we match rows for each comparison, or for the export as a whole? (wait on this)
* Given double X and double Y, don't do "X == Y", instead do "Math.abs(X - Y) < threshold"
* Look into Federal Section 508
    * https://www.epa.gov/accessibility/what-section-508
    * https://www.section508.gov/
    * https://www.section508.gov/create
    * https://www.access-board.gov/guidelines-and-standards/communications-and-it/about-the-ict-refresh/final-rule/text-of-the-standards-and-guidelines
* maybe just add a check for AbstractGradingCriteria.canGradeDataType(...)

## Helpful Links
* [Apache CLI](https://commons.apache.org/proper/commons-cli/javadocs/api-release/index.html)
* [Excel Library](https://poi.apache.org/apidocs/4.1/)