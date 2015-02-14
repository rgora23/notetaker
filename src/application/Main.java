package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(this.getClass().getResource("GUIfxml.fxml"));
			
			Parent root = loader.load();
			
			Scene scene = new Scene(root,1200,650);
			primaryStage.setTitle("Note Taker");

			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			EventController c = loader.getController();
			c.setMain(this);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void loginSuccessWindow(Parent root) throws IOException {
//		System.out.println("THIS WORKED!");
//		Stage primaryStage = getStage(root);
//		Parent newRoot = FXMLLoader.load(this.getClass().getResource("GUIfxml.fxml"));
//		Scene scene = new Scene(newRoot, 1200, 650);
//		primaryStage.setScene(scene);
	}
	
	public static Stage getStage(Parent root) {
		return (Stage) root.getScene().getWindow();
	}
	
	


	public static void main(String[] args) {
		launch(args);
	}
	


}
