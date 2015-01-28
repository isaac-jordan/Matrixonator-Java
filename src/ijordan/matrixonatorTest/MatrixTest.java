package ijordan.matrixonatorTest;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.Arrays;

import ijordan.matrixonator.model.*;
import ijordan.matrixonator.view.MatrixIO;

import org.junit.Test;

public class MatrixTest {

  /*
   * -------------------------- Matrix Creation and Methods Tests ---
   */

  @Test
  // Checks to see if a Matrix is created properly
  // Revision 1: Changed how to test for data
  public void testMatrixCreation() {
    double[][] data = new double[5][2];
    data[1][1] = 5.2;

    final Matrix testMatrix = new Matrix("Test", data, null);
    assertEquals("Created matrix has inaccurate number of rows", testMatrix.getNumRows(), 5);
    assertEquals("Created matrix has inaccurate number of columns", testMatrix.getNumCols(), 2);
    assertTrue("Created matrix has inaccurate data", testMatrix.getCell(1, 1) == 5.2);
  }

  @Test
  // Checks if we still have the right row/col orientation and that the
  // getters work
  public void testMatrixRowCol() {
    double[][] data = new double[4][4];
    data[1][3] = 3.0;
    data[3][1] = 1.0;

    Matrix testMatrix = new Matrix("Test", data, null);
    assertTrue("Matrix has row/col conflict.", testMatrix.getCell(1, 3) == 3.0);
    assertTrue("Matrix has row/col conflict.", testMatrix.getCell(3, 1) == 1.0);
  }

  @Test
  // Checks if we have our row and col getters working properly
  public void testMartixRowCol2() {
    // C1 C2 C3
    double[][] data = { {1.0, 1.0, 1.0}, // R1
        {1.0, 2.0, 3.0}, // R2
        {1.0, 3.0, 0.9}}; // R3

    Matrix testMatrix = new Matrix("Test", data, null);

    // Get the first row and column
    double[] Row1 = testMatrix.getRow(0);
    boolean RowFails = false;

    // Check that each value in the Row only has 1.0 in it
    for (double val : Row1) {
      if (val != 1.0) {
        RowFails = true;
        break;
      }
    }

    // If either fails, we've failed this test!
    assertFalse("Matrix get Row has invalid members", RowFails);
  }

  @Test
  // Tests that matrix will be saved properly
  public void testMatrixSaveLoad() {
    double[][] data = { {1, 2, 3, 4, 5}, {6, 7, 8, 9, 10}};
    Matrix testMatrix = new Matrix("testMatrixSave", data, LocalDate.now());
    assertTrue("Matrix did not save successfully", MatrixIO.save(testMatrix));

    Matrix testMatrixL = null;

    // We fail in the try incase this can't load. Adds extra catch if the
    // save isn't working
    try {
      testMatrixL = MatrixIO.loadMatrix("testMatrixSave.matrix");
    } catch (Exception e) {
      e.printStackTrace();
      fail("Exception was thrown");
    }
    assertTrue("Matrix name data was invalid", testMatrixL.getName().equals("testMatrixSave"));
    assertTrue("Matrix creation date was wrong",
        testMatrixL.getCreatedDate().equals(LocalDate.now()));
    assertTrue("Matrix row count is wrong", testMatrixL.getNumRows() == 2);
    assertTrue("Matrix col count is wrong", testMatrixL.getNumCols() == 5);
    assertTrue("Matrix data in invalid", Arrays.deepEquals(data, testMatrix.getData()));
				Arrays.deepEquals(data, testMatrix.getData());
  }

  @Test
  // Tests for saving falg within Matrix IO
  public void testMatrixIOwithFlag() {
    MatrixIO.setSaveFlag(); // Simulating that the setup function hasn't worked correctly
								// worked correctly

    Matrix testMatrix = new Matrix(null, new double[1][1], LocalDate.now());

    // Attempting to load. Expected to return null
    try {
      testMatrix = MatrixIO.loadMatrix("thisisnevergoingtobeusedasafilename.matrix");
      fail("Matrix should not have loaded anything");
    } catch (Exception e) {
      assertTrue(
          "Exception should occur here",
          e.getMessage().contains(
              "Save is currently disabled due to Matrixonator not having working directories"));
    }
    assertTrue("Matrix should not contain anything", testMatrix.getName() == null);
    assertFalse("Matrix should not be saved!", MatrixIO.save(testMatrix));

    // Resetting the flag for testing purposed
    MatrixIO.resetSaveFlag();
  }

  @Test
  // Checks we can load and save RREF matrices with the same code
  public void testSaveLoadRREF() {
    double[][] data = { {1, 2, 3, 4, 5}, {6, 7, 8, 9, 10}};
    RREFMatrix testMatrix = new RREFMatrix(new Matrix("testMatrixSave", data, LocalDate.now()));
    assertTrue("Matrix did not save successfully", MatrixIO.save(testMatrix));

    RREFMatrix testMatrixL = null;

    // We fail in the try incase this can't load. Adds extra catch if the
    // save isn't working
    try {
      testMatrixL = MatrixIO.loadRREFMatrix("RREFtestMatrixSave.matrix");
    } catch (Exception e) {
      e.printStackTrace();
      fail("Exception was thrown");
    }
    assertTrue("Matrix name data was invalid", testMatrixL.getName().equals("RREF testMatrixSave"));
    assertTrue("Matrix creation date was wrong",
        testMatrixL.getCreatedDate().equals(LocalDate.now()));
    assertTrue("Matrix row count is wrong", testMatrixL.getNumRows() == 2);
    assertTrue("Matrix col count is wrong", testMatrixL.getNumCols() == 5);
    assertTrue("Matrix data in invalid",
        Arrays.deepEquals(testMatrix.getData(), testMatrixL.getData()));
  }

  /*
   * ------------ Matrix Arithmetic Tests ----------------------------------
   */

  @Test
  public void testMatrixMultiplicationBaseCase() {
    double[][] dataFirst = { {0, 0, 0}, {0, 0, 0}};
    double[][] dataSecond = { {0, 0}, {0, 0}, {0, 0}};
    double[][] dataResult = { {0, 0}, {0, 0}};
    final Matrix testMatrix1 = new Matrix("Test1", dataFirst, null);
    final Matrix testMatrix2 = new Matrix("Test2", dataSecond, null);
    final Matrix testMatrixResult = new Matrix("TestResult", dataResult, null);
    assertTrue("Matrices should be compatible",
        Matrix.checkMultCompatibility(testMatrix1, testMatrix2));

    assertTrue(
        "Matrix Multiplication gives incorrect result",
        Arrays.deepEquals(testMatrixResult.getData(),
            Matrix.multiplyMatrices(testMatrix1, testMatrix2).getData()));
  }

  @Test
  public void testMatrixMultiplicationSimple() {
    double[][] dataFirst = { {1, 2, 3}, {4, 5, 6}};
    double[][] dataSecond = { {7, 8}, {9, 10}, {11, 12}};
    double[][] dataResult = { {58, 64}, {139, 154}};
    final Matrix testMatrix1 = new Matrix("Test1", dataFirst, null);
    final Matrix testMatrix2 = new Matrix("Test2", dataSecond, null);
    final Matrix testMatrixResult = new Matrix("TestResult", dataResult, null);

    assertTrue("Matrices should be compatible",
        Matrix.checkMultCompatibility(testMatrix1, testMatrix2));

    assertTrue(
        "Matrix Multiplication gives incorrect result",
        Arrays.deepEquals(testMatrixResult.getData(),
            Matrix.multiplyMatrices(testMatrix1, testMatrix2).getData()));
  }

