package ijordan.matrixonatorTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import ijordan.matrixonator.model.*;

import org.junit.Test;

public class DiagonalMatrixTest {

  @Test
  // Checks to see if a Diagonal Matrix is created properly
  public void testDiagonalMatrixCreation() {
    double[] data = {1, 3, 6, 9, 3};
    double[][] resultData =
        { {1.0, 0.0, 0.0, 0.0, 0.0}, {0.0, 3.0, 0.0, 0.0, 0.0}, {0.0, 0.0, 6.0, 0.0, 0.0},
            {0.0, 0.0, 0.0, 9.0, 0.0}, {0.0, 0.0, 0.0, 0.0, 3.0}};

    final Matrix testMatrix = new DiagonalMatrix("Test", data, null);
    assertEquals("Created matrix has inaccurate number of rows", testMatrix.getNumRows(), 5);
    assertEquals("Created matrix has inaccurate number of columns", testMatrix.getNumCols(), 5);
    assertTrue("Created matrix has inaccurate data",
        Arrays.deepEquals(testMatrix.getData(), resultData));
  }

  @Test
  // Checks to see if a Diagonal Matrix is created properly
  public void testDiagonalMatrixDeterminant() {
    double[] data = {1, 3, 6, 9, 3};
    double resultData = 486;

    final DiagonalMatrix testMatrix = new DiagonalMatrix("Test", data, null);
    assertTrue("Determinant calculation gives incorrect result.",
        testMatrix.determinant() == resultData);
  }

}
