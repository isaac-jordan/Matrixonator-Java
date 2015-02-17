package main.java;

import main.java.model.Matrix;
import main.java.model.MatrixIO;
import main.java.view.MatrixAlerts;
import main.java.view.MatrixOverviewController;
import main.java.view.MatrixonatorIOException;

import java.io.IOException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

/**
 * Main entry point for Matrixonator application.
 * @author Isaac Jordan
 */
public class MainApp extends Application {

  private Stage primaryStage;
  private BorderPane rootLayout;

  /**
   * Set on startup if passed with update arg Means update message is displayed
   */
  private static boolean wasUpdate = false;

  /**
   * Constructor
   */
  public MainApp() {
    // Add some sample data
    Global.addMatrix(new Matrix("Example", new double[][] { {5, 2, 13}, {3, 2, -5}, {7, 0, 9}}, null));
    Global.addMatrix(new Matrix("Identity2", new double[][] { {1, 0}, {0, 1}}, null));

    /*
     * NB: ONLY HERE FOR TESTING PURPOSES, PERHAPS MOVE TO INIT AND OUT OF CONSTRUCTOR? OUT OF
     * CONSTRUCTOR?
     */
    try {
      MatrixIO.checkDirectories();
    } catch (MatrixonatorIOException e) {
      Alert alert = new Alert(AlertType.WARNING);
      alert.setHeaderText("Matrixonator working Directories");
      alert.setTitle("Alert");
      alert.setContentText(e.getMessage());
      alert.showAndWait();
      MatrixIO.setSaveFlag();
    }

    if (wasUpdate) {
      MatrixAlerts.showWasUpdate();
    }

    // Load in all saved matrices for display
    ArrayList<Matrix> result = MatrixIO.loadAll();
    for (Matrix m : result) {
      Global.addMatrix(m);
    }
  }

  @Override
  public void start(Stage primaryStage) {
    this.primaryStage = primaryStage;
    this.primaryStage.setTitle("Matrixonator");

    initRootLayout();

    showMatrixOverview();

  }

  /**
   * Initialises the root layout.
   */
  public void initRootLayout() {
    try {
      // Load root layout from fxml file.
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(MatrixOverviewController.class.getResource("RootLayout.fxml"));
      rootLayout = (BorderPane) loader.load();

      // Show the scene containing the root layout.
      Scene scene = new Scene(rootLayout);
      primaryStage.setScene(scene);
      primaryStage.show();

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Shows the person overview inside the root layout.
   */
  public void showMatrixOverview() {
    try {
      // Load matrix overview.
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(MainApp.class.getResource("view/MatrixOverview.fxml"));
      AnchorPane matrixOverview = (AnchorPane) loader.load();

      // Set matrix overview into the centre of root layout.
      rootLayout.setCenter(matrixOverview);

      // Give the controller access to the main app.
      MatrixOverviewController controller = loader.getController();
      controller.setMainApp(this);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Returns the main stage.
   * 
   * @return
   */
  public Stage getPrimaryStage() {
    return primaryStage;
  }

  public static void main(String[] args) {

    // Check if called with update args
    if (args.length == 1) {
      if (args[0].toLowerCase().compareTo("update") == 0) {
        wasUpdate = true;
      }
    }

    launch(args);
  }


}
