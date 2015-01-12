package ijordan.matrixonator.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class RREFMatrix extends Matrix {

	private final ObjectProperty<Matrix> parent;

	public RREFMatrix(Matrix parent) {
		super(null, new double[parent.getNumRows()][parent.getNumCols()], null);
		this.parent = new SimpleObjectProperty<Matrix>(parent);
		double[][] data = new double[parent.getNumRows()][parent.getNumCols()];

		for (int x = 0; x < parent.getNumRows(); x++) {
			for (int y = 0; y < parent.getNumCols(); y++) {
				data[x][y] = parent.getData()[x][y];
			}
		}
		
		Matrix localMatrix = new Matrix(null, data, null);
		int i = 0;
		int j = 0;
		while (i < parent.getNumRows() && j < parent.getNumCols()) {
			/*
			 * OLD CODE THAT IS BROKEN, STILL HERE FOR REFERENCE
			 * stepOne(localMatrix, i, j); if (i == localMatrix.getNumRows() -
			 * 1) { if (j == localMatrix.getNumCols() - 1) {
			 * stepThree(localMatrix, i, j); break; } else { while (j !=
			 * localMatrix.getNumCols()) { if (localMatrix.getData()[i][j] == 0)
			 * { //Do nothing } else { stepTwo(localMatrix, i, j); }
			 * stepThree(localMatrix, i, j); j += 1; } } } else { if
			 * (localMatrix.getData()[i][j] == 0) { //Do nothing } else {
			 * stepTwo(localMatrix, i, j); } stepThree(localMatrix, i, j); }
			 */
			if (stepOne(localMatrix, i, j) && j != localMatrix.getNumCols() - 1) {
				j++;
			}
			if (localMatrix.getData()[i][j] != 0) {
				stepTwo(localMatrix, i, j);
			}
			stepThree(localMatrix, i, j);
			i += 1;
			j += 1;
		}
		// At this stage, the data may contain -0.0, which is not equal to 0.0.
		// So we convert all -0.0 to 0.0.
		for (i = 0; i < localMatrix.getNumRows(); i++) {
			for (j = 0; j < localMatrix.getNumCols(); j++) {
				if (localMatrix.getData()[i][j] == -0.0) {
					localMatrix.getData()[i][j] = 0.0;
				}
				// Round number to 10 decimal places.
				localMatrix.getData()[i][j] = Math
						.round(localMatrix.getData()[i][j] * 10000000000.0) / 10000000000.0;
			}
		}

		for (int x = 0; x < parent.getNumRows(); x++) {
			for (int y = 0; y < parent.getNumCols(); y++) {
				this.getData()[x][y] = localMatrix.getData()[x][y];
			}
		}
	}

	// http://www.csun.edu/~panferov/math262/262_rref.pdf
	// i,j i,j+1 i,j+2
	// i+1,j i+1,j+1 i+1,j+2
	// i+2,j i+2,j+2 i+2,j+2 etc
	public static boolean stepOne(Matrix A, int i, int j) {
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
			return true;
		} else {
			return false;
		}
	}

	public static void stepTwo(Matrix A, int i, int j) {
		// Divide the ith row by A[i][j] to make the pivot entry = 1
		A = ERO2(A, i, (1 / A.getData()[i][j]));
	}

	public static void stepThree(Matrix A, int i, int j) {
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
	// END OF RREF CODE

}
