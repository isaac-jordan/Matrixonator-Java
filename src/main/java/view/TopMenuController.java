package main.java.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;


public class TopMenuController {

  @FXML
  public void handleMenuClose() {
    /** @TODO Add save logic for all Matrices here **/
    System.exit(0);
  }

  @FXML
  public void initialize() {}

  @FXML
  public void handleMenuAbout() {
    StringBuilder aboutInfo = new StringBuilder("WRITTEN BY: Isaac Jordan (Sheepzez)");
    aboutInfo.append("\n\nWITH THANKS:");
    aboutInfo.append("\nBen Jackson (ExogenesisBen)\nEwan McCartney (projectgoav)\n\n");
    aboutInfo.append("\nAny issues, please visit https://github.com/Sheepzez/Matrixonator-Java");

    String aboutText = aboutInfo.toString();

    TextArea aboutArea = new TextArea(aboutText);
    aboutArea.setEditable(false);
    aboutArea.setWrapText(true);

    aboutArea.setMaxWidth(Double.MAX_VALUE);
    aboutArea.setMaxHeight(Double.MAX_VALUE);
    GridPane.setVgrow(aboutArea, Priority.ALWAYS);
    GridPane.setHgrow(aboutArea, Priority.ALWAYS);

    GridPane aboutContent = new GridPane();
    aboutContent.setMaxWidth(Double.MAX_VALUE);
    aboutContent.add(aboutArea, 0, 0);

    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("About: Matrixonator - Java");
    alert.setHeaderText("Matrixonator-Java: [VERSION NO]");
    alert.setContentText("Click 'Show Details' for more information");
    alert.getDialogPane().setExpandableContent(aboutContent);
    alert.showAndWait();
  }

  @FXML
  public void handleMenuDelete() {
    System.out.println("BERSTY");
  }

  @FXML
  public void handleMenuHelp() {
    System.out.println("I'll show help sometime soon");
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("HelpViewer.fxml"));
      Parent helpPage = (Parent) fxmlLoader.load();
      Stage stage = new Stage();
      stage.setScene(new Scene(helpPage));
      stage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @FXML
  public void handleMenuSaveAll() {

  }
}
