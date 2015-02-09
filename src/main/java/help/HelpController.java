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

public class HelpController {

  @FXML
  private WebView lewis;

  @FXML
  public void initialize() {

    WebEngine web = lewis.getEngine();
    File webpage;

    if (System.getProperty("os.name").contains("Windows")) {
      webpage = new File("help/test.html");
    } else {
      webpage = new File("help\test.html");
    }
    try {
      web.load(webpage.toURI().toURL().toString());
    } catch (MalformedURLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }


  public void run() {
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
}
