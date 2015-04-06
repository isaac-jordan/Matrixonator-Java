package main.java.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Main Matrix class for mathematical matrix operations.
 * @author Isaac Jordan
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class Matrix<T extends Field> {

  private StringProperty name;
  private final IntegerProperty numRows;
  private final IntegerProperty numCols;
  private final ObjectProperty<LocalDate> createdDate;
  private final SimpleObjectProperty<List<List<T>>> data;

  private T determinant;
  private Matrix inverse;
  private RREFMatrix RREForm;
  private Matrix cofactor;

  /**
   * Constructor with some initial data.
   * 
   * @param name
   * @param data
   * @throws Exception
   */
  public Matrix(String name, List<List<T>> data, LocalDate date) {
    this.name = new SimpleStringProperty(name);
    //this.data = new SimpleObjectProperty<T[][]>(data);
    this.data = new SimpleObjectProperty<>(data);
    numRows = new SimpleIntegerProperty(data.size());
    numCols = new SimpleIntegerProperty(data.get(0).size());
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

  // createdDateget
  public LocalDate getCreatedDate() {
    return createdDate.get();
  }

  public ObjectProperty<LocalDate> createdDateProperty() {
    return createdDate;
  }

  // data
  public List<List<T>> getData() {
    return data.get();
  }

  public List<T> getRow(int row) {
    return data.get().get(row);
  }

  public List<T> getCol(int colNum) {
	List<T> column = new ArrayList<>();
    for (int i = 0; i < getNumRows(); i++)
      column.add(data.get().get(i).get(colNum));
    return column;
  }

  public T getCell(int row, int col) {
    return data.get().get(row).get(col);
  }

  public List<List<T>> cloneData() {
	List<List<T>> result = new ArrayList<>(getNumRows());
	for (List<T> row : result) {
		row = new ArrayList<>(getNumCols());
	}
    for (int i = 0; i < getNumRows(); i++) {
      for (int j = 0; j < getNumCols(); j++) {
        result.get(i).set(j, getData().get(i).get(j));
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
    List<List<T>> data = getData();
    for (int i = 0; i < getNumRows(); i++) {
      for (int j = 0; j < getNumCols(); j++) {
        data.get(i).get(j).normalise();
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
    if (A.getNumRows() != B.getNumCols())
      return false;
    return true;
  }

  /**
   * Uses naive method to calculate the product of two matrices.
   * Throws IllegalArgumentException if the matrices are not compatible.
   * 
   * @param A
   * @return
   */
  public Matrix multiplyMatrices(Matrix A) {
    if (checkMultCompatibility(this, A)) {
      List<List<T>> result = new ArrayList<>(this.getNumRows());
  	for (List<T> row : result) {
  		row = new ArrayList<>(A.getNumCols());
  	}
  	List<List<T>> local1 = this.getData();
  	List<List<T>> local2 = A.getData();
      int i = 0;
      int j = 0;
      int k = 0;
      while (i < this.getNumRows()) {
        while (j < A.getNumCols()) {
          while (k < A.getNumRows()) {
            result.get(i).set(j, (T) result.get(i).get(j).add(local1.get(i).get(k).multiply(local2.get(k).get(j))));
            k += 1;
          }
          j += 1;
          k = 0;
        }
        i += 1;
        j = 0;
      }
      return (new Matrix(null, result, null));
    }
    throw new IllegalArgumentException("Matrices are not compatible");
  }

  /**
   * Multiples a matrix by a scalar.
   * @param c - A double value to multiple all entries of the matrix by.
   * @return
   */
  public Matrix multiplyScalar(T c) {
	  List<List<T>> result = new ArrayList<>(this.getNumRows());
	  	for (List<T> row : result) {
	  		row = new ArrayList<>(getNumCols());
	  	}
    for (int i = 0; i < getNumRows(); i++)
      for (int j = 0; j < getNumCols(); j++)
        result.get(i).set(j, (T) result.get(i).get(j).multiply(c));
    return (new Matrix<>(null, result, null));
  }

  /**
   * Raises matrix to the power n using naive method.
   * Can use a lot of resources if matrix, or n, is large.
   * @param n
   * @return
   */
  public Matrix toPower(int n) {
    Matrix resultMatrix = new Matrix<>(null, cloneData(), null);
    for (int i = 1; i < n; i++)
      resultMatrix = multiplyMatrices(resultMatrix);
    return resultMatrix;
  }


  /**
   * Static method that adds two matrices A, and B. If the matrices cannot be added, an
   * IllegalArgumentException is thrown.
   *
   * @param A
   * @return
   */
  public Matrix addMatrices(Matrix A) {
    if (getNumRows() == A.getNumRows() && getNumCols() == A.getNumCols()) {
    	List<List<T>> local1 = getData();
    	List<List<T>> local2 = A.getData();
    	List<List<T>> result = new ArrayList<>(this.getNumRows());
	  	for (List<T> row : result) {
	  		row = new ArrayList<>(getNumCols());
	  	}
      for (int i = 0; i < getNumRows(); i++)
        for (int j = 0; j < getNumCols(); j++)
          result.get(i).set(j, (T) local1.get(i).get(j).add(local2.get(i).get(j)));
      return (new Matrix<>(null, result, null));
    } else
      throw new IllegalArgumentException("Matrices are not compatible.");
  }

  /**
   * Returns the nested array after having removed the values in the given row, and column.
   * http://en.wikipedia.org/wiki/Minor_(linear_algebra)
   * 
   * @param initialData
   * @param returnData - The object to fill with the result.
   * @param row - Which row's data to remove
   * @param column - Which column's data to remove.
   * @param numRows - How many rows initialData has.
   * @return
   */
  private List<List<T>> reduce(List<List<T>> initialData, List<List<T>> returnData, int row,
      int column, int numRows) {
	  //initialData.get(0).get(0).getClass();
    for (int h = 0, j = 0; h < numRows; h++) {
      if (h == row)
        continue;
      for (int i = 0, k = 0; i < numRows; i++) {
        if (i == column)
          continue;
        returnData.get(j).set(k, initialData.get(h).get(i));
        k++;
      }
      j++;
    }
    return returnData;
  }

  /**
   * Returns the full cofactor matrix of this matrix.
   * Warning: This is very computationally heavy.
   * @return
   */
  public Matrix cofactorMatrix() {
    if (cofactor != null) {
      return cofactor;
    }
    List<List<T>> data = cloneData();
    List<List<T>> cofactorData = new ArrayList<>(this.getNumRows());
  	for (List<T> row : cofactorData) {
  		row = new ArrayList<>(getNumCols());
  	}
    T det;
    for (int i = 0; i < getNumRows(); i++) {
      for (int j = 0; j < getNumCols(); j++) {
        List<List<T>> reduced = new ArrayList<>(getNumRows() -1);
	  	for (List<T> row : reduced) {
	  		row = new ArrayList<>(getNumCols()-1);
	  	}
        det = determinant(reduce(data, reduced, i, j, getNumRows()));
        cofactorData.get(i).set(j, ((i + j) % 2 == 0 ? det : (T) det.negate()));
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
  private T determinant(List<List<T>> data) {
    int numRows = data.size();
    T ret = (T) data.get(0).get(0).getZero();
    if (numRows == 2)
    	//[0][0]*[1][1] - [0][1]*[1][0]
      return (T) data.get(0).get(0).multiply(data.get(1).get(1)).add((data.get(0).get(1).multiply(data.get(1).get(0)).negate()));

    if (numRows < 4) {
      T prod1 = (T) data.get(0).get(0).getUnity();
      T prod2 = (T) data.get(0).get(0).getUnity();
      for (int i = 0; i < numRows; i++) {
    	  prod1 = (T) prod1.getUnity();
          prod2 = (T) prod2.getUnity();

        for (int j = 0; j < numRows; j++) {
          prod1 = (T) prod1.multiply(data.get((j + i + 1) % numRows).get(j));
          prod2 = (T) prod2.multiply(data.get((j + i + 1) % numRows).get(numRows - j - 1));
        }
        ret.add(prod1.add(prod2.negate()));
      }
      return ret;
    }

    List<List<T>> reduced = new ArrayList<>(numRows - 1);
  	for (List<T> row : reduced) {
  		row = new ArrayList<>(numRows - 1);
  	}
    for (int h = 0; h < numRows; h++) {
      if (data.get(h).get(0).equals(data.get(h).get(0).getZero()))
        continue;
      reduce(data, reduced, h, 0, numRows);
      if (h % 2 == 0)
        ret.add(determinant(reduced).multiply(data.get(h).get(0)).negate());
      if (h % 2 == 1)
        ret.add(determinant(reduced).multiply(data.get(h).get(0)));
    }
    return ret;
  }

  /**
   * Public method for determinant calculation.
   * 
   * @return
   */
  public T determinant() {
    if (determinant != null) {
      return determinant;
    }
    determinant = determinant(getData());
    return determinant;
  }

  /**
   * Swaps row1 and row2
   * @param A
   * @param row1
   * @param row2
   * @return
   */
  public Matrix ERO1(Matrix A, int row1, int row2) {
    List<T> temp = new ArrayList<>(A.getNumCols());
    temp = (List<T>) A.getData().get(row1);
    A.getData().set(row1, A.getData().get(row2));
    A.getData().set(row2, temp);
    return A;
  }

  /**
   * Multiply every element of row by scalar
   * @param A
   * @param row
   * @param scalar
   * @return
   */
  public Matrix ERO2(Matrix A, int row, T scalar) {
	  List<List<T>> data = A.getData();
    for (int i = 0; i < A.getNumCols(); i++) {
      data.get(row).set(i, (T) data.get(row).get(i).multiply(scalar));
    }
    return A;
  }

  /**
   * Executes: row1 = row1 + scalar*row2
   * @param A
   * @param row1
   * @param row2
   * @param scalar
   * @return
   */
  public Matrix ERO3(Matrix A, int row1, int row2, T scalar) {
	  List<List<T>> data = A.getData();
    for (int i = 0; i < A.getNumCols(); i++) {
      data.get(row1).set(i, (T) data.get(row1).get(i).add(scalar.multiply(data.get(row2).get(i))));
    }
    return A;
  }

  /**
   * Returns the transpose of this matrix in a new Matrix object.
   * @return
   */
  public Matrix transpose() {
	  List<List<T>> data = new ArrayList<>(this.getNumRows());
	  	for (List<T> row : data) {
	  		row = new ArrayList<>(getNumCols());
	  	}
    for (int i = 0; i < this.getNumRows(); i++) {
      data.set(i, this.getCol(i));
    }
    return new Matrix(null, data, null);
  }

  /**
   * Returns the inverse of this matrix in a new matrix object.
   * The inverse is only calculated the first time this method is called,
   * and stored for subsequent calls.
   * @return
   */
  public Matrix inverse() {
    if (inverse != null) {
      return inverse;
  }
  T det = determinant();
  if (!det.equals(det.getZero())) {
    inverse = cofactorMatrix().transpose().multiplyScalar((T) det.inverse()).normalise();
    return inverse;
  } else
    return null;
  }
  
  /**
   * Returns the sum of all values on the main diagonal of this matrix.
   * @return
   */
  public T trace() {
    T total = (T) getCell(0, 0).getZero();
    List<List<T>> data = new ArrayList<>(this.getNumRows());
  	for (List<T> row : data) {
  		row = new ArrayList<>(getNumCols());
  	}
    data = getData();
    for (int i = 0; i < getNumRows(); i++) {
      total.add(data.get(i).get(i));
    }
    return total;
  }
  
  public RREFMatrix reducedEchelonForm() {
    if (RREForm != null) {
      return RREForm;
    }
    RREForm = new RREFMatrix(this);
    return RREForm;
  }
  
  /**
   * Currently only works for 2x2 matrices.
   * http://en.wikipedia.org/wiki/Eigenvalue_algorithm#Direct_calculation
   * @return
   */
  /*public List<T> eigenvalues() {
    if (getNumRows() == 2 && getNumCols() == 2){
      double lambda1 = (this.trace() + Math.sqrt(Math.pow(this.trace(), 2) - 4 * this.determinant()))/2;
      double lambda2 = (this.trace() - Math.sqrt(Math.pow(this.trace(), 2) - 4 * this.determinant()))/2;
      double[] returnData = {lambda1,lambda2};
      return returnData;
    } else {
      return null;
    }
  }*/

}