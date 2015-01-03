package ijordan.matrixonatorTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import ijordan.matrixonator.model.Matrix;

import org.junit.Test;

public class MatrixTest {
	
	@Test
    public void testMatrixCreation(){
		double[][] data = new double[5][2];
		data[1][1] = 5.2;
		
        final Matrix testMatrix = new Matrix("Test", data);
        assertEquals("Created matrix has inaccurate number of rows", testMatrix.getNumRows(), 5);
        assertEquals("Created matrix has inaccurate number of columns",testMatrix.getNumCols(), 2);
        assertEquals("Created matrix has inaccurate data", testMatrix.getData(), data);
        
    }
	
	@Test
    public void testMatrixMultiplication(){
		double[][] dataFirst = {{1,2,3},{4,5,6}};
		double[][] dataSecond = {{7,8},{9,10},{11,12}};
		
		double[][] dataResult = {{58,64},{139,154}};
		
        final Matrix testMatrix1 = new Matrix("Test1", dataFirst);
        final Matrix testMatrix2 = new Matrix("Test2", dataSecond);
        final Matrix testMatrixResult = new Matrix("TestResult", dataResult);
        
        assertTrue("Matrices should be compatible", 
        		Matrix.checkMultCompatibility(testMatrix1, testMatrix2));
        
        assertEquals("Matrix Multiplication gives incorrect result", 
        		testMatrixResult.getData(), 
        		Matrix.multiplyMatrices(testMatrix1, testMatrix2).getData());
        
    }

}
