package ijordan.matrixonator.model;

import java.time.LocalDate;
import java.util.Optional;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Matrix {

  private StringProperty name;
  private final IntegerProperty numRows;
  private final IntegerProperty numCols;
  private final ObjectProperty<LocalDate> createdDate;
  private final ObjectProperty<double[][]> data;

  private Double determinant;
  private Matrix inverse;
  private RREFMatrix RREForm;
  private Matrix cofactor;

  /**
   * Default constructor. Creates an empty, unnamed matrix.
   * 
   * @throws Exception
   */
  public Matrix() {
    this(null, new double[0][0], null);
  }

  /**
   * Constructor with some initial data.
   * 
   * @param name
   * @param data
   * @throws Exception
   */
  public Matrix(String name, double[][] data, LocalDate date) {
    this.name = new SimpleStringProperty(name);
    this.data = new SimpleObjectProperty<double[][]>(data);
    numRows = new SimpleIntegerProperty(data.length);
    numCols = new SimpleIntegerProperty(data[0].length);
    if (date != null)
      createdDate = new SimpleObjectProperty<LocalDate>(date);
    else
      createdDate = new SimpleObjectProperty<LocalDate>(LocalDate.now());
  }

  // Getters/Setters
  // name
  public String getName() {
    return name.get();
  }

  public void setName(String name) {
    this.name.set(name);
  }

  public StringProperty nameProperty() {
    return name;
  }

  // numRows
  public int getNumRows() {
    return numRows.get();
  }

  public IntegerProperty numRowsProperty() {
    return numRows;
  }

  // numCols
  public int getNumCols() {
    return numCols.get();
  }

  public IntegerProperty numColsProperty() {
    return numCols;
  }

  // createdDate
  public LocalDate getCreatedDate() {
    return createdDate.get();
  }

  public ObjectProperty<LocalDate> createdDateProperty() {
    return createdDate;
  }

  // data
  public double[][] getData() {
    return data.get();
  }

  public double[] getRow(int row) {
    return data.get()[row];
  }

  public double[] getCol(int colNum) {
    double[] column = new double[getNumRows()];
    for (int i = 0; i < getNumRows(); i++)
      column[i] = data.get()[i][colNum];
    return column;
  }

  public double getCell(int row, int col) {
    return data.get()[row][col];
  }

  public double[][] cloneData() {
    double[][] result = new double[getNumRows()][getNumCols()];
    for (int i = 0; i < getNumRows(); i++) {
      for (int j = 0; j < getNumCols(); j++) {
        result[i][j] = getData()[i][j];
      }
    }
    return result;
  }

  /**
   * Changes matrix's data to positive 0's and 10 decimal places.
   * 
   * @return
   */
  public Matrix normalise() {
    for (int i = 0; i < getNumRows(); i++) {
      for (int j = 0; j < getNumCols(); j++) {
        if (getData()[i][j] == -0.0)
          getData()[i][j] = 0.0;
        // Round number to 10 decimal places.
        getData()[i][j] = Math.round(getData()[i][j] * 10000000000.0) / 10000000000.0;
      }
    }
    return this;
  }

  /**
   * Checks whether it is possible to multiply two matrices together.
   * 
   * @param A
   * @param B
   * @return
   */
  public static Boolean checkMultCompatibility(Matrix A, Matrix B) {
    if (A.getData().length != B.getData()[0].length)
      return false;
    return true;
  }

  /**
   * Uses naive method to calculate the product of two matrices.
   * 
   * @param A
   * @param B
   * @return
   */
  public static Matrix multiplyMatrices(Matrix A, Matrix B) {
    if (checkMultCompatibility(A, B)) {
      double[][] data = new double[A.getNumRows()][B.getNumCols()];
      int i = 0;
      int j = 0;
      int k = 0;
      while (i < A.getNumRows()) {
        while (j < B.getNumCols()) {
          while (k < B.getNumRows()) {
            data[i][j] = data[i][j] + A.getData()[i][k] * B.getData()[k][j];
            k += 1;
          }
          j += 1;
          k = 0;
        }
        i += 1;
        j = 0;
      }
      return (new Matrix(null, data, null));
    }
    throw new IllegalArgumentException("Matrices are not compatible");
  }

  public Matrix multiplyScalar(double c) {
    for (int i = 0; i < getNumRows(); i++)
      for (int j = 0; j < getNumCols(); j++)
        getData()[i][j] *= c;
    return this;
  }

  public Matrix toPower(int n) {
    Matrix resultMatrix = new Matrix(null, cloneData(), null);
    for (int i = 1; i < n; i++)
      resultMatrix = Matrix.multiplyMatrices(this, resultMatrix);
    return resultMatrix;
  }


  /**
   * Static method that adds two matrices A, and B. If the matrices cannot be added, an
   * IllegalArgumentException is thrown.
   *
   * @param A
   * @param B
   * @return
   */
  public static Matrix addMatrices(Matrix A, Matrix B) {
    if (A.getNumRows() == B.getNumRows() && A.getNumCols() == B.getNumCols()) {
      double[][] data = new double[A.getNumRows()][A.getNumCols()];
      for (int i = 0; i < A.getNumRows(); i++)
        for (int j = 0; j < A.getNumCols(); j++)
          data[i][j] = A.getData()[i][j] + B.getData()[i][j];
      return (new Matrix(null, data, null));
    } else
      throw new IllegalArgumentException("Matrices are not compatible.");
  }


  // http://en.wikipedia.org/wiki/Minor_(linear_algebra)
  private static double[][] reduce(double[][] initialData, double[][] returnData, int row,
      int column, int numRows) {
    for (int h = 0, j = 0; h < numRows; h++) {
      if (h == row)
        continue;
      for (int i = 0, k = 0; i < numRows; i++) {
        if (i == column)
          continue;
        returnData[j][k] = initialData[h][i];
        k++;
      }
      j++;
    }
    return returnData;
  }

  public Matrix cofactorMatrix() {
    if (cofactor != null) {
      return cofactor;
    }
    double[][] data = getData();
    double[][] cofactorData = new double[getNumRows()][getNumCols()];
    double det;
    for (int i = 0; i < getNumRows(); i++) {
      for (int j = 0; j < getNumCols(); j++) {
        double[][] reduced = new double[getNumRows() - 1][getNumCols() - 1];
        det = determinant(reduce(data, reduced, i, j, getNumRows()));
        cofactorData[i][j] = ((i + j) % 2 == 0 ? det : -det);
      }
    }
    cofactor = new Matrix(null, cofactorData, null);
    return cofactor;
  }



  /**
   * Private static method that deals with the recursion of determinant calculation. Inspired by
   * http://en.wikibooks.org/wiki/Algorithm_Implementation/Linear_Algebra/Determinant_of_a_Matrix
   * 
   * @param data
   * @return
   */
  private static double determinant(double[][] data) {
    int numRows = data.length;
    double ret = 0;
    if (numRows == 2)
      return data[0][0] * data[1][1] - data[0][1] * data[1][0];

    if (numRows < 4) {
      double prod1 = 1, prod2 = 1;
      for (int i = 0; i < numRows; i++) {
        prod1 = 1;
        prod2 = 1;

        for (int j = 0; j < numRows; j++) {
          prod1 *= data[(j + i + 1) % numRows][j];
          prod2 *= data[(j + i + 1) % numRows][numRows - j - 1];
        }
        ret += prod1 - prod2;
      }
      return ret;
    }

    double[][] reduced = new double[numRows - 1][numRows - 1];
    for (int h = 0; h < numRows; h++) {
      if (data[h][0] == 0)
        continue;
      reduce(data, reduced, h, 0, numRows);
      if (h % 2 == 0)
        ret -= determinant(reduced) * data[h][0];
      if (h % 2 == 1)
        ret += determinant(reduced) * data[h][0];
    }
    return ret;
  }

  /**
   * Public method for determinant calculation.
   * 
   * @return
   */
  public double determinant() {
    if (determinant != null) {
      return determinant;
    }
    determinant = determinant(getData());
    return determinant;
  }

  public static Matrix ERO1(Matrix A, int row1, int row2) {
    // Swaps row1 and row2
    double[] temp = A.getData()[row1];
    A.getData()[row1] = A.getData()[row2];
    A.getData()[row2] = temp;
    return A;
  }

  public static Matrix ERO2(Matrix A, int row, double scalar) {
    // Multiply every element of row by scalar
    for (int i = 0; i < A.getNumCols(); i++) {
      A.getData()[row][i] *= scalar;
    }
    return A;
  }

  public static Matrix ERO3(Matrix A, int row1, int row2, double scalar) {
    // row1 = row1 + scalar*row2
    for (int i = 0; i < A.getNumCols(); i++) {
      A.getData()[row1][i] += scalar * A.getData()[row2][i];
    }
    return A;
  }

  public Matrix transpose() {
    double[][] data = new double[this.getNumCols()][this.getNumRows()];
    for (int i = 0; i < this.getNumRows(); i++) {
      data[i] = this.getCol(i);
    }
    return new Matrix(null, data, null);
  }

  // Not Working Yet
  public Matrix inverse() {
    double[][] data = this.cloneData();
    Matrix newMatrix = new Matrix(null, data, null);
    double det = this.determinant();
    if (det != 0) {
      return newMatrix.transpose().multiplyScalar(1 / det);
    } else {
      return null;
    }
  }

}
