package views;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.User;
import controllers.EventController;


public class Main extends Application {

	Stage stage;
	User currentUser;

	@Override
	public void start(Stage primaryStage) throws IOException {

		// Set the title of the window
		primaryStage.setTitle("Note Taker");

		// Set the stage so that it can be retrieved later during scene transitions
		setStage(primaryStage);

		loadNewScene("GUIfxml.fxml");

		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}

	// For loading with application.css as default stylesheet
	public void loadNewScene(String fxmlString) throws IOException {
		loadNewScene(fxmlString, "application.css");
	}

	public void loadNewScene(String fxmlString, String cssString) throws IOException {

		// Load FUIfxml.fxml and create scene
		FXMLLoader loader = new FXMLLoader(this.getClass().getResource(fxmlString));
		Parent root = loader.load();
		Scene scene = new Scene(root,1200,650);

		// After Parent root is loaded, set Main's controller to root's controller
		// Need to cast it as EventController to get access to EventController's setter
		( (EventController) loader.getController() ).setMain(this);

		// Apply style to scene
		String cssUrl = this.getClass().getResource(cssString).toExternalForm();
		scene.getStylesheets().add(cssUrl);

		// Set the newly created and styled scene to primaryStage and show it.
		getStage().setScene(scene);
	}


	public void transitionLoginSuccess() throws IOException {
		// At this point the scene needs to load for the logged in user
		
		getElementById("login_pane").setVisible(false);
		getElementById("dashboard").setDisable(false);
		
		System.out.println("LOGIN SUCCESS");
	}
	
	//This method is called when User clicks "create account" in the program
	public void transitionCreateAccount() throws IOException {
		getElementById("create_account_pain").setVisible(true);
		
	}
	
	public void transitionCancelCreateAccount() throws IOException {
		getElementById("create_account_pain").setVisible(false);
	}
	
	public void transitionLoginFailed() throws IOException {
		getElementById("login_fail_text").setVisible(true);		
	}

	public void transitionSaveSettings() throws IOException {
		
	}
	
	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public Stage getStage() {
		return this.stage;
	}

	public User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}
	
	public Node getElementById(String id) {
		 return getStage().getScene().lookup("#" + id); 
	}
	
	
}
