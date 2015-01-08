package ijordan.matrixonator.model;

import java.time.LocalDate;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Matrix {

	private StringProperty name;
	private final IntegerProperty numRows;
	private final IntegerProperty numCols;
	private final ObjectProperty<LocalDate> createdDate;
	private ObjectProperty<double[][]> data;

	/**
	 * Default constructor. Creates an empty, unnamed matrix.
	 * @throws Exception 
	 */
	public Matrix(){
		this(null, new double[0][0]);
	}

	/**
	 * Constructor with some initial data.
	 * 
	 * @param name
	 * @param data
	 * @throws Exception 
	 */
	public Matrix(String name, double[][] data){
		this.name = new SimpleStringProperty(name);
		this.data = new SimpleObjectProperty<double[][]>(data);

		this.numRows = new SimpleIntegerProperty(data.length);
		this.numCols = new SimpleIntegerProperty(data[0].length); 
		this.createdDate = new SimpleObjectProperty<LocalDate>(LocalDate.now());
	}

	//Getters/Setters
	// name
	public String getName() { return name.get(); }
	public void setName(String name) { this.name.set(name); }
	public StringProperty nameProperty() { return name; }

	// numRows
	public int getNumRows() { return numRows.get(); }
	public void setNumRows(int numRows) { this.numRows.set(numRows); }
    public IntegerProperty numRowsProperty() { return numRows; }

	// numCols
	public int getNumCols() { return numCols.get(); }
	public void setNumCols(int numCols) { this.numCols.set(numCols); }
	public IntegerProperty numColsProperty() { return numCols; }

	// createdDate
	public LocalDate getCreatedDate() { return createdDate.get(); }
	public void setCreatedDate(LocalDate createdDate) { this.createdDate.set(createdDate); }
	public ObjectProperty<LocalDate> createdDateProperty() { return createdDate; }

	// data
	public double[][] getData() { return data.get(); }
	
	/* Row and Column getters 
	 * This assumes that a Martix data is stored as [row][col] format
	 */
	//Returns a given row of Matrix
	public double[] getRow(int row) { return getData()[row]; }
	
	//Returns a given cell of the matrix
	public double getCell(int row, int col){ return data.get()[row][col]; }
}
