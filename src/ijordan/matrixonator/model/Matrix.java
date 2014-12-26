package ijordan.matrixonator.model;

import java.time.LocalDate;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Matrix {
	
	private final StringProperty name;
    private final IntegerProperty numRows;
    private final IntegerProperty numCols;
    private final ObjectProperty<LocalDate> createdDate;
    private final ObjectProperty<double[][]> data;

    /**
     * Default constructor.
     * Creates an empty, unnamed matrix.
     */
    public Matrix() {
    	this(null, new double[0][0]);
    }

    /**
     * Constructor with some initial data.
     * 
     * @param name
     * @param data
     */
    public Matrix(String name, double[][] data) {
        this.name = new SimpleStringProperty(name);
        this.data = new SimpleObjectProperty<double[][]>(data);

        // Some initial dummy data, just for convenient testing.
        this.numRows = new SimpleIntegerProperty(5);
        this.numCols = new SimpleIntegerProperty(6);
        this.createdDate = new SimpleObjectProperty<LocalDate>(LocalDate.now());
    }

    //name
	public String getName() {
		return name.get();
	}

	public void setName(String name) {
		this.name.set(name);
	}
	
	public StringProperty nameProperty() {
		return name;
	}
	
	//numRows
	public int getNumRows() {
		return numRows.get();
	}

	public void setNumRows(int numRows) {
		this.numRows.set(numRows);
	}
	
	public IntegerProperty numRowsProperty() {
		return numRows;
	}
	
	//numCols
	public int getNumCols() {
		return numCols.get();
	}

	public void setNumCols(int numCols) {
		this.numCols.set(numCols);
	}
	
	public IntegerProperty numColsProperty() {
		return numCols;
	}
	
	//createdDate
	public LocalDate getCreatedDate() {
		return createdDate.get();
	}

	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate.set(createdDate);
	}
	
	public ObjectProperty<LocalDate> createdDateProperty() {
		return createdDate;
	}
	

	

}
