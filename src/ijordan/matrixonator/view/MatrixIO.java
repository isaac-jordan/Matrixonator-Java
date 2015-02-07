package ijordan.matrixonator.view;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

import ijordan.matrixonator.model.Matrix;

/*
 * MatrixIO -------- Provides static methods to deal with load and saving of matrices
 * 
 * Author: Ewan McCartney
 */
public class MatrixIO {

  // Flag if there has been an error when creating directories
  private static boolean dontSave = false;

  // Store OS Path Seperator, default is Linux/OSX
  private static char pathSep = '/';

  /**
   * Called from startup check to stop IO operations if no directory structure
   */
  public static void setSaveFlag() {
    dontSave = true;
  }

  /**
   * RESET METHOD FOR JUNIT TESTS. DO NOT USE IN APPLICATION
   */
  public static void resetSaveFlag() {
    dontSave = false;
  }

  /**
   * Performs the load operation on a matrix file
   * 
   * @param matrixFile File object to load
   * @return the loaded matrix is sucsessful
   * @throws Exception
   */
  private static Matrix load(File matrixFile) throws Exception {
    // Properties from file
    String name = "";
    LocalDate date = null;
    int Rows = 0;
    int Cols = 0;
    double[][] matrixData = null;

    try {
      // Attempting to read in file given
      FileReader fr = new FileReader(matrixFile);
      BufferedReader br = new BufferedReader(fr);

      name = br.readLine();
      date = LocalDate.parse(br.readLine());
      String[] NumRowsCols = br.readLine().split(",");
      Rows = Integer.parseInt(NumRowsCols[0]);
      Cols = Integer.parseInt(NumRowsCols[1]);

      matrixData = new double[Rows][Cols];

      for (int i = 0; i < Rows; ++i) {
        String row = br.readLine();
        String[] Values = row.split(",");
        int Col = 0;

        for (String val : Values) {
          matrixData[i][Col] = Double.parseDouble(val);
          ++Col;
        }
      }

      br.close();
    } catch (Exception e) {
      throw e;
    }

    return new Matrix(name, matrixData, date);
  }



  /**
   * Load Matrix from File
   * 
   * @param filename of Matrix to load. To load RREF, use different call
   * @return Loaded Matrix
   * @throws FileNotFoundException, IOException, MatrixonatorIOException
   */
  public static Matrix loadMatrix(String filename) throws Exception {

    if (dontSave) {
      throw new MatrixonatorIOException(
          "Save is currently disabled due to Matrixonator not having working directories. Please contact system administor for directory create rights.");
    } // Checking incase the working directories haven't worked properly

    filename = getWorkingDir() + MATRIXDIR + pathSep + filename;

    File matrixFile = new File(filename);

    if (!matrixFile.exists()) {
      throw new FileNotFoundException();
    }

    // Load Matrix and return it
    try {
      return load(matrixFile);
    } catch (Exception e) {
      throw e;
    }
  }

  /**
   * Save Matrix to file
   * 
   * @param matrix
   * @return True on success, false otherwise
   */

  /*
   * Saves Matrix as plain text (for now)
   * 
   * File Format: - Matrix Name - Matrix Date - Matrix NumRows/Cols - Matrix Data (Row per line,
   * Cols split with ,)
   */
  public static boolean save(Matrix matrix) {

    if (dontSave) {
      return false;
    } // Checking for Save flag on startup

    // Size required for data
    String[] buffer = new String[(matrix.getNumRows() + 3)];

    // Adds title information
    buffer[0] = matrix.getName();
    buffer[1] = matrix.getCreatedDate().toString();
    buffer[2] = matrix.getNumRows() + "," + matrix.getNumCols();

    for (int i = 3; i < (matrix.getNumRows() + 3); ++i) {
      // For each row, we add a new line and put each value in a string
      // seperated by ,
      StringBuilder line = new StringBuilder();
      double[] row = matrix.getRow(i - 3);

      for (double val : row) {
        line.append(val);
        line.append(",");
      }

      buffer[i] = line.toString();
    }

    // Actual IO Operation in try
    try {
      File matrixFile = new File(getWorkingDir() + MATRIXDIR + pathSep + buffer[0] + ".matrix");

      if (!matrixFile.exists()) {
        matrixFile.createNewFile();
      }

      FileWriter fw = new FileWriter(matrixFile.getAbsoluteFile());
      BufferedWriter bw = new BufferedWriter(fw);

      for (String line : buffer) {
        bw.append(line + "\n");
      }

      bw.close();
      return true;
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * Gets all the saved Matrices saved previously
   * 
   * @return List of loaded Matrices
   */
  public static ArrayList<Matrix> loadAll() {
    File searchDir = new File(getWorkingDir() + MATRIXDIR);
    String[] matrixNames = searchDir.list();
    ArrayList<Matrix> loadedMatrices = new ArrayList<Matrix>();

    if (matrixNames != null) {
      for (String name : matrixNames) {
        try {
          String filename = getWorkingDir() + MATRIXDIR + pathSep + name;
          File m = new File(filename);
          loadedMatrices.add(load(m));
        } catch (Exception e) {
          System.out.println("Error loading a saved matrix into the application: " + name);
        }

      }
    }
    return loadedMatrices;
  }

  /*
   * IO Helper Methods
   */
  private static String MATRIXDIR = null;
  private static String LOCALDIR = null;

  /**
   * @return The path to application working directory
   */
  private static String getWorkingDir() {
    return System.getProperty("user.dir");
  }

  /**
   * Performs IO startup checks and defines path separators for running OS If an error occurs, will
   * save a noSave flag where any IO operation is blocked. Application can still perform
   * calculations
   * 
   * @throws MatrixonatorIOException
   */
  public static void checkDirectories() throws MatrixonatorIOException {

    // Additional check to create proper path seperators per OS
    // WE MUST DO THIS FIRST. WONDER WHY THE HELL THIS EVEN WORKED ON LINUX BEFORE?!
    String tempLDir = "%Matrixonator";
    String tempMDir = "%Matrixonator%Matrix";
    String osName = System.getProperty("os.name");

    // Update Path Sep
    if (osName.startsWith("Windows")) {
      pathSep = '\\';
    }

    LOCALDIR = tempLDir.replace('%', pathSep);
    MATRIXDIR = tempMDir.replace('%', pathSep);


    // Checking for a working directory
    File BaseDirectory = new File(getWorkingDir() + LOCALDIR);
    if (!BaseDirectory.exists()) {

      try {
        if (!BaseDirectory.mkdir()) {
          throw new MatrixonatorIOException(
              "Unable to create working directories. Please contact system administrator for directory create rights.\n\nMatrixonator will still work, however you will be unable to save matrices or preferences");
        }
      } catch (SecurityException e) {
        e.printStackTrace();
        throw new MatrixonatorIOException(
            "Unable to create working directories due to secuirty issues. Please contact system administrator for directory create rights.");
      }
    }

    // Check for a directory to save matrix files to
    File MatrixDirectory = new File(getWorkingDir() + MATRIXDIR);
    if (!MatrixDirectory.exists()) {

      try {
        if (!MatrixDirectory.mkdir()) {
          throw new MatrixonatorIOException(
              "Unable to create working directories. Please contact system administrator for directory create rights.\n\nMatrixonator will still work, however you will be unable to save matrices or preferences");
        }
      } catch (SecurityException e) {
        e.printStackTrace();
        throw new MatrixonatorIOException(
            "Unable to create working directories due to secuirty issues. Please contact system administrator for directory create rights.");
      }
    }
  }

}
