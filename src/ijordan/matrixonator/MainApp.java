package ijordan.matrixonator;

import ijordan.matrixonator.model.Matrix;
import ijordan.matrixonator.view.MatrixOverviewController;

import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class MainApp extends Application {
	
	private Stage primaryStage;
	private BorderPane rootLayout;
	
	/**
     * The data as an observable list of matrices.
     */
    private ObservableList<Matrix> matrixData = FXCollections.observableArrayList();

    /**
     * Constructor
     */
    public MainApp() {
        // Add some sample data
        matrixData.add(new Matrix("Hans", new double[][]{{5,2},{3,0}}));
        matrixData.add(new Matrix("Identity2", new double[][]{{1,0},{0,1}}));
        
    }

    /**
     * Returns the data as an observable list of matrices. 
     * @return
     */
    public ObservableList<Matrix> getMatrixData() {
        return matrixData;
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
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

	public static void main(String[] args) {
		launch(args);
	}
}
