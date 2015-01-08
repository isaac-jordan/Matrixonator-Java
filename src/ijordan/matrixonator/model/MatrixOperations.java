package ijordan.matrixonator.model;

import java.util.Arrays;

/* Matrix Operations
 * Author: Ewan McCartney
 * 
 * Contains static operations for matrices
 * REPLACES SimpleMatrixArithmetic & MatrixSolver
 */
public class MatrixOperations {
	
	
	//SIMPLE MATRIX ARITHMETIC METHODS
	//--------------------------------
	
	/**
	 * Checks whether it is possible to multiply two matrices together.
	 * 
	 * @param A
	 * @param B
	 * @return
	 */
	public static boolean checkMultCompatibility(Matrix A, Matrix B) {
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
	
	
	//MATRIX SOLVING METHODS
	//----------------------
	
	// THIS SECTION'S METHODS ARE ALL FOR SOLVING A MATRIX
	// NEEDS RESTRUCTURING

	/**
	 * Returns the reduced row echelon form of this matrix.
	 * 
	 * @return
	 */
	public static Matrix reducedEchelonForm(Matrix A) {
		Matrix localMatrix = new Matrix(null, A.getData());
		int i = 0;
		int j = 0;
		while (i < A.getNumRows() && j < A.getNumCols()) {
			j = stepOne(localMatrix, i, j);
			System.out.println("Solving...");
			stepTwo(localMatrix, i, j);
			System.out.println("Solving...");
			stepThree(localMatrix, i, j);
			System.out.println("Solving...");
			i += 1;
			j += 1;
		}
		System.out.println(Arrays.deepToString(localMatrix.getData()));
		return localMatrix;

	}

	private static Matrix ERO1(Matrix A, int row1, int row2) {
		// Swaps row1 and row2
		double[] temp = A.getData()[row1];
		A.getData()[row1] = A.getData()[row2];
		A.getData()[row2] = temp;
		return A;
	}

	private static Matrix ERO2(Matrix A, int row, double scalar) {
		// Multiply every element of row by scalar
		for (double value : A.getData()[row]) {
			value *= scalar;
		}
		return A;
	}

	private static Matrix ERO3(Matrix A, int row1, int row2, double scalar) {
		for (int i = 0; i < A.getNumCols(); i++) {
			A.getData()[row1][i] += scalar * A.getData()[row2][i];
		}
		return A;
	}

	// http://www.csun.edu/~panferov/math262/262_rref.pdf
	// i,j i,j+1 i,j+2
	// i+1,j i+1,j+1 i+1,j+2
	// i+2,j i+2,j+2 i+2,j+2 etc

	private static int stepOne(Matrix A, int i, int j) {
		// If A[i][j] = 0 swap the ith row with some other row (A[i+b]) below to
		// make A[i][j] not 0.
		// This A[i][j], non-zero entry is called a pivot.
		// If all entries in the column are zero, increase j by 1

		int b = 0;
		while (A.getData()[i][j] == 0 && i + b < A.getNumRows()) {
			if (A.getData()[i + b][j] == 0) {
				b += 1;
			} else {
				A = ERO1(A, i, (i + b));
			}
		}
		if (A.getData()[i][j] == 0) {
			j += 1;
			stepOne(A, i, j);
		}
		return j;
	}

	private static void stepTwo(Matrix A, int i, int j) {
		// Divide the ith row by A[i][j] to make the pivot entry = 1
		A = ERO2(A, i, (1 / A.getData()[i][j]));
	}

	private static void stepThree(Matrix A, int i, int j) {
		// Eliminate all other entries in the
		// jth column by subtracting suitable multiples of the
		// ith row from the other rows
		int x = 0;
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


}
