<h1>Matrixonator-Java</h1>
<h2>&nbsp;&nbsp;Custom JAR build tools</h2>


<h3> Overivew </h3>
Custom build tools for Matrixonator, bundling compiled program into executable jar.

Windows and Bash files supplied (see below)

<h6> Each version requires mxbuild.txt to be in same directory. This makes jar executable. </h6>

<h6> PLEASE NOTE: You may need to give mkjar.sh execute permissions after downloading. Use:

    chmod u+x mkjar.sh  
</h6>

<h6> PLEASE NOTE: The requires jar tool to be installed (Part of JDK) </h6>

<h4> win/ </h4>
<h6>&nbsp;&nbsp;&nbsp;&nbsp;Windows batch files</h6>

<h4> lnx/ </h4>
<h6>&nbsp;&nbsp;&nbsp;&nbsp;Linux bash files</h6>

<h2> Variables </h2>
In either version, you will have to specify a number of locations before you run the build tool for the first time. Detailed below:

<h4> SOURCE </h4>
<h6> This is the root path of the <b>compiled</b> source <b>without</b> the package directories. This is normally <i>[PATH]/Matrixonator-Java/bin/</i>.</h6>

<h4> LIBS </h4>
<h6> This is where the jar dependencies are stored on your system. </h6>

<h4> EXTENSION </h4>
<h6> This is the package directories. This <b>generally</b> should not be changed.</h6>

<h4> MAIN </h4>
<h6> Relative path to MainApp.class from <b>SOURCE</b>.</h6>

<h4> MODEL </h4>
<h6> Relative path to model package from <b>SOURCE</b>.</h6>

<h4> VIEW </h4>
<h6> Relative path to view package from <b>SOURCE</b>.</h6>

<h4> FXBUILD </h4>
<h6> Relative path to .fxml files from <b>SOURCE</b>. The <b>generally</b> should not be changed</b>.</h6>

<h3> Output </h3>
Once run, you should see a lib directory, containing the .jar dependencies and Matrixonator.jar.

<h6> Directory Tree </h6>

    [PATH TO mkjar]
    |- lib/
    |   |_ jar dependencies...
    |- Matrixonator.jar
    |_ ...

To deploy, you must provide both the lib/ directory <b>AND</b> Matrixonator.jar