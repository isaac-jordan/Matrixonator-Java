package ijordan.matrixonator.view;

import org.controlsfx.dialog.Dialogs;

import ijordan.matrixonator.MainApp;
import ijordan.matrixonator.model.Matrix;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class MatrixOverviewController {

	@FXML
    private TableView<Matrix> matrixTable;
    @FXML
    private TableColumn<Matrix, String> nameColumn;
    @FXML
    private TableColumn<Matrix, Integer> numRowsColumn;
    @FXML
    private TableColumn<Matrix, Integer> numColsColumn;

    @FXML
    private Label nameLabel;
    @FXML
    private Label numRowsLabel;
    @FXML
    private Label numColsLabel;
    @FXML
    private Label createdDateLabel;

    // Reference to the main application.
    private MainApp mainApp;

    /**
     * The constructor.
     * The constructor is called before the initialise() method.
     */
    public MatrixOverviewController() {
    }

    /**
     * Initialises the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // Initialise the person table with the two columns.
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        
        //Not typesafe
        numRowsColumn.setCellValueFactory(new PropertyValueFactory<Matrix, Integer>("numRows"));
        numColsColumn.setCellValueFactory(new PropertyValueFactory<Matrix, Integer>("numCols"));
    
        // Clear matrix details.
        showMatrixDetails(null);
        
        // Listen for selection changes and show the person details when changed.
        matrixTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showMatrixDetails(newValue));
    }

    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // Add observable list data to the table
        matrixTable.setItems(mainApp.getMatrixData());
    }
    
    private void showMatrixDetails(Matrix matrix) {
    	if (matrix != null) {
    		nameLabel.setText(matrix.getName());
    		numRowsLabel.setText(Integer.toString(matrix.getNumRows()));
    		numColsLabel.setText(Integer.toString(matrix.getNumCols()));
    		createdDateLabel.setText(matrix.getCreatedDate().toString());
    	} else {
    		nameLabel.setText("");
    		numRowsLabel.setText("");
    		numColsLabel.setText("");
    		createdDateLabel.setText("");
    	}
    }
    
    /**
     * Called when the user clicks on the delete button.
     */
    @FXML
    private void handleDeleteMatrix() {
        int selectedIndex = matrixTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
        	matrixTable.getItems().remove(selectedIndex);
        } else {
        	//Nothing is selected
        	Alert alert = new Alert(AlertType.ERROR);
        	alert.setTitle("No Selection");
        	alert.setHeaderText("No Matrix Selected");
        	alert.setContentText("Please select a matrix in the table.");

        	alert.showAndWait();
        }
        
    }
}
