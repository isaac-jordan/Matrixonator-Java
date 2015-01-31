#!/bin/bash
# This script is a custom build script for Matrixonator-Java. Based on mkjar.bat from Matrixonator/make/win/
set +v


#*[EDITABLE SECION] *

#VARIABLES
#=========
#DIRECTORY LOCATIONS
#========
#PACKAGE EXTENSION

#SOURCE - Home directory of source (without package extension)
SOURCE="~/GitHub/Matrixonator-Java/Matrixonator-Java-E/bin/"

#LIBS - Location of any library/.jar files we may require to be packed within
LIBS="~/GitHub/Matrixonator-Java/Matrixonator-Java/lib/"

#Allows jar to compress [root]\ijordan\matrixonator\ and not full path from Windows
EXTENSION=ijordan/matrixonator/

#JAVA CLASS LOCATION
#========

#MAIN - Main startup class for JAR Manifest
MAIN=${EXTENSION}MainApp.class

#FOLDERS/PACKAGES
#---------
#MODEL - Includes all CLASS files from the model package
MODEL=${EXTENSION}model/*.class

#VIEW - Includes all CLASS files from view package
VIEW=${EXTENSION}view/*.class

#EXTRAS
#--------
#FXBUILD - Adds .fx files required for runtime
FXBUILD=${EXTENSION}view/*.fxml

#=============================
#**[END OF EDITABLE SECTION]**

#ROOT OF SCRIPT
MYPATH=${PWD}/

#MANIFEST NAME
MANIFEST=mxbuild.txt


#RUN!
#--------

#copy manifest template to source directory
cp ${MANIFEST} ${SOURCE}

#Change to where libs are stored and copy to batch path
cd ${LIBS}

#Copy explicitly (JUST CAUSE IT WORKS :D)
cp controlsfx-8.20.8.jar ${MYPATH}/lib/
cp openjfx-dialogs-1.0.2.jar ${MYPATH}/lib/

#Change to source directory
cd ${SOURCE}

#Calling jar creator with required data
jar cfm ./Matrixonator.jar ${MANIFEST} ${MAIN} ${MODEL} ${VIEW} ${FXBUILD}

#Move created jar back to where we called the script from
mv  ./Matrixonator.jar ${MYPATH}Matrixonator.jar

#Remove temporary manifest template
rm ${MANIFEST}

#Return console back to where it was, helps when called from CMD
cd ${MYPATH}


echo
# END
