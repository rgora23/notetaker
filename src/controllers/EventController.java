package controllers;
import helpers.LoginRequest;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import models.User;
public class EventController extends EventControllerParent {

	@FXML private Label noteTaker_text;
	@FXML private Parent root;
	@FXML private AnchorPane dashboard;
	@FXML private Button notes_button;
	@FXML private Button title_button;
	@FXML private Button modified_button; 
	@FXML private Button date_button;
	@FXML private GridPane loginPane; 
	@FXML private Button createNote_button;
	@FXML private Button settings_button;
	@FXML private Button tags_button;
	@FXML private Label create_account;
	@FXML private Text login_fail_text;
	@FXML private TextField username_input;
	@FXML private PasswordField password_input;
	
	
	@FXML protected void tryLogin(ActionEvent e) throws IOException {
		String username = username_input.getText();
		String password = password_input.getText();
		LoginRequest loginRequest = new LoginRequest(username, password);
		
		if (User.authenticate(loginRequest).isAuthenticated()) {
			User currentUser = User.getUserById(loginRequest.getUserID());
			getMain().setCurrentUser(currentUser);
			getMain().transitionLoginSuccess();
		}
		else {
			getMain().transitionLoginFailed(login_fail_text);
		}
	}

  
	@FXML protected void noteButtonClicked(MouseEvent e) throws IOException{
		  noteTaker_text.setText("YES");
	}
	
	@FXML protected void titleButtonClicked(MouseEvent e) throws IOException{
		  noteTaker_text.setText("NO");
	}
	
	@FXML protected void modifiedButtonClicked(MouseEvent e) throws IOException{
		  noteTaker_text.setText("MAYBE");
	}
	
	@FXML protected void dateButtonClicked(MouseEvent e) throws IOException{
		  noteTaker_text.setText("..YES");
	}
   
	@FXML protected void createNoteButtonClicked(MouseEvent e) throws IOException {
		  noteTaker_text.setText("new Note!");
	}
	
	@FXML protected void tagsButtonClicked(MouseEvent e) throws IOException {
		  noteTaker_text.setText("searchn' tags!");
	}
   
	@FXML protected void settingsButtonClicked(MouseEvent e) throws IOException {
		  noteTaker_text.setText("viewSettings!");
	}

	@FXML protected void createAccount(MouseEvent e) throws IOException {
		  // open window to gather account info
		 noteTaker_text.setText("New account!");
	}
}
