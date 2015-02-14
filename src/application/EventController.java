package application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
public class EventController {
	
		@FXML private Label noteTaker_text;
		@FXML protected void ChangeText(ActionEvent event) {
			  noteTaker_text.setText("NoteTaker");
			  
			  
		}
	
	
}
