package main.java.model;

import java.time.LocalDate;

/**
 * Matrix subclass that handles specialised methods for diagonal matrices.
 * @author Isaac Jordan
 */
public class DiagonalMatrix extends Matrix {

  /**
   * Creates a diagonal matrix with the values given along the main diagonal.
   * @param name
   * @param values An array of values to put on the main diagonal. The length of this determines how many rows the diagonal matrix will have.
   * @param date Set creation date to this date. Can be null for today.
   */
  public DiagonalMatrix(String name, double[] values, LocalDate date) {
    super(name, new double[values.length][values.length], date);
    double[][] data = this.getData();
    for (int i = 0; i < values.length; i++) {
      data[i][i] = values[i];
    }
  }

  public DiagonalMatrix(Matrix parent) {
    // Check whether matrix parent is diagonalisable.
    // http://en.wikipedia.org/wiki/Diagonalizable_matrix
  }

  @Override
  public DiagonalMatrix multiplyScalar(double c) {
    for (int i = 0; i < this.getNumRows(); i++) {
      this.getData()[i][i] *= c;
    }
    return this;
  }

  @Override
  public double determinant() {
    double result = 1;
    for (int i = 0; i < this.getNumRows(); i++) {
      result *= this.getData()[i][i];
    }
    return result;
  }

}