  @Test
  public void testMatrixMultiplicationBig() {
    double[][] dataFirst = { {1586, 7546, 85746, 57564}, {756, 374, 385, 0}, {8765, 123, 765, 234}};
    double[][] dataSecond =
        { {6543, 7896, 9876}, {76546, 765467, 4657}, {34765, 28452, 9876789}, {897655, 876, 5}};
    double[][] dataResult =
        { {55241565424.0, 8278808294.0, 846946242472.0}, {46959237, 303208054, 3811771739.0},
            {303411048, 185331645, 7642880706.0}};
    final Matrix testMatrix1 = new Matrix("Test1", dataFirst, null);
    final Matrix testMatrix2 = new Matrix("Test2", dataSecond, null);
    final Matrix testMatrixResult = new Matrix("TestResult", dataResult, null);

    assertTrue("Matrices should be compatible",
        Matrix.checkMultCompatibility(testMatrix1, testMatrix2));

    assertTrue(
        "Matrix Multiplication gives incorrect result",
        Arrays.deepEquals(testMatrixResult.getData(),
            Matrix.multiplyMatrices(testMatrix1, testMatrix2).getData()));

  }

  @Test
  public void testMatrixMultCompatibility() {
    double[][] dataFirst = { {1, 2, 3}, {1, 2, 3}, {12, 3, 4}};
    double[][] dataSecond = { {2, 3}, {3, 7}};
    final Matrix testMatrix1 = new Matrix("Test1", dataFirst, null);
    final Matrix testMatrix2 = new Matrix("Test2", dataSecond, null);

    assertFalse("Matrices should not be compatible",
        Matrix.checkMultCompatibility(testMatrix1, testMatrix2));

    assertFalse("Matrices should not be compatible",
        Matrix.checkMultCompatibility(testMatrix2, testMatrix1));

  }

  @Test
  public void testMatrixAddBaseCase() {
    double[][] dataFirst = { {0, 0}, {0, 0}};
    double[][] dataSecond = { {0, 0}, {0, 0}};
    double[][] dataResult = { {0, 0}, {0, 0}};
    final Matrix testMatrix1 = new Matrix("Test1", dataFirst, null);
    final Matrix testMatrix2 = new Matrix("Test2", dataSecond, null);
    final Matrix testMatrixResult = new Matrix("TestResult", dataResult, null);
    assertTrue("Matrix Addition gives incorrect result", Arrays.deepEquals(
        testMatrixResult.getData(), Matrix.addMatrices(testMatrix1, testMatrix2).getData()));
  }

  @Test
  public void testMatrixAddSimple() {
    double[][] dataFirst = { {1, 2}, {2, 3}};
    double[][] dataSecond = { {4, 2}, {3, 5}};
    double[][] dataResult = { {5, 4}, {5, 8}};
    final Matrix testMatrix1 = new Matrix("Test1", dataFirst, null);
    final Matrix testMatrix2 = new Matrix("Test2", dataSecond, null);
    final Matrix testMatrixResult = new Matrix("TestResult", dataResult, null);
    assertTrue("Matrix Addition gives incorrect result", Arrays.deepEquals(
        testMatrixResult.getData(), Matrix.addMatrices(testMatrix1, testMatrix2).getData()));
  }

  @Test
  public void testMatrixAddBig() {
    double[][] dataFirst = { {1586, 7546, 85746, 57564}, {756, 374, 385, 0}, {8765, 123, 765, 234}};
    double[][] dataSecond =
        { {6543, 7896, 9876, 156}, {76546, 765467, 4657, 573},
            {34765, 28452, 9876789, 475645323234.0}};
    double[][] dataResult =
        { {8129, 15442, 95622, 57720}, {77302, 765841, 5042, 573},
            {43530, 28575, 9877554, 475645323468.0}};
    final Matrix testMatrix1 = new Matrix("Test1", dataFirst, null);
    final Matrix testMatrix2 = new Matrix("Test2", dataSecond, null);
    final Matrix testMatrixResult = new Matrix("TestResult", dataResult, null);

    assertTrue("Matrix Addition gives incorrect result", Arrays.deepEquals(
        testMatrixResult.getData(), Matrix.addMatrices(testMatrix1, testMatrix2).getData()));

  }

  @Test
  public void testMatrixDeterminantSimple() {
    double[][] dataFirst = { {1, 2, 3}, {0, -4, 1}, {0, 3, -1}};
    double dataResult = 1;
    final Matrix testMatrix1 = new Matrix("Test1", dataFirst, null);
    assertTrue("Matrix Addition gives incorrect result", testMatrix1.determinant() == dataResult);
  }

