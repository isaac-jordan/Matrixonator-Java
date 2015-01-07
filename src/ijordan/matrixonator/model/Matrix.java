package ijordan.matrixonator.model;

import java.time.LocalDate;
import java.util.Arrays;

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
	private ObjectProperty<double[][]> data;

	/**
	 * Default constructor. Creates an empty, unnamed matrix.
	 */
	public Matrix() {
		this(null, new double[0][0]);
	}

	/**
	 * Constructor with some initial data.
	 * 
	 * @param name
	 * @param data
	 */
	public Matrix(String name, double[][] data) {
		this.name = new SimpleStringProperty(name);
		this.data = new SimpleObjectProperty<double[][]>(data);

		this.numRows = new SimpleIntegerProperty(data.length);
		this.numCols = new SimpleIntegerProperty(data[0].length); 
		this.createdDate = new SimpleObjectProperty<LocalDate>(LocalDate.now());
	}

	/**
	 * Checks whether it is possible to multiply two matrices together.
	 * 
	 * @param A
	 * @param B
	 * @return
	 */
	public static Boolean checkMultCompatibility(Matrix A, Matrix B) {
		if (A.getData().length != B.getData()[0].length) {
			return false;
		}
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
			return (new Matrix(null, data));
		}
		throw new IllegalArgumentException("Matrices are not compatible");

	}

	/**
	 * Static method that adds two matrices A, and B. If the matrices cannot be
	 * added, an IllegalArgumentException is thrown.
	 * 
	 * @param A
	 * @param B
	 * @return
	 */
	public static Matrix addMatrices(Matrix A, Matrix B) {
		if (A.getNumRows() == B.getNumRows() && A.getNumCols() == B.getNumCols()) {
			double[][] data = new double[A.getNumRows()][A.getNumCols()];
			for (int i = 0; i < A.getNumRows(); i++) {
				for (int j = 0; j < A.getNumCols(); j++) {
					data[i][j] = A.getData()[i][j] + B.getData()[i][j];
				}
			}
			return (new Matrix(null, data));

		} else {
			throw new IllegalArgumentException("Matrices are not compatible.");
		}
	}

	// THIS SECTION'S METHODS ARE ALL FOR SOLVING A MATRIX
	// NEEDS RESTRUCTURING

	/**
	 * Returns the reduced row echelon form of this matrix.
	 * 
	 * @return
	 */
	public Matrix reducedEchelonForm() {
		Matrix localMatrix = new Matrix(null, this.getData());
		int i = 0;
		int j = 0;
		while (i < this.getNumRows() && j < this.getNumCols()) {
/*			OLD CODE THAT IS BROKEN, STILL HERE FOR REFERENCE
 * stepOne(localMatrix, i, j);

			if (i == localMatrix.getNumRows() - 1) {
				if (j == localMatrix.getNumCols() - 1) {
					stepThree(localMatrix, i, j);
					break;
				} else {
					while (j != localMatrix.getNumCols()) {
						if (localMatrix.getData()[i][j] == 0) {
							//Do nothing
						} else {
							stepTwo(localMatrix, i, j);
						}
						stepThree(localMatrix, i, j);
						j += 1;
					}
				}
			} else {
				if (localMatrix.getData()[i][j] == 0) {
					//Do nothing
				} else {
					stepTwo(localMatrix, i, j);
				}
				stepThree(localMatrix, i, j);
			}*/
			
			if (stepOne(localMatrix, i, j) && j != localMatrix.getNumCols()-1) {
				j++;
			}
			stepTwo(localMatrix, i, j);
			
			stepThree(localMatrix, i, j);

			i += 1;
			j += 1;
			

		}
		System.out.println("RREF: " + Arrays.deepToString(localMatrix.getData()));
		return localMatrix;

	}

	public Matrix ERO1(Matrix A, int row1, int row2) {
		// Swaps row1 and row2
		double[] temp = A.getData()[row1];
		A.getData()[row1] = A.getData()[row2];
		A.getData()[row2] = temp;
		return A;
	}

	public Matrix ERO2(Matrix A, int row, double scalar) {
		// Multiply every element of row by scalar
		for (int i = 0; i < A.getNumCols(); i++) {
			A.getData()[row][i] *= scalar;
		}
		return A;
	}

	public Matrix ERO3(Matrix A, int row1, int row2, double scalar) {
		//row1 = row1 + scalar*row2
		for (int i = 0; i < A.getNumCols(); i++) {
			A.getData()[row1][i] += scalar * A.getData()[row2][i];
		}
		return A;
	}

	// http://www.csun.edu/~panferov/math262/262_rref.pdf
	// i,j i,j+1 i,j+2
	// i+1,j i+1,j+1 i+1,j+2
	// i+2,j i+2,j+2 i+2,j+2 etc

	public boolean stepOne(Matrix A, int i, int j) {
		// If A[i][j] = 0 swap the ith row with some other row (A[i+b]) below to
		// make A[i][j] not 0.
		// This A[i][j], non-zero entry is called a pivot.
		// If all entries in the column are zero, increase j by 1

		int x = 0;
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

	public void stepTwo(Matrix A, int i, int j) {
		// Divide the ith row by A[i][j] to make the pivot entry = 1
		A = ERO2(A, i, (1 / A.getData()[i][j]));
		
	}

	public void stepThree(Matrix A, int i, int j) {
		// Eliminate all other entries in the
		// jth column by subtracting suitable multiples of the
		// ith row from the other rows
		int x = 0;
		int y = 0;
		while (x < A.getNumRows()) {
			if (A.getData()[x][j] != 0 && x != i) {
				// If this entry in the jth column isn't zero
				// and it's not the ith row make it zero
				A = ERO3(A, x, i, (A.getData()[x][j] * -1));
			}
			x += 1;
		}
	}

	// END OF RREF CODE

	//Getters/Setters
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

	public void setNumRows(int numRows) {
		this.numRows.set(numRows);
	}

	public IntegerProperty numRowsProperty() {
		return numRows;
	}

	// numCols
	public int getNumCols() {
		return numCols.get();
	}

	public void setNumCols(int numCols) {
		this.numCols.set(numCols);
	}

	public IntegerProperty numColsProperty() {
		return numCols;
	}

	// createdDate
	public LocalDate getCreatedDate() {
		return createdDate.get();
	}

	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate.set(createdDate);
	}

	public ObjectProperty<LocalDate> createdDateProperty() {
		return createdDate;
	}

	// data
	public double[][] getData() {
		return data.get();
	}

}
