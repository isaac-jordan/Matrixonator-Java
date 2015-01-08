package ijordan.matrixonatorTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import ijordan.matrixonator.model.*;

import org.junit.Test;

public class MatrixTest {
	
	//---------------------------------------------------
	//MATRIX CREATION
	//---------------------------------------------------
	
	@Test //Checks to see if a Martix is created properly
	// Revision 1: Changed how to test for data
	public void testMatrixCreation() {
		double[][] data = new double[5][2];
		data[1][1] = 5.2;

		final Matrix testMatrix = new Matrix("Test", data);
		assertEquals("Created matrix has inaccurate number of rows", testMatrix.getNumRows(), 5);
		assertEquals("Created matrix has inaccurate number of columns", testMatrix.getNumCols(), 2);
		assertTrue("Created matrix has inaccurate data", testMatrix.getCell(1, 1) == 5.2);
	}
	
	@Test //Checks if we still have the right row/col orientation and that the getters work 
	public void testMatrixRowCol(){
		double[][] data = new double[4][4];
		data[1][3] = 3.0;
	 	data[3][1] = 1.0;
		
		Matrix testMatrix = new Matrix("Test", data);
		 
		assertTrue("Matrix has row/col conflict.", testMatrix.getCell(1, 3) == 3.0);
		assertTrue("Matrix has row/col conflict.", testMatrix.getCell(3, 1) == 1.0);
	}
	
	
	@Test //Checks if we have our row and col getters working properly
	public void testMartixRol2(){	
							//C1   C2   C3
		double[][] data = {  {1.0, 1.0, 1.0} ,	//R1
				             {1.0, 2.0, 3.0} ,	//R2
				             {1.0, 3.0, 0.9} };	//R3
		
		Matrix testMatrix = new Matrix("Test", data);
		
		//Get the first row and column
		double[] Row1 = testMatrix.getRow(0);
		
		boolean RowFails = false;
		
		//Check that each value in the Row only has 1.0 in it
		for(double val : Row1)
		{ if (val != 1.0) { RowFails = true; break;}}
		
		//If either fails, we've failed this test!
		assertFalse("Matrix get Row has invalid members", RowFails);
	}
	
	
	@Test //Tests that matrix will be saved properly
	public void testMatrixSave()
	{
		double[][] data  = { { 1,2,3,4,5 } ,
				             { 6,7,8,9,10} };
		
		Matrix testMatrix = new Matrix("testMatrixSave", data);
			
		assertTrue("Matrix did not save successfully", testMatrix.save());
	}


}
