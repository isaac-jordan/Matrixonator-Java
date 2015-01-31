::Matrixonator JAR Creator Batch
:: ----------------------------
:: Converts the Matrixonator.class files into .jar
:: Written by: BigE

@ECHO OFF

::*[EDITABLE SECION] *::

::VARIABLES
::=========
::DIRECTORY LOCATIONS
::========
::PACKAGE EXTENSION

::SOURCE - Home directory of source (without package extension)
set SOURCE="C:\Users\[USERNAME]\Documents\GitHub\Matrixonator-Java\Matrixonator-Java-E\bin\"

::LIBS - Location of any library/.jar files we may require to be packed within
set LIBS="C:\Users\[USERNAME]\Documents\GitHub\Matrixonator-Java\Matrixonator-Java\lib\"

::Allows jar to compress [root]\ijordan\matrixonator\ and not full path from Windows
set EXTENSION=ijordan\matrixonator\

::JAVA CLASS LOCATION
::========

::MAIN - Main startup class for JAR Manifest
set MAIN=%EXTENSION%MainApp.class

::FOLDERS/PACKAGES
::---------
::MODEL - Includes all CLASS files from the model package
set MODEL=%EXTENSION%model\*.class

::VIEW - Includes all CLASS files from view package
set VIEW=%EXTENSION%view\*.class

::EXTRAS
::--------
::FXBUILD - Adds .fx files required for runtime
set FXBUILD=%EXTENSION%view\*.fxml

::=============================::
::**[END OF EDITABLE SECTION]**::


::ROOT OF SCRIPT
set MYPATH="%~dp0"

::MANIFEST NAME
set MANIFEST=mxbuild.txt


::RUN!
::--------

::copy manifest template to source directory
xcopy %MANIFEST% %SOURCE%

::Change to where libs are stored and copy to batch path
cd %LIBS%
xcopy /s/y *.jar %MYPATH%\lib\

::Change to source directory
cd %SOURCE%

::Calling jar creator with required data 
call jar cfm Matrixonator.jar %MANIFEST% %MAIN% %MODEL% %VIEW% %FXBUILD%

::Move created jar back to where we called the script from
move /Y Matrixonator.jar %MYPATH%Matrixonator.jar

::Remove temporary manifest template
del %MANIFEST%

::Return console back to where it was, helps when called from CMD
cd %MYPATH%

::TURN ECHO BACK ON
@ECHO ON