package main.java.model;

import java.time.LocalDate;

/**
 * Reduced Row Echelon Form matrix class.
 * @author Isaac Jordan
 */
public class RREFMatrix extends Matrix {

  /**
   * Creates the reduced row echelon form of the parent matrix.
   * @param parent
   */
  public RREFMatrix(Matrix parent) {
    super("RREF " + parent.getName(), new double[parent.getNumRows()][parent.getNumCols()],
        LocalDate.now());
    double[][] data = parent.cloneData();

    Matrix localMatrix = new Matrix(null, data, null);
    int i = 0;
    int j = 0;
    while (i < parent.getNumRows() && j < parent.getNumCols()) {
      if (stepOne(localMatrix, i, j) && j != localMatrix.getNumCols() - 1)
        j++;
      if (localMatrix.getData()[i][j] != 0)
        stepTwo(localMatrix, i, j);
      stepThree(localMatrix, i, j);
      i += 1;
      j += 1;
    }
    // At this stage, the data may contain -0.0, which is not equal to 0.0.
    // So we convert all -0.0 to 0.0.
    localMatrix.normalise();

    for (int x = 0; x < parent.getNumRows(); x++)
      for (int y = 0; y < parent.getNumCols(); y++)
        this.getData()[x][y] = localMatrix.getData()[x][y];
  }

  // http://www.csun.edu/~panferov/math262/262_rref.pdf
  /**
   * If A[i][j] = 0 swap the ith row with some other row (A[i+b]) below to make A[i][j] not 0. This
   * A[i][j], non-zero entry is called a pivot. If all entries in the column are zero, increase j by
   * 1
   * 
   * @param A
   * @param i
   * @param j
   * @return
   */
  public static boolean stepOne(Matrix A, int i, int j) {

    int b = 0;
    while (A.getData()[i][j] == 0 && i + b < A.getNumRows()) {
      if (A.getData()[i + b][j] == 0) {
        b += 1;
      } else {
        A = ERO1(A, i, (i + b));
      }
    }
    if (A.getData()[i][j] == 0) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * Divide the ith row by A[i][j] to make the pivot entry = 1
   * 
   * @param A
   * @param i
   * @param j
   */
  public static void stepTwo(Matrix A, int i, int j) {
    A = ERO2(A, i, (1 / A.getData()[i][j]));
  }

  /**
   * Eliminate all other entries in the jth column by subtracting suitable multiples of the ith row
   * from the other rows
   * 
   * @param A
   * @param i
   * @param j
   */
  public static void stepThree(Matrix A, int i, int j) {
    int x = 0;
    while (x < A.getNumRows()) {
      if (A.getData()[x][j] != 0 && x != i)
        A = ERO3(A, x, i, (A.getData()[x][j] * -1));
      x += 1;
    }
  }

}
