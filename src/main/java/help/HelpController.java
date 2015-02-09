package main.java.help;

import java.io.File;
import java.net.MalformedURLException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 * Matrixonator HelpViewer. Displays the help Content to the User
 * Serves the HTML help pages from Matrixonator GitHub pages
 * @author BigE
 */
public class HelpController {

  @FXML //Lewis the WebBrowser Object :)
  private WebView lewis;

  @FXML //Initially goes to help Index
  public void initialize() {
    WebEngine web = lewis.getEngine();
    web.load(generateURL("test.html"));
  }
  
  /**
   * Set to True is HelpView is already open
   */
  private static boolean isOpen = false;

  /**
   * Run method shows the Help Viewer to the user. Viewer can run side-by-side
   */
  public void run() {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("HelpViewer.fxml"));
      Parent helpPage = (Parent) fxmlLoader.load();
      Stage helpView = new Stage();
      helpView.setTitle("Matrixonator - HelpViewer");
      helpView.setScene(new Scene(helpPage));
      isOpen = true;
      helpView.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Gets if Viewer is open
   * @return True is view is open
   */
  public boolean isOpen() { return isOpen; }
  
  @FXML
  /**
   * Closes the HelpViewer
   */
  public void handleClose() { isOpen = false;
    Stage s = (Stage) lewis.getScene().getWindow();
  s.close(); }

  @FXML
  /**
   * Calls the Web Engine to refresh the current document
   */
  public void handleRefresh() { lewis.getEngine().reload(); }
  
  @FXML
  /**
   * Gets the next item in the WebEngine history
   * TODO Catch OutOfBoundsException for cleaner console output
   */
  public void handleForward() { lewis.getEngine().getHistory().go(1); }
  
  @FXML
  /**
   * Gets the last item in the WebEngine history
   * TODO Catch OutOfBoundException for cleaner console output
   */
  public void handleBack() { lewis.getEngine().getHistory().go(-1); }

  @FXML
  /**
   * Navigates to the About Help page
   */
  public void handleAbout() { lewis.getEngine().load(generateURL("AA.html")); }
  
  
  /**
   * Turns the given HTML filename into a usable URL for WebEngine load()
   * @param webpage (ending in .html)
   * @return  URL in String format of file. Returns null if invalid URL
   */
  private String generateURL(String webpage)
  {
    File newPage = null;
    if (System.getProperty("os.name").contains("Windows")) {
      newPage = new File("help/" + webpage);
    } else {
      newPage = new File("help\\" + webpage);
    }
    try {
      return newPage.toURI().toURL().toString();
    } catch (MalformedURLException e) {
      e.printStackTrace();
      return null;
    } 
  }
}
