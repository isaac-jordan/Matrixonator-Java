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
	private final ObjectProperty<double[][]> data;

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

		this.numRows = new SimpleIntegerProperty(data.length);
		this.numCols = new SimpleIntegerProperty(data[0].length);
		if (date != null) {
			this.createdDate = new SimpleObjectProperty<LocalDate>(date);
		} else {
			this.createdDate = new SimpleObjectProperty<LocalDate>(LocalDate.now());
		}
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

	/*
	 * Row and Column getters This assumes that a Martix data is stored as
	 * [row][col] format
	 */
	// Returns a given row of Matrix
	public double[] getRow(int row) {
		return data.get()[row];
	}

	// Returns a given cell of the matrix
	public double getCell(int row, int col) {
		return data.get()[row][col];
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
						data[i][j] = data[i][j] + A.getData()[i][k]
								* B.getData()[k][j];
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
	
	public Matrix toPower(int n) {
		Matrix resultMatrix = new Matrix(null, this.cloneData(), null);
		for (int i=1; i<n; i++) {
			resultMatrix = Matrix.multiplyMatrices(this, resultMatrix);
		}
		return resultMatrix;
	}
	public void scalarMultiply(double c) {
		for (int i = 0; i < this.getNumRows(); i++) {
			for (int j = 0; j < this.getNumCols(); j++) {
				this.getData()[i][j] *= c;
			}
		}
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
		if (A.getNumRows() == B.getNumRows()
				&& A.getNumCols() == B.getNumCols()) {
			double[][] data = new double[A.getNumRows()][A.getNumCols()];
			for (int i = 0; i < A.getNumRows(); i++) {
				for (int j = 0; j < A.getNumCols(); j++) {
					data[i][j] = A.getData()[i][j] + B.getData()[i][j];
				}
			}
			return (new Matrix(null, data, null));
		} else {
			throw new IllegalArgumentException("Matrices are not compatible.");
		}
	}

	//Source: http://en.wikibooks.org/wiki/Algorithm_Implementation/Linear_Algebra/Determinant_of_a_Matrix
	//TODO: Make this more readable, and fit the code style better.
	private static double[][] reduce(double[][] x, double[][] y, int r, int c, int n) {
		for (int h = 0, j = 0; h < n; h++) {
			if (h == r)
				continue;
			for (int i = 0, k = 0; i < n; i++) {
				if (i == c)
					continue;
				y[j][k] = x[h][i];
				k++;
			} // end inner loop
			j++;
		} // end outer loop
		return y;
	} // end method

	// ===================================================
	private static double det(int NMAX, double[][] x) {
		double ret = 0;
		if (NMAX < 4)// base case
		{
			double prod1 = 1, prod2 = 1;
			for (int i = 0; i < NMAX; i++) {
				prod1 = 1;
				prod2 = 1;

				for (int j = 0; j < NMAX; j++) {
					prod1 *= x[(j + i + 1) % NMAX][j];
					prod2 *= x[(j + i + 1) % NMAX][NMAX - j - 1];
				} // end inner loop
				ret += prod1 - prod2;
			} // end outer loop
			return ret;
		} // end base case
		double[][] y = new double[NMAX - 1][NMAX - 1];
		for (int h = 0; h < NMAX; h++) {
			if (x[h][0] == 0)
				continue;
			reduce(x, y, h, 0, NMAX);
			if (h % 2 == 0)
				ret -= det(NMAX - 1, y) * x[h][0];
			if (h % 2 == 1)
				ret += det(NMAX - 1, y) * x[h][0];
		} // end loop
		return ret;
	} // end method

	public double determinant() {
		return det(this.getNumRows(), this.getData());
	}
	
	public double[][] cloneData() {
		double[][] result = new double[this.getNumRows()][this.getNumCols()];
		for (int i=0;i<this.getNumRows();i++) {
			for (int j=0;j<this.getNumCols();j++) {
				result[i][j] = this.getData()[i][j];
			}
		}
		return result;
	}
	

}
