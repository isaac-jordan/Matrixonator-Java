package ijordan.matrixonatorTest;

import static org.junit.Assert.*;

import java.util.Arrays;

import ijordan.matrixonator.model.Matrix;
import ijordan.matrixonator.model.RREFMatrix;

import org.junit.Test;

public class RREFMatrixTest {

  /*
   * -------------------------- Matrix Reduction Tests -------------------
   */

  @Test
  public void testMatrixReduceNormal() {
    double[][] data = { {1, 0, 3, 3}, {0, 1, 0, 4}, {0, 0, 0, 1}};
    double[][] dataResult = { {1, 0, 3, 0}, {0, 1, 0, 0}, {0, 0, 0, 1}};
    final RREFMatrix testMatrix = new RREFMatrix(new Matrix("Test", data, null));

    assertTrue("Matrix Reduction gives incorrect result",
        Arrays.deepEquals(dataResult, testMatrix.getData()));
  }

  @Test
  public void testMatrixReduceNormal2() {
    double[][] data = { {15, 3, 6}, {0, 3, 6}};
    double[][] dataResult = { {1, 0, 0}, {0, 1, 2}};
    final RREFMatrix testMatrix = new RREFMatrix(new Matrix("Test", data, null));

    assertTrue("Matrix Reduction gives incorrect result",
        Arrays.deepEquals(dataResult, testMatrix.getData()));
  }

  @Test
  public void testMatrixReduceManyRows() {
    double[][] data =
        { {1, 2, 3, 4}, {-1, -2, 0, 1}, {5, 6, 7, 8}, {1, 3, 2, 4}, {0, 1, 2, 3}, {5, 6, 7, 8}};
    double[][] dataResult =
        { {1, 0, 0, 0}, {0, 1, 0, 0}, {0, 0, 1, 0}, {0, 0, 0, 1}, {0, 0, 0, 0}, {0, 0, 0, 0}};
    final RREFMatrix testMatrix = new RREFMatrix(new Matrix("Test", data, null));

    assertTrue("Matrix Reduction gives incorrect result",
        Arrays.deepEquals(dataResult, testMatrix.getData()));
  }

  @Test
  public void testMatrixReduceNegative() {
    double[][] data = { {1, 2, 1}, {-2, -3, 1}, {3, 5, 0}};
    double[][] dataResult = { {1, 0, -5}, {0, 1, 3}, {0, 0, 0}};
    final RREFMatrix testMatrix = new RREFMatrix(new Matrix("Test", data, null));

    assertTrue("Matrix Reduction gives incorrect result",
        Arrays.deepEquals(dataResult, testMatrix.getData()));
  }

  @Test
  public void testMatrixReduceLargeValue() {
    double[][] data =
        { {1586, 7546, 85746, 57564}, {756, 374, 385, 0}, {8765, 123, 765, 234},
            {5736, 4736, 27364, 5}};
    double[][] dataResult = { {1, 0, 0, 0}, {0, 1, 0, 0}, {0, 0, 1, 0}, {0, 0, 0, 1}};
    final RREFMatrix testMatrix = new RREFMatrix(new Matrix("Test", data, null));

    assertTrue("Matrix Reduction gives incorrect result",
        Arrays.deepEquals(dataResult, testMatrix.getData()));
  }

  @Test
  public void testERO1() {
    double[][] data = { {1, 2, 1}, {-2, -3, 1}, {3, 5, 0}};
    double[][] dataResult = { {1, 2, 1}, {3, 5, 0}, {-2, -3, 1}};
    final Matrix testMatrix = new Matrix("Test", data, null);
    assertTrue("Matrix ERO1 gives incorrect result",
        Arrays.deepEquals(dataResult, RREFMatrix.ERO1(testMatrix, 2, 1).getData()));
  }

  @Test
  public void testERO2Negative() {
    double[][] data = { {1, 2, 1}, {-2, -3, 1}, {3, 5, 0}};
    double[][] dataResult = { {1, 2, 1}, {2, 3, -1}, {3, 5, 0}};
    final Matrix testMatrix = new Matrix("Test", data, null);
    assertTrue("Matrix ERO2 gives incorrect result",
        Arrays.deepEquals(dataResult, RREFMatrix.ERO2(testMatrix, 1, -1).getData()));
  }

  @Test
  public void testERO3Negative() {
    double[][] data = { {1, 2, 1}, {-2, -3, 1}, {3, 5, 0}};
    double[][] dataResult = { {1, 2, 1}, {-8, -13, 1}, {3, 5, 0}};
    final Matrix testMatrix = new Matrix("Test", data, null);
    assertTrue("Matrix ERO3 gives incorrect result",
        Arrays.deepEquals(dataResult, RREFMatrix.ERO3(testMatrix, 1, 2, -2).getData()));
  }

  @Test
  public void testStepOne() {
    double[][] data = { {1, 2, 1}, {-2, 0, 1}, {3, 5, 0}};
    double[][] dataResult = { {1, 2, 1}, {3, 5, 0}, {-2, 0, 1}};
    final Matrix testMatrix = new Matrix("Test", data, null);
    RREFMatrix.stepOne(testMatrix, 1, 1);
    assertTrue("Matrix stepOne gives incorrect result",
        Arrays.deepEquals(dataResult, testMatrix.getData()));
  }

  @Test
  public void testStepOne2() {
    double[][] data = { {1.0, 0.0, -5.0}, {0.0, 1.0, 3.0}, {0.0, 0.0, 0.0}};
    double[][] dataResult = { {1.0, 0.0, -5.0}, {0.0, 1.0, 3.0}, {0.0, 0.0, 0.0}};
    final Matrix testMatrix = new Matrix("Test", data, null);
    RREFMatrix.stepOne(testMatrix, 2, 2);
    assertTrue("Matrix stepOne gives incorrect result",
        Arrays.deepEquals(dataResult, testMatrix.getData()));
  }
}
