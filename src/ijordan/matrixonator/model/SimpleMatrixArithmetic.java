package ijordan.matrixonator.model;

/* SimpleMatrixAirthmetic
 * Author: Ewan McCartney
 * 
 * Provides base methods for adding and multiplying matrices
 * 
 * NOTE: These methods were found in matrixonator.model.Matrix before being moved here
 */
public class SimpleMatrixArithmetic {

	/**
	 * Checks whether it is possible to multiply two matrices together.
	 * 
	 * @param A
	 * @param B
	 * @return
	 */
	public boolean checkMultCompatibility(Matrix A, Matrix B) {
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
	public Matrix multiplyMatrices(Matrix A, Matrix B) {
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
	public Matrix addMatrices(Matrix A, Matrix B) {
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
}
