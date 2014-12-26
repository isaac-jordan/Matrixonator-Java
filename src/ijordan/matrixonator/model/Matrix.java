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

    /**
     * Default constructor.
     */
    public Matrix() {
        this(null);
    }

    /**
     * Constructor with some initial data.
     * 
     * @param firstName
     * @param lastName
     */
    public Matrix(String name) {
        this.name = new SimpleStringProperty(name);

        // Some initial dummy data, just for convenient testing.
        this.numRows = new SimpleIntegerProperty(5);
        this.numCols = new SimpleIntegerProperty(6);
        this.createdDate = new SimpleObjectProperty<LocalDate>(LocalDate.of(1999, 2, 21));
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
