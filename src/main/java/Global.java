package main.java;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.java.model.Matrix;
import main.java.model.MatrixIO;

/**
 * Contains many properties and methods that are used globally throughout Matrixonator
 * 
 * @author Ewan
 *
 */
public class Global {

  /**
   * Major version number
   */
  public static final int MAJOR_VERSION_NUMBER = 1;

  /**
   * Minor Version number
   */
  public static final int MINOR_VERSION_NUMBER = 0;

  /**
   * IO Flag set if there is no proper working directories
   */
  public static boolean DONT_SAVE = false;


  /**
   * Global Path seperator to use DEFAULT : '/' for linux
   */
  public static char PATH_SEP = '/';

  /**
   * The data as an observable list of matrices.
   */
  private static ObservableList<Matrix> matrixData = FXCollections.observableArrayList();

  /**
   * Add matrix to global list of Matrices
   * 
   * @param m - Matrix to add
   */
  public static void addMatrix(Matrix m) {
    matrixData.add(m);
  }

  /**
   * Get all the matrices that Matrixonator knows about
   * 
   * @return List of all matrices
   */
  public static ObservableList<Matrix> getMatrices() {
    return matrixData;
  }

  /**
   * Removes a matrix from the global list if found
   * 
   * @param m - Matrix to remove
   * @return True if success
   */
  public static boolean removeMatrix(Matrix m) {
    return matrixData.remove(m);
  }

  /**
   * Saves all matrices stored within the application
   * 
   * @return True if all files are saved successfully.
   */
  public static boolean saveAllMatrices() {

    boolean saveResult = true;

    for (Matrix m : matrixData) {
      // AND all results together, so if we get at least one false, it'll say false.
      saveResult = saveResult && MatrixIO.save(m);
    }
    return saveResult;
  }

}
