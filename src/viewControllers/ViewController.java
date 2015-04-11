package viewControllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.web.HTMLEditor;
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

	protected Session getSession() {
		return Session.getInstance();
	}

	protected Scene getScene() {
		return getSession().getStage().getScene();
	}

	protected Node getNodeById(String id) {
		return getScene().lookup("#" + id);
	}

	protected Label getLabelById(String id) {
		return ((Label) getNodeById(id));
	}

	protected TextField getTextFieldById(String id) {
		return ((TextField) getNodeById(id));
	}

	protected PasswordField getPasswordFieldById(String id) {
		return ((PasswordField) getNodeById(id));
	}

	protected Button getButtonById(String id) {
		return ((Button) getNodeById(id));
	}

	protected GridPane getGridPaneById(String id) {
		return ((GridPane) getNodeById(id));
	}

	protected AnchorPane getAnchorPaneById(String id) {
		return ((AnchorPane) getNodeById(id));
	}

	protected Pane getPaneById(String id) {
		return ((Pane) getNodeById(id));
	}

	protected Text getTextById(String id) {
		return ((Text) getNodeById(id));
	}

	protected ListView<String> getListViewById(String id) {
		return ((ListView<String>) getNodeById(id));
	}

	protected TextFlow getTextFlowById(String id) {
		return ((TextFlow) getNodeById(id));
	}

	protected HTMLEditor getHTMLEditorById(String id) {
		return ((HTMLEditor) getNodeById(id));
	}

}
