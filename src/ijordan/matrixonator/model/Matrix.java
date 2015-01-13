package ijordan.matrixonator.model;

import java.time.LocalDate;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

//Required for Save/Load
import java.io.File;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;

public class Matrix {

	private StringProperty name;
	private final IntegerProperty numRows;
	private final IntegerProperty numCols;
	private final ObjectProperty<LocalDate> createdDate;
	private final ObjectProperty<double[][]> data;

	/**
	 * Default constructor. Creates an empty, unnamed matrix.
	 * 
	 * @throws Exception
	 */
	public Matrix() {
		this(null, new double[0][0], null);
	}

	/**
	 * Constructor with some initial data.
	 * 
	 * @param name
	 * @param data
	 * @throws Exception
	 */
	public Matrix(String name, double[][] data, LocalDate date) {
		this.name = new SimpleStringProperty(name);
		this.data = new SimpleObjectProperty<double[][]>(data);

		this.numRows = new SimpleIntegerProperty(data.length);
		this.numCols = new SimpleIntegerProperty(data[0].length);
		if (date != null) {
			this.createdDate = new SimpleObjectProperty<LocalDate>(date);
		} else {
			this.createdDate = new SimpleObjectProperty<LocalDate>(LocalDate.now());
		}
	}

	/**
	 * Constructor to load from file
	 * 
	 * @param Filename
	 */
	public Matrix(String filename) {
		// Checking for location settings are shown
		if (!filename.startsWith("./")) {
			filename = "./" + filename;
		}

		File matrixFile = new File(filename);

		//Properties from file
		String name = "";
		LocalDate date = null;
		int Rows = 0;
		int Cols = 0;
		double[][] matrixData = null;

		try {			
			//Attempting to read in file given
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
			e.printStackTrace();
		}

		// Adding data to the class
		this.name = new SimpleStringProperty(name);
		this.data = new SimpleObjectProperty<double[][]>(matrixData);

		this.numRows = new SimpleIntegerProperty(Rows);
		this.numCols = new SimpleIntegerProperty(Cols);
		this.createdDate = new SimpleObjectProperty<LocalDate>(
				LocalDate.from(date));
	}

	// Getters/Setters
	// name
	public String getName() {
		return name.get();
	}

	public void setName(String name) {
		this.name.set(name);
	}

	public StringProperty nameProperty() {
		return name;
	}

	// numRows
	public int getNumRows() {
		return numRows.get();
	}

	public IntegerProperty numRowsProperty() {
		return numRows;
	}

	// numCols
	public int getNumCols() {
		return numCols.get();
	}

	public IntegerProperty numColsProperty() {
		return numCols;
	}

	// createdDate
	public LocalDate getCreatedDate() {
		return createdDate.get();
	}

	public ObjectProperty<LocalDate> createdDateProperty() {
		return createdDate;
	}

	// data
	public double[][] getData() {
		return data.get();
	}

	/*
	 * Row and Column getters This assumes that a Martix data is stored as
	 * [row][col] format
	 */
	// Returns a given row of Matrix
	public double[] getRow(int row) {
		return data.get()[row];
	}

	// Returns a given cell of the matrix
	public double getCell(int row, int col) {
		return data.get()[row][col];
	}

	/*
	 * ------- IO Operations
	 */

	/*
	 * Saves Matrix to file
	 * 
	 * Saves Matrix as plain text (for now)
	 * 
	 * File Format: - Matrix Name - Matrix Date - Matrix NumRows/Cols - Matrix
	 * Data (Row per line, Cols split with ,)
	 */

