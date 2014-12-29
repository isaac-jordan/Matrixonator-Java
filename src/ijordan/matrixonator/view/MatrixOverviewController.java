package ijordan.matrixonator.view;

import org.controlsfx.dialog.Wizard;
import org.controlsfx.dialog.Wizard.WizardPane;
import org.controlsfx.dialog.Wizard.LinearFlow;

import ijordan.matrixonator.MainApp;
import ijordan.matrixonator.model.Matrix;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

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
	 * The constructor. The constructor is called before the initialise()
	 * method.
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
		nameColumn.setCellValueFactory(cellData -> cellData.getValue()
				.nameProperty());

		// Not typesafe
		numRowsColumn
				.setCellValueFactory(new PropertyValueFactory<Matrix, Integer>(
						"numRows"));
		numColsColumn
				.setCellValueFactory(new PropertyValueFactory<Matrix, Integer>(
						"numCols"));

		// Clear matrix details.
		showMatrixDetails(null);

		// Listen for selection changes and show the person details when
		// changed.
		matrixTable
				.getSelectionModel()
				.selectedItemProperty()
				.addListener(
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

	/**
	 * This method is called when the listener detects that a matrix was selected on the left hand table.
	 * It updates the labels on the right-hand side of the GUI.
	 * @param matrix
	 */
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
	 * Called when the user clicks on the new button.
	 * This method will guide the user through the wizard
	 * that asks them to enter the matrix details.
	 */
	@FXML
	private void handleNewMatrix() {
		// define pages to show

		Wizard wizard = new Wizard();
		wizard.setTitle("Create New Matrix");

		// --- page 1
		int row = 0;

		GridPane page1Grid = new GridPane();
		page1Grid.setVgap(10);
		page1Grid.setHgap(10);

		page1Grid.add(new Label("Name:"), 0, row);
		TextField txFirstName = createTextField("name", 80);
		page1Grid.add(txFirstName, 1, row++);

		page1Grid.add(new Label("Number of rows:"), 0, row);
		TextField txNumRows = createTextField("numRows", 80);
		page1Grid.add(txNumRows, 1, row++);

		page1Grid.add(new Label("Number of columns:"), 0, row);
		TextField txNumCols = createTextField("numCols", 80);
		page1Grid.add(txNumCols, 1, row);

		WizardPane page1 = new WizardPane();
		page1.setHeaderText("Please Enter Matrix Details");
		page1.setContent(page1Grid);

		// --- page 2

		final WizardPane page2 = new WizardPane() {
			@Override
			public void onEnteringPage(Wizard wizard) {
				String name = (String) wizard.getSettings().get("name");

				// Bit of a horrible hack to get the integers. Need to find
				// direct conversion.
				int numRows = Integer.parseInt((String) wizard.getSettings()
						.get("numRows"));
				int numCols = Integer.parseInt((String) wizard.getSettings()
						.get("numCols"));

				GridPane page2Grid = new GridPane();
				for (int i = 0; i < numRows; i++) {
					for (int j = 0; j < numCols; j++) {
						// Naming of text fields needs to be improved
						TextField tx = createTextField("" + i + " " + j, 20);
						tx.setPromptText("Enter integer");
						page2Grid.add(tx,j,i);
						
					}
				}
				page2Grid.setHgap(5);
				page2Grid.setVgap(10);
				setContent(page2Grid);

			}
		};
		page2.setHeaderText("Creating Matrix");

		// --- page 3
		WizardPane page3 = new WizardPane() {
			@Override
			public void onEnteringPage(Wizard wizard) {
				String name = (String) wizard.getSettings().get("name");

				// Bit of a horrible hack to get the integers. Need to find
				// direct conversion.
				int numRows = Integer.parseInt((String) wizard.getSettings()
						.get("numRows"));
				int numCols = Integer.parseInt((String) wizard.getSettings()
						.get("numCols"));
				
				double[][] data = new double[numRows][numCols];
				
				int currentData;

				for (int i = 0; i < numRows; i++) {
					for (int j = 0; j < numCols; j++) {
						String raw = (String) wizard.getSettings().get("" + i + " " + j);
						try {
							currentData = Integer.parseInt(raw);
						} catch (NumberFormatException e) {
							currentData = 0;
						}
						
						data[i][j] = currentData;

					}
				}

				mainApp.getMatrixData().add(new Matrix(name, data));

			}
		};
		page3.setHeaderText("Goodbye!");
		page3.setContentText("Matrix created.");

		// Example of how to add a button
		// ButtonType helpDialogButton = new ButtonType("Help",
		// ButtonData.HELP_2);
		// page3.getButtonTypes().add(helpDialogButton);
		// Button helpButton = (Button) page3.lookupButton(helpDialogButton);
		// helpButton.addEventFilter(ActionEvent.ACTION, actionEvent -> {
		// actionEvent.consume(); // stop hello.dialog from closing
		// System.out.println("Help clicked!");
		// });

		// create wizard
		wizard.setFlow(new LinearFlow(page1, page2, page3));

		// show wizard and wait for response
		wizard.showAndWait().ifPresent(
				result -> {
					if (result == ButtonType.FINISH) {
						System.out.println("Wizard finished, settings: "
								+ wizard.getSettings());
						System.out.println(wizard.getProperties());
					}
				});
	}

	/**
	 * Method is called when the "Delete" button is pressed.
	 * If a valid matrix is selected in the table on the right,
	 * then it is deleted from the matrixTable.
	 */
	@FXML
	private void handleDeleteMatrix() {
		int selectedIndex = matrixTable.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			matrixTable.getItems().remove(selectedIndex);
		} else {
			// Nothing is selected
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("No Selection");
			alert.setHeaderText("No Matrix Selected");
			alert.setContentText("Please select a matrix in the table.");

			alert.showAndWait();
		}

	}

	/**
	 * A utility method for creating TextFields with specified id and width.
	 * @param id
	 * @param width
	 * @return
	 */
	private TextField createTextField(String id, int width) {
		TextField textField = new TextField();
		textField.setId(id);
		textField.setPrefWidth(width);
		GridPane.setHgrow(textField, Priority.ALWAYS);
		return textField;
	}
}
