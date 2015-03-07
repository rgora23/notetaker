package controllers;
//import helpers.LoginRequest;

import helpers.LoginRequest;
import helpers.RegistrationRequest;

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
	@FXML private Label create_account;
	@FXML private Label create_account_error_text;
	@FXML private Parent root;
	@FXML private AnchorPane dashboard;
	@FXML private Button settings_save_button;
	@FXML private Button notes_button;
	@FXML private Button title_button;
	@FXML private Button modified_button; 
	@FXML private Button date_button;
	@FXML private Button createNote_button;
	@FXML private Button settings_button;
	@FXML private Button tags_button;
    @FXML private Button cancel_create_account_button;    
	@FXML private Text login_fail;
	@FXML private TextField username_input;
	@FXML private PasswordField password_input;
	@FXML private GridPane loginPane; 
	@FXML private Button create_account_button;
	@FXML private AnchorPane create_account_window;
	@FXML private TextField new_username;
	@FXML private PasswordField new_password;
	@FXML private PasswordField confirm_password;
	
	
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
			getMain().transitionLoginFailed();
		}
	}

	@FXML protected void tryRegistration(ActionEvent e) throws IOException {
		String username = new_username.getText();
		String password = new_password.getText();
		String confirmPassword = confirm_password.getText();
		RegistrationRequest request = new RegistrationRequest(username, password, confirmPassword);
		if ( User.register(request).getErrors().isEmpty() ) {
			System.out.println("Account Created");
		}
		else {
			for (String error : request.getErrors() ) {
				System.out.println(error);
			}
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

	@FXML protected void createAccountClicked(MouseEvent e) throws IOException {
		  getMain().transitionCreateAccount();
	}
	
   @FXML protected void tryAccount(MouseEvent e) throws IOException {
	
	  
   }

  @FXML protected void cancelAccountButtonClicked(MouseEvent e) throws IOException {
	  
  }

  @FXML protected void saveSettingsButtonClicked(MouseEvent e) throws IOException {
	  
  }
  
}
