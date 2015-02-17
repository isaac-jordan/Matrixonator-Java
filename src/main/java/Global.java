package main.java;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.java.model.Matrix;

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
   * The data as an observable list of matrices.
   */
  private static ObservableList<Matrix> matrixData = FXCollections.observableArrayList();
  
  
  /**
   * Add matrix to global list of Matrices
   * @param m - Matrix to add
   */
  public static void addMatrix(Matrix m){ matrixData.add(m); }
  
  /**
   * Get all the matrices that Matrixonator knows about
   * @return List of all matrices
   */
  public static ObservableList<Matrix> getMatrices(){return matrixData; }
  
  /**
   * Removes a matrix from the global list if found
   * @param m - Matrix to remove
   * @return True if success
   */
  public static boolean removeMatrix(Matrix m) { return matrixData.remove(m); } 
  
}
