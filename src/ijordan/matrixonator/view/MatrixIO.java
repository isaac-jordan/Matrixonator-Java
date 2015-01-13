package ijordan.matrixonator.view;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

import ijordan.matrixonator.model.Matrix;

/* MatrixIO
 * --------
 * Provides static methods to deal with load and saving of matrices
 * 
 * Author: Ewan McCartney
 */
public class MatrixIO {

	//Flag if there has been an error when creating directories
	private static boolean dontSave = false;
	
	/**
	 * Called from startup check to stop IO operations if no directory structure
	 */
	public static void setSaveFlag() { dontSave = true; }
	
	/*
	 * RESET METHOD FROM TESTS. DO NOT CALL FROM APPLICATION
	 */
	public static void resetSaveFlag() { dontSave = false; }
	
	
	/**
	 * Load Matrix from File
	 * 
	 * @param filename (Local [NAME].matrix. DO NOT ADD PATH)
	 * @return Loaded Matrix
	 * @throws Exception
	 *             (FileNotFound for missing file, SaveDisabledFlag or general exception during
	 *             IO)
	 */
	public static Matrix load(String filename) throws Exception {

		if (dontSave) { return null; } //Checking incase the working directories haven't worked properly
		
		filename = getWorkingDir() + MatrixDir + "/" + filename;

		File matrixFile = new File(filename);

		if (!matrixFile.exists()) {
			throw new FileNotFoundException();
		}

		// Properties from file
		String name = "";
		LocalDate date = null;
		int Rows = 0;
		int Cols = 0;
		double[][] matrixData = null;

		try {
			// Attempting to read in file given
			FileReader fr = new FileReader(matrixFile);
			BufferedReader br = new BufferedReader(fr);

			name = br.readLine();
			date = LocalDate.parse(br.readLine());
			String[] NumRowsCols = br.readLine().split(",");
			Rows = Integer.parseInt(NumRowsCols[0]);
			Cols = Integer.parseInt(NumRowsCols[1]);

			matrixData = new double[Rows][Cols];

			for (int i = 0; i < Rows; ++i) {
				String row = br.readLine();
				String[] Values = row.split(",");
				int Col = 0;

				for (String val : Values) {
					matrixData[i][Col] = Double.parseDouble(val);
					++Col;
				}
			}

			br.close();
		} catch (Exception e) {
			throw e;
		}

		return new Matrix(name, matrixData, date);
	}

	/**
	 * Save Matrix to file
	 * 
	 * @param matrix
	 * @return True on success, false otherwise
	 */

	/*
	 * Saves Matrix as plain text (for now)
	 * 
	 * File Format: - Matrix Name - Matrix Date - Matrix NumRows/Cols - Matrix
	 * Data (Row per line, Cols split with ,)
	 */

	public static boolean save(Matrix matrix) {
		
		if (dontSave) { return false; }	//Checking for Save flag on startup
		
		//Size required for data
		String[] buffer = new String[(matrix.getNumRows() + 3)]; 
		
		// Adds title information
		buffer[0] = matrix.getName();
		buffer[1] = matrix.getCreatedDate().toString();
		buffer[2] = matrix.getNumRows() + "," + matrix.getNumCols();

		for (int i = 3; i < (matrix.getNumRows() + 3); ++i) {
			// For each row, we add a new line and put each value in a string
			// seperated by ,
			StringBuilder line = new StringBuilder();
			double[] row = matrix.getRow(i - 3);

			for (double val : row) {
				line.append(val);
				line.append(",");
			}

			buffer[i] = line.toString();
		}

		// Actual IO Operation in try
		try {
			// "./" means to save in the local application directory
			File matrixFile = new File(getWorkingDir() + MatrixDir + "/" + buffer[0] + ".matrix");
			
			if (!matrixFile.exists()) {
				matrixFile.createNewFile();
			}

			FileWriter fw = new FileWriter(matrixFile.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);

			for (String line : buffer) {
				bw.append(line + "\n");
			}

			bw.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/*
	 * IO Helper Methods
	 */
	private static final String MatrixDir = "/Matrixonator/Matrix";
	private static final String LocalDir = "/Matrixonator";

	/**
	 * @return The path to application working directory
	 */
	private static String getWorkingDir() {
		return System.getProperty("user.dir");
	}

	// Checks both directories are there and attempts to make them. Throw the
	// non-critical exception
	public static void checkDirectories() throws DirectoryNotCreatedException {
		File BaseDirectory = new File(getWorkingDir() + LocalDir);
		if (!BaseDirectory.exists()) {

			try {
				if (!BaseDirectory.mkdir()) {
					throw new DirectoryNotCreatedException(false);
				}
			} catch (SecurityException e) {
				e.printStackTrace();
				throw new DirectoryNotCreatedException(true);
			}
		}

		File MatrixDirectory = new File(getWorkingDir() + MatrixDir);
		if (!MatrixDirectory.exists()) {

			try {
				if (!MatrixDirectory.mkdir()) {
					throw new DirectoryNotCreatedException(false);
				}
			} catch (SecurityException e) {
				e.printStackTrace();
				throw new DirectoryNotCreatedException(true);
			}
		}
	}
}
