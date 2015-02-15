package main.java.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import main.java.help.HelpController;

public class TopMenuController {
  
  private HelpController hc;

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

    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("About: Matrixonator - Java");
    alert.setHeaderText("Matrixonator-Java: Alpha");
    alert.setContentText(aboutText);
    alert.getDialogPane().setPrefSize(500, 400);
    alert.showAndWait();
  }

  @FXML
  public void handleMenuDelete() {
    System.out.println("BERSTY");
  }

  @FXML
  public void handleMenuHelp() {
    if (hc == null) { hc = new HelpController(); }    
    if (!hc.isOpen()) { hc.run(); }
  }
}
