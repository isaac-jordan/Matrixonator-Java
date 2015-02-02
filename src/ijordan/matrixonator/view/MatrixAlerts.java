package ijordan.matrixonator.view;

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
 * @author BigE
 *
 */
public class MatrixAlerts {

  /**
   * Creates and displays a pop-up (alert) that contains the data of the given matrix.
   * 
   * @param matrix
   */
  public static void dataAlert(Matrix matrix) {
    Dialog<Object> dialog = new Dialog<Object>();
    dialog.setTitle(matrix.getName());
    dialog.setHeaderText("Showing the data associated with " + matrix.getName());
    ButtonType closeButtonType = new ButtonType("Close", ButtonData.OK_DONE);
    dialog.getDialogPane().getButtonTypes().addAll(closeButtonType);

    GridPane alertGrid = new GridPane();
    alertGrid.setHgap(20);
    alertGrid.setVgap(10);
    for (int i = 0; i < matrix.getNumRows(); i++) {
      for (int j = 0; j < matrix.getNumCols(); j++) {
        Label label = new Label();
        // Should probably use decimalFormat for clean formatting
        label.setText(String.valueOf(matrix.getData()[i][j]));
        alertGrid.add(label, j, i);

      }
    }
    dialog.getDialogPane().setContent(alertGrid);
    dialog.showAndWait();
  }
  
  /** 
   * Shows a no-selection alert to the user
   */
  public static void noSelectionAlert()
  {
    Alert alert = new Alert(AlertType.ERROR);
    alert.setTitle("No Selection");
    alert.setHeaderText("No Matrix Selected");
    alert.setContentText("Please select a matrix in the table.");

    alert.showAndWait(); 
  }
  
  /**
   * Shows an invalid Row & Column alert when creating a Matrix
   */
  public static void invalidRowColAlert()
  {
    Alert alert = new Alert(AlertType.ERROR);
    alert.setTitle("Error");
    alert.setHeaderText("Invalid number of rows, or columns.");
    alert
        .setContentText("Please enter ONLY integers next time. Please cancel and try again.");

    alert.showAndWait(); 
  }
  
}
