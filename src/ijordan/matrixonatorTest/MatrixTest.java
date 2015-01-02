package ijordan.matrixonatorTest;

import static org.junit.Assert.assertEquals;
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

}
