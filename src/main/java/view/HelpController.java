package main.java.view;

import java.io.File;
import java.net.MalformedURLException;

import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class HelpController {
  
  @FXML
  private WebView lewis;
  
  @FXML
  public void initialize() {
    
    WebEngine web = lewis.getEngine();
    File webpage = new File("test.html");
    try {
		web.load(webpage.toURI().toURL().toString());
	} catch (MalformedURLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }
}
