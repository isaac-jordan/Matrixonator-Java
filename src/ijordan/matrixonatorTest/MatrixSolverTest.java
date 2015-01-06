package ijordan.matrixonatorTest;

import static org.junit.Assert.*;
import ijordan.matrixonator.model.Matrix;
import ijordan.matrixonator.model.MatrixSolver;
import ijordan.matrixonator.model.SimpleMatrixArithmetic;

import org.junit.Test;

public class MatrixSolverTest {
	
	//Required solver and arithmetic (Since Revision 2)
	private SimpleMatrixArithmetic SMA = new SimpleMatrixArithmetic();
	private MatrixSolver MS = new MatrixSolver();

	@Test
	public void testMatrixReduceNormal() {
		double[][] data = { { 1, 0, 3, 3 }, { 0, 1, 0, 4 }, { 0, 0, 0, 1 } };
		double[][] dataResult = { { 1, 0, 3, 0 }, { 0, 1, 0, 0 }, { 0, 0, 0, 1 } };
		final Matrix testMatrix = new Matrix("Test1", data);

		assertEquals("Matrix Reduction gives incorrect result", dataResult,
									MS.reducedEchelonForm(testMatrix).getData());
	}

	@Test
	public void testMatrixReduceNegative() {
		double[][] data = { { 1, 2, 1 }, { -2, -3, 1 }, { 3, 5, 0 } };
		double[][] dataResult = { { 1, 0, -5 }, { 0, 1, 3 }, { 0, 0, 0 } };
		final Matrix testMatrix = new Matrix("Test2", data);

		assertEquals("Matrix Reduction gives incorrect result", dataResult, 
													MS.reducedEchelonForm(testMatrix).getData());
	
	}

	@Test
	public void testMatrixReduceLargeValue() {
		double[][] data = { { 1586, 7546, 85746, 57564 }, { 756, 374, 385, 0 },
				{ 8765, 123, 765, 234 }, { 5736, 4736, 27364, 5 } };
		double[][] dataResult = { { 1, 0, 0, 0 }, { 0, 1, 0, 0 }, { 0, 0, 1, 0 }, { 0, 0, 0, 1 } };
		final Matrix testMatrix = new Matrix("Test2", data);

		assertEquals("Matrix Reduction gives incorrect result", dataResult, MS.reducedEchelonForm(testMatrix).getData());
	}

}