  @Test
  public void testMatrixDeterminantSimple2() {
    double[][] dataFirst = { {1, 2, 3}, {3, 2, 1}, {2, 1, 3}};
    double dataResult = -12;
    final Matrix testMatrix1 = new Matrix("Test1", dataFirst, null);
    assertTrue("Matrix Addition gives incorrect result", testMatrix1.determinant() == dataResult);
  }

  @Test
  public void testMatrixDeterminantSmall() {
    double[][] dataFirst = { {1, 2}, {0, 3}};
    double dataResult = 3;
    final Matrix testMatrix1 = new Matrix("Test1", dataFirst, null);
    assertTrue("Matrix Addition gives incorrect result", testMatrix1.determinant() == dataResult);
  }

  @Test
  public void testMatrixPowerBig() {
    double[][] dataFirst = { {1, 2, -3}, {4, 8, 1}, {0, 3, 5}};
    double[][] dataResult =
        { {-654572067, -1330179531, 26989594}, {11511254796.0, 21852059469.0, -3420681349.0},
            {9447742572.0, 18081183669.0, -2527619248.0}};
    final Matrix testMatrix = new Matrix("Test", dataFirst, null);
    assertTrue("Matrix raised to power gives incorrect result",
        Arrays.deepEquals(testMatrix.toPower(11).getData(), dataResult));
  }

  @Test
  public void testMatrixPowerSmall() {
    double[][] dataFirst = { {1, 2}, {2, 1}};
    double[][] dataResult = { {88573, 88574}, {88574, 88573}};
    final Matrix testMatrix = new Matrix("Test", dataFirst, null);
    assertTrue("Matrix raised to power gives incorrect result",
        Arrays.deepEquals(testMatrix.toPower(11).getData(), dataResult));
  }

  @Test
  public void testMatrixInverse() {
    double[][] dataFirst = { {1, 2, -3}, {4, 8, 1}, {0, 3, 5}};
    double[][] dataResult =
        { {-37.0 / 39, 19.0 / 39, -2.0 / 3}, {20.0 / 39, -5.0 / 39, 1.0 / 3},
            {-4.0 / 13, 1.0 / 13, 0}};
    final Matrix testMatrix = new Matrix("Test", dataFirst, null);
    final Matrix testMatrix2 = new Matrix("Test", dataResult, null);
    assertTrue("Matrix inverse gives incorrect result",
        Arrays.deepEquals(testMatrix.inverse().getData(), testMatrix2.normalise().getData()));
  }