	/**
	 * save
	 * 
	 * @returns True on success, false otherwise
	 */
	public boolean save() {
		String[] buffer = new String[(this.numRows.get() + 3)]; // Size required
																// for data

		// Adds title information
		buffer[0] = this.name.get();
		buffer[1] = this.createdDate.get().toString();
		buffer[2] = this.numRows.get() + "," + this.numCols.get();

		for (int i = 3; i < (this.numRows.get() + 3); ++i) {
			// For each row, we add a new line and put each value in a string
			// seperated by ,
			StringBuilder line = new StringBuilder();
			double[] row = this.getRow(i - 3);

			for (double val : row) {
				line.append(val);
				line.append(",");
			}

			buffer[i] = line.toString();
		}

		// Actual IO Operation in try
		try {
			// "./" means to save in the local application directory
			File matrixFile = new File("./" + this.name.get() + ".matrix");

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

	/**
	 * load
	 * 
	 * @param Matrix file to load
	 * @returns True on success, false if error occurs
	 */
	public boolean load(String filename) {

		// Checking for location settings are shown
		if (!filename.startsWith("./")) {
			filename = "./" + filename;
		}

		File matrixFile = new File(filename);

		if (!matrixFile.exists()) {
			return false;
		}

		try {
			FileReader fr = new FileReader(matrixFile);
			BufferedReader br = new BufferedReader(fr);

			String name = br.readLine();
			LocalDate date = LocalDate.parse(br.readLine());
			String[] NumRowsCols = br.readLine().split(",");
			int Rows = Integer.parseInt(NumRowsCols[0]);
			int Cols = Integer.parseInt(NumRowsCols[1]);

			double[][] matrixData = new double[Rows][Cols];

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

			//MOVE SAVE/LOAD METHODS TO CONTROLLER, 
			//SINCE THIS MATRIX MUST BE ADDED TO
			//THE OBSERVABLE LIST.
			
			// Adding data to the class
			/*this.setName(name);
			this.setCreatedDate(LocalDate.from(date));
			this.setNumRows(Rows);
			this.setNumCols(Cols);
			this.data.setValue(matrixData);*/

			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Checks whether it is possible to multiply two matrices together.
	 *
	 * @param A
	 * @param B
	 * @return
	 */
	public static Boolean checkMultCompatibility(Matrix A, Matrix B) {
		if (A.getData().length != B.getData()[0].length) {
			return false;
		}
		return true;
	}

	/**
	 * Uses naive method to calculate the product of two matrices.
	 *
	 * @param A
	 * @param B
	 * @return
	 */
	public static Matrix multiplyMatrices(Matrix A, Matrix B) {
		if (checkMultCompatibility(A, B)) {
			double[][] data = new double[A.getNumRows()][B.getNumCols()];
			int i = 0;
			int j = 0;
			int k = 0;
			while (i < A.getNumRows()) {
				while (j < B.getNumCols()) {
					while (k < B.getNumRows()) {
						data[i][j] = data[i][j] + A.getData()[i][k]
								* B.getData()[k][j];
						k += 1;
					}
					j += 1;
					k = 0;
				}
				i += 1;
				j = 0;
			}
			return (new Matrix(null, data, null));
		}
		throw new IllegalArgumentException("Matrices are not compatible");
	}

	/**
	 * Static method that adds two matrices A, and B. If the matrices cannot be
	 * added, an IllegalArgumentException is thrown.
	 *
	 * @param A
	 * @param B
	 * @return
	 */
	public static Matrix addMatrices(Matrix A, Matrix B) {
		if (A.getNumRows() == B.getNumRows()
				&& A.getNumCols() == B.getNumCols()) {
			double[][] data = new double[A.getNumRows()][A.getNumCols()];
			for (int i = 0; i < A.getNumRows(); i++) {
				for (int j = 0; j < A.getNumCols(); j++) {
					data[i][j] = A.getData()[i][j] + B.getData()[i][j];
				}
			}
			return (new Matrix(null, data, null));
		} else {
			throw new IllegalArgumentException("Matrices are not compatible.");
		}
	}

	// THIS SECTION'S METHODS ARE ALL FOR REDUCING A MATRIX
	/**
	 * Returns the reduced row echelon form of this matrix.
	 *
	 * @return
	 */
	public Matrix reducedEchelonForm() {
		double[][] data = new double[this.getNumRows()][this.getNumCols()];
		return null;
		
	}

}
