package viewControllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import noteTaker.Session;

// This is the parent controller class that will be extended by all view controllers.
// It holds common methods and variables that all controllers in this application
// will need access to.
public abstract class ViewController implements Initializable {
	
	public ViewController() {
		
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}

	
	public Scene getScene() {
		return Session.getStage().getScene();
	}

	public Node getNodeById(String id) {
		return getScene().lookup("#" + id);
	}

	public Label getLabelById(String id) {
		return ((Label) getNodeById(id));
	}

	public TextField getTextFieldById(String id) {
		return ((TextField) getNodeById(id));
	}

	public PasswordField getPasswordFieldById(String id) {
		return ((PasswordField) getNodeById(id));
	}

	public Button getButtonById(String id) {
		return ((Button) getNodeById(id));
	}

	public GridPane getGridPaneById(String id) {
		return ((GridPane) getNodeById(id));
	}

	public AnchorPane getAnchorPaneById(String id) {
		return ((AnchorPane) getNodeById(id));
	}
	
	public Pane getPaneById(String id) {
		return ((Pane) getNodeById(id));
	}

	public Text getTextById(String id) {
		return ((Text) getNodeById(id));
	}

}
