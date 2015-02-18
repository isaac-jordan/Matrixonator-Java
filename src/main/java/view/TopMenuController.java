package main.java.view;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import main.java.Global;
import main.java.help.HelpController;

/**
 * Class for handling the top GUI menu elements.
 */
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
    if (hc == null) {
      hc = new HelpController();
    }
    if (!hc.isOpen()) {
      hc.run();
    }
  }

  @FXML
  /**
   * Initiates and update check and prompts user is one is found
   */
  public void handleMenuUpdate() {
    try {
      URL url = new URL("https://gist.githubusercontent.com/projectgoav/58b6e2d5f1f317eefe4f/raw");
      BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

      String s = reader.readLine();
      String path = reader.readLine();

      reader.close();

      int major = Integer.parseInt(String.valueOf(s.charAt(0)));
      int minor = Integer.parseInt(String.valueOf(s.charAt(2)));

      if ((major > Global.MAJOR_VERSION_NUMBER) || (minor > Global.MINOR_VERSION_NUMBER)) {
        MatrixAlerts.showUpdates(s);
        MatrixAlerts.showUpdateWarning();
        // TODO Add in check for if updater.jar isn't actually there :(
        Process p = Runtime.getRuntime().exec("java -jar Updater.jar" + path);
        if (p.isAlive()) {
          System.exit(0);
        }
      } else {
        MatrixAlerts.showNoUpdates();
      }
    } catch (Exception e) {
      System.out.println("An error occured when checking for updates...");
      MatrixAlerts.showNoUpdateCheck();
    }
  }
}
