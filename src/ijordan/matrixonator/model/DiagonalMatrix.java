package ijordan.matrixonator.model;

import java.time.LocalDate;
import java.util.Arrays;

public class DiagonalMatrix extends Matrix {

	public DiagonalMatrix(String name, double[] values, LocalDate date) {
		super(name, new double[values.length][values.length], date);
		double[][] data = this.getData();
		for (int i = 0; i < values.length; i++) {
			data[i][i] = values[i];
		}
	}

}
