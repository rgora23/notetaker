package controllers;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
public class EventController extends EventControllerParent {

	@FXML private Label noteTaker_text;
	@FXML private Parent root;
<<<<<<< Updated upstream
=======
	@FXML private AnchorPane dashboard;
	@FXML private Button notes_button;
	@FXML private Button title_button;
	@FXML private Button modified_button; 
	@FXML private Button date_button;
	@FXML private GridPane loginPane;
	@FXML private Button createNote_button;
	@FXML private Button settings_button;
	@FXML private Button tags_button;
	
>>>>>>> Stashed changes
	@FXML protected void changeText(ActionEvent e) throws IOException {
		changeText();
	}
	@FXML protected void changeText(MouseEvent e) throws IOException {
		changeText();
	}
	
	protected void changeText() throws IOException {
		noteTaker_text.setText("NoteTaker");
		// Check db for correct un/pw
		getMain().transitionLoginSuccess();
	}


}
