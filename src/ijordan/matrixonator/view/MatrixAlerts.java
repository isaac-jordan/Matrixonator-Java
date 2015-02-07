package ijordan.matrixonator.view;

import java.util.Optional;

import ijordan.matrixonator.model.Matrix;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;

/**
 * Provides basic alert templates for Matrixonator.
 * 
 * @author BigE
 *
 */
public class MatrixAlerts {

  /**
   * Creates and displays a pop-up (alert) that contains the data of the given matrix.
   * 
   * @param matrix - Matrix data to display
   * @param matrixName - The name of the matrix. Use null if data can be retrieved from matrix parameter.
   */
  public static void dataAlert(Matrix matrix, String matrixName) {
    Dialog<Object> dialog = new Dialog<Object>();

    if (matrixName == null) {
      matrixName = matrix.getName();
    }
    dialog.setTitle(matrixName);
    dialog.setHeaderText("Showing the data associated with " + matrixName);
    
    ButtonType closeButtonType = new ButtonType("Close", ButtonData.OK_DONE);
    dialog.getDialogPane().getButtonTypes().addAll(closeButtonType);

    GridPane alertGrid = new GridPane();
    alertGrid.setHgap(20);
    alertGrid.setVgap(10);
    for (int i = 0; i < matrix.getNumRows(); i++) {
      for (int j = 0; j < matrix.getNumCols(); j++) {
        Label label = new Label();
        double value = matrix.getData()[i][j];
        
        // Ternary 'if' to remove '.0' from a value like '5.0'.
        label.setText((long) value == value ? "" + (long) value : "" + value);
        alertGrid.add(label, j, i);
        System.out.println((long) value + " " + value);

      }
    }
    dialog.getDialogPane().setContent(alertGrid);
    dialog.showAndWait();
  }

  /**
   * Shows a no-selection alert to the user
   */
  public static void noSelectionAlert() {
    Alert alert = new Alert(AlertType.ERROR);
    alert.setTitle("No Selection");
    alert.setHeaderText("No Matrix Selected");
    alert.setContentText("Please select a matrix in the table.");

    alert.showAndWait();
  }

  /**
   * Shows an invalid Row & Column alert when creating a Matrix
   */
  public static void invalidRowColAlert() {
    Alert alert = new Alert(AlertType.ERROR);
    alert.setTitle("Error");
    alert.setHeaderText("Invalid number of rows, or columns.");
    alert.setContentText("Please enter ONLY integers next time. Please cancel and try again.");

    alert.showAndWait();
  }

  /**
   * Displays confirmation that the matrix has been saved to file!
   */
  public static void onSave() {
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("Matrix Saved!");
    alert.setHeaderText("Matrix has been saved properly");
    alert.setContentText("It should appear again when you next load the program!");

    alert.showAndWait();

  }
  
  public static void handleDeleteRequest(Matrix matrix)
  {
    if (MatrixIO.isMatrixSaved(matrix.getName() + ".matrix"))
    {
      Alert alert = new Alert(AlertType.WARNING);
      alert.setTitle("Warning : Delete");
      alert.setHeaderText("Delete " + matrix.getName());
      alert.setContentText(matrix.getName() + " is saved on your system. Do you wish to remove this?\nIf not, " + matrix.getName() + " will appear when you restart Matrixonator");
    
      ButtonType yesButton = new ButtonType("Yes", ButtonData.YES);
      ButtonType noButton = new ButtonType("No", ButtonData.NO);
      
      alert.getButtonTypes().setAll(yesButton, noButton);
      
      Optional<ButtonType> result = alert.showAndWait();
      
      if (result.get().getButtonData() == ButtonData.YES) {
        System.out.println("Received confirmation to delete matrix from disk!");
        System.out.println("Attempting to delete: " + matrix.getName() + " from file..." + MatrixIO.deleteFile(matrix.getName()+ ".matrix"));
        
        Alert newAlert = new Alert(AlertType.INFORMATION);
        newAlert.setTitle("Matrix deleted!");
        newAlert.setHeaderText(matrix.getName() + "deleted!");
        newAlert.setContentText("The matrix has gone for ever :( RIP");  
        newAlert.showAndWait();
      }
      else{       
        Alert newAlert = new Alert(AlertType.INFORMATION);
        newAlert.setTitle("Matrix removed!");
        newAlert.setHeaderText(matrix.getName() + "removed!");
        newAlert.setContentText("The next time you start Matrixonator, it will be back!");  
        newAlert.showAndWait();
      }
    }
    
    
    
    
  }
}
