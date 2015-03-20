package noteTaker;


import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
	
	private Scene scene;
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws IOException {
		
		// Set the stage for the EventControllerParent so that
		// all view controllers have access to the stage.
		Session.setStage(primaryStage);
		
		// Set the title of the window
		primaryStage.setTitle("NoteTaker");

		// Load filePath and create scene from it
		Parent root = getRootFromFile("/views/Root.fxml");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		scene = new Scene(root, screenSize.width, screenSize.height);

		// Apply style to scene
		String cssUrl = this.getClass().getResource("/assets/css/application.css").toExternalForm();
		scene.getStylesheets().add(cssUrl);
		
		// Set the newly created and styled scene to primaryStage and show it.
		primaryStage.setScene(scene);
		primaryStage.setMinWidth(800.0);
		primaryStage.setMinHeight(500.0);
		primaryStage.show();
	}

	public Parent getRootFromFile(String pathToFile) {
		FXMLLoader loader = new FXMLLoader(this.getClass().getResource(pathToFile));
		
		Parent root = null;
		try {
			root = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return root;
	}
	

}