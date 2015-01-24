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
	
	// Returns a given row of Matrix
		public double[] getCol(int colNum) {
			double[] column = new double[this.getNumRows()];
			for (int i=0; i<this.getNumRows();i++) {
				column[i] = data.get()[i][colNum];
			}
			return column;
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
	public Matrix scalarMultiply(double c) {
		for (int i = 0; i < this.getNumRows(); i++) {
			for (int j = 0; j < this.getNumCols(); j++) {
				this.getData()[i][j] *= c;
			}
		}
		return this;
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
	
	//Seems to return cofactor matrix of x.
	//http://en.wikipedia.org/wiki/Minor_(linear_algebra)
	private static double[][] reduce(double[][] initialData, double[][] returnData, int row, int column, int numRows) {
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
		double[][] data = this.getData();
		double[][] cofactorData = new double[this.getNumRows()][this.getNumCols()];
		double det;
		for (int i=0;i<this.getNumRows();i++) {
			for (int j=0;j<this.getNumCols();j++) {
				double[][] reduced = new double[this.getNumRows() - 1][this.getNumCols() - 1];
				det = determinant(reduce(data,reduced,i,j,this.getNumRows()));
				cofactorData[i][j] = ((i + j) % 2 == 0 ? det : -det);
			}
		}
		return new Matrix(null, cofactorData, null);
	}
	
	public Matrix normalise() {
		for (int i = 0; i < this.getNumRows(); i++) {
			for (int j = 0; j < this.getNumCols(); j++) {
				if (this.getData()[i][j] == -0.0) {
					this.getData()[i][j] = 0.0;
				}
				// Round number to 10 decimal places.
				this.getData()[i][j] = Math
						.round(this.getData()[i][j] * 10000000000.0) / 10000000000.0;
			}
		}
		return this;
	}

	private static double determinant(double[][] data) {
		int numRows = data.length;
		double ret = 0;
		if (numRows == 2) {
			return data[0][0]*data[1][1] - data[0][1]*data[1][0];
		}
		
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
		
		double[][] reduced = new double[numRows - 1][numRows - 1]; //Create new matrix to contain cofactor matrix.
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
	 * @return
	 */
	public double determinant() {
		return determinant(getData());
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
	
	public Matrix transpose() {
		double[][] data = new double[this.getNumCols()][this.getNumRows()];
		for (int i=0;i<this.getNumRows();i++) {
			data[i] = this.getCol(i);
		}
		return new Matrix(null, data, null);
	}
	
	public Matrix inverse() { 
		double[][] data = this.cloneData();
		Matrix newMatrix = new Matrix(null, data, null);
		double det = determinant(this.getData());
		if (det != 0) {
			return newMatrix.cofactorMatrix().transpose().scalarMultiply(1/det).normalise();
		} else {
			return null;
		}
	}
	
}