  @Test
  public void testMatrixNoInverse() {
    double[][] dataFirst = { {1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
    final Matrix testMatrix = new Matrix("Test", dataFirst, null);
    assertTrue("Matrix inverse gives incorrect result", testMatrix.inverse() == null);
  }

  @Test
  public void testMatrixTranspose() {
    double[][] dataFirst = { {1, 2, -3}, {4, 8, 1}, {0, 3, 5}};
    double[][] dataResult = { {1, 4, 0}, {2, 8, 3}, {-3, 1, 5}};
    final Matrix testMatrix = new Matrix("Test", dataFirst, null);
    assertTrue("Matrix inverse gives incorrect result",
        Arrays.deepEquals(testMatrix.transpose().getData(), dataResult));
  }

  @Test
  public void testMatrixCofactor() {
    double[][] dataFirst = { {1, 2, -3}, {4, 8, 1}, {0, 3, 5}};
    double[][] dataResult = { {37, -20, 12}, {-19, 5, -3}, {26, -13, 0}};
    final Matrix testMatrix = new Matrix("Test", dataFirst, null);
    assertTrue("Matrix cofactor gives incorrect result",
        Arrays.deepEquals(testMatrix.cofactorMatrix().getData(), dataResult));
  }

  @Test
  public void testMatrixGetRow() {
    double[][] dataFirst = { {1, 2, -3}, {4, 8, 1}, {0, 3, 5}};
    double[] dataResult0 = {1, 2, -3};
    double[] dataResult1 = {4, 8, 1};
    double[] dataResult2 = {0, 3, 5};
    final Matrix testMatrix = new Matrix("Test", dataFirst, null);
    assertTrue("Matrix getRow gives incorrect result",
        Arrays.equals(testMatrix.getRow(0), dataResult0));
    assertTrue("Matrix getRow gives incorrect result",
        Arrays.equals(testMatrix.getRow(1), dataResult1));
    assertTrue("Matrix getRow gives incorrect result",
        Arrays.equals(testMatrix.getRow(2), dataResult2));
  }

  @Test
  public void testMatrixGetCol() {
    double[][] dataFirst = { {1, 2, -3}, {4, 8, 1}, {0, 3, 5}};
    double[] dataResult0 = {1, 4, 0};
    double[] dataResult1 = {2, 8, 3};
    double[] dataResult2 = {-3, 1, 5};
    final Matrix testMatrix = new Matrix("Test", dataFirst, null);
    assertTrue("Matrix getCol gives incorrect result",
        Arrays.equals(testMatrix.getCol(0), dataResult0));
    assertTrue("Matrix getCol gives incorrect result",
        Arrays.equals(testMatrix.getCol(1), dataResult1));
    assertTrue("Matrix getCol gives incorrect result",
        Arrays.equals(testMatrix.getCol(2), dataResult2));
	}

	@Test
	public void testERO1() {
		double[][] data = { { 1, 2, 1 }, { -2, -3, 1 }, { 3, 5, 0 } };
		double[][] dataResult = { { 1, 2, 1 }, { 3, 5, 0 }, { -2, -3, 1 } };
		final Matrix testMatrix = new Matrix("Test", data, null);
		assertTrue("Matrix ERO1 gives incorrect result", Arrays.deepEquals(
				dataResult, RREFMatrix.ERO1(testMatrix, 2, 1).getData()));
  }

	@Test
	public void testERO2Negative() {
		double[][] data = { { 1, 2, 1 }, { -2, -3, 1 }, { 3, 5, 0 } };
		double[][] dataResult = { { 1, 2, 1 }, { 2, 3, -1 }, { 3, 5, 0 } };
		final Matrix testMatrix = new Matrix("Test", data, null);
		assertTrue("Matrix ERO2 gives incorrect result", Arrays.deepEquals(
				dataResult, RREFMatrix.ERO2(testMatrix, 1, -1).getData()));
	}

	@Test
	public void testERO3Negative() {
		double[][] data = { { 1, 2, 1 }, { -2, -3, 1 }, { 3, 5, 0 } };
		double[][] dataResult = { { 1, 2, 1 }, { -8, -13, 1 }, { 3, 5, 0 } };
		final Matrix testMatrix = new Matrix("Test", data, null);
		assertTrue(
				"Matrix ERO3 gives incorrect result",
				Arrays.deepEquals(dataResult,
						RREFMatrix.ERO3(testMatrix, 1, 2, -2).getData()));
	}

	@Test
	public void testStepOne() {
		double[][] data = { { 1, 2, 1 }, { -2, 0, 1 }, { 3, 5, 0 } };
		double[][] dataResult = { { 1, 2, 1 }, { 3, 5, 0 }, { -2, 0, 1 } };
		final Matrix testMatrix = new Matrix("Test", data, null);
		RREFMatrix.stepOne(testMatrix, 1, 1);
		assertTrue("Matrix stepOne gives incorrect result",
				Arrays.deepEquals(dataResult, testMatrix.getData()));
	}

	@Test
	public void testStepOne2() {
		double[][] data = { { 1.0, 0.0, -5.0 }, { 0.0, 1.0, 3.0 },
				{ 0.0, 0.0, 0.0 } };
		double[][] dataResult = { { 1.0, 0.0, -5.0 }, { 0.0, 1.0, 3.0 },
				{ 0.0, 0.0, 0.0 } };
		final Matrix testMatrix = new Matrix("Test", data, null);
		RREFMatrix.stepOne(testMatrix, 2, 2);
		assertTrue("Matrix stepOne gives incorrect result",
				Arrays.deepEquals(dataResult, testMatrix.getData()));
	}

}
