package main.java.view;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import main.java.help.HelpController;

public class TopMenuController {

  private static final int MAJOR_VERSION = 0;
  private static final int MINOR_VERSION = 0;
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
    if (hc == null) {
      hc = new HelpController();
    }
    if (!hc.isOpen()) {
      hc.run();
    }
  }

  @FXML
  public void handleMenuUpdate() {
    try {
      URL url = new URL("https://gist.githubusercontent.com/projectgoav/58b6e2d5f1f317eefe4f/raw");
      BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

      String s = reader.readLine();
      String path = reader.readLine();

      reader.close();

      int major = Integer.parseInt(String.valueOf(s.charAt(0)));
      int minor = Integer.parseInt(String.valueOf(s.charAt(2)));

      if ((major > MAJOR_VERSION) || (minor > MINOR_VERSION)) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Matrixonator - Update");
        alert.setHeaderText("Update available");
        alert.setContentText("Matrixonator Version " + s
            + " is ready to download. Do you wish to download?");

        ButtonType yesButton = new ButtonType("Yes", ButtonData.YES);
        ButtonType noButton = new ButtonType("No", ButtonData.NO);
        alert.getButtonTypes().setAll(yesButton, noButton);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get().getButtonData() == ButtonData.YES) {
          // DOWNLOAD THE UPDATE AND INSTALL?
          // START DOWNLOAD APPLICATION AND INSTALL?
          Process p = Runtime.getRuntime().exec("java -jar Updater.jar " + path);
          System.exit(0);
        }

      }
    } catch (Exception e) {
      System.out.println("An error occured when checking for updates...");
    }
  }
}
