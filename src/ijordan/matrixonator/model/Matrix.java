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

        
        this.numRows = new SimpleIntegerProperty(data.length);
        this.numCols = new SimpleIntegerProperty(data[0].length); //Assumes matrices have 1 entry
        this.createdDate = new SimpleObjectProperty<LocalDate>(LocalDate.now());
    }
    
    /**
     * Checks whether it is possible to multiply two matrices together.
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
     * @param A
     * @param B
     * @return
     */
    public static Matrix multiplyMatrices(Matrix A, Matrix B) {
    	if (checkMultCompatibility(A,B)) {
    		double [][] data = new double[A.getNumRows()][B.getNumCols()];
    		int i = 0;
    		int j = 0;
    		int k = 0;
    		while (i < A.getNumRows()) {
                while (j < B.getNumCols()) {
                    while (k < B.getNumRows()) {
                        data[i][j] = data[i][j] + A.getData()[i][k] * B.getData()[k][j];
                        k += 1;
                    }
                    j += 1;
                    k = 0;
                }
                i += 1;
                j = 0;
    		}
    		return (new Matrix(null, data));
    	}
		throw new IllegalArgumentException("Matrices are not compatible");
    	
    }
    
    /**
     * Static method that adds two matrices A, and B. 
     * If the matrices cannot be added, an IllegalArgumentException is thrown.
     * @param A
     * @param B
     * @return
     */
	public static Matrix addMatrices(Matrix A, Matrix B) {
		if (A.getNumRows() == B.getNumRows() && A.getNumCols() == B.getNumCols()) {
			double[][] data = new double[A.getNumRows()][A.getNumCols()];
			for (int i = 0; i < A.getNumRows(); i++) {
				for (int j = 0; j < A.getNumCols(); j++) {
					data[i][j] = A.getData()[i][j] + B.getData()[i][j];
				}
			}
			return (new Matrix(null, data));
			
		} else {
			throw new IllegalArgumentException("Matrices are not compatible.");
		}
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
	
	//data
	public double[][] getData() {
		return data.get();
	}
	

	

}
