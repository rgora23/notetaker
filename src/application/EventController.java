package application;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.stage.Stage;
public class EventController {
	private Main main;

	@FXML private Label noteTaker_text;
	@FXML private Parent root;
	@FXML protected void ChangeText(ActionEvent event) throws IOException {
		noteTaker_text.setText("NoteTaker");
		// Check db for correct un/pw
		Stage stage = (Stage) root.getScene().getWindow();

		boolean loginSuccessful = true;
		getMain().loginSuccessWindow(root);

	}

	public Main getMain() {
		return main;
	}
	public void setMain(Main main) {
		this.main = main;
	}


}
