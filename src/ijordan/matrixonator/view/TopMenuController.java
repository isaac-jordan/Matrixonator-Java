package ijordan.matrixonator.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;


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
  public void handleMenuSaveAll() {

  }
}
