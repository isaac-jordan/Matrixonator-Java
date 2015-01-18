package ijordan.matrixonator.model;

import java.time.LocalDate;

public class DiagonalMatrix extends Matrix {

	public DiagonalMatrix(String name, double[] values, LocalDate date) {
		super(name, new double[values.length][values.length], date);
		double[][] data = this.getData();
		for (int i = 0; i < values.length; i++) {
			data[i][i] = values[i];
		}
	}
	
	public DiagonalMatrix(Matrix parent) {
		//Check whether matrix parent is diagonalisable.
		//http://en.wikipedia.org/wiki/Diagonalizable_matrix
	}
	
	@Override
	public void scalarMultiply(double c) {
		for (int i = 0; i < this.getNumRows(); i++) {
			this.getData()[i][i] *= c;
		}
	}

}
