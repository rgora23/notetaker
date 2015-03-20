package viewControllers;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import noteTaker.Session;
import controllers.AccountsController;
import controllers.NotesController;
public class EventController extends ViewController {

	AccountsController accountsController;
	
	public EventController() {
		accountsController = new AccountsController();
	}
	
	@FXML protected void tryLogin(ActionEvent e) throws IOException {
		// Get username and password input values to give to controller
		String username = getTextFieldById("username_input").getText();
		String password = ( getPasswordFieldById("password_input") ).getText();
		

		// AccountsController tries to login with these credentials
		String[] errors = AccountsController.login(username, password);
		if (errors.length == 0) {
			// Change GUI to show logged in user
			// Can reference account for info by referencing Session class.
			// Session.getAccount().getUsername();
			System.out.println("Welcome " + Session.getAccount().getUsername() + "!");
			getGridPaneById("login_pane_root").setVisible(false);
			getAnchorPaneById("dashboard").setDisable(false);
			getButtonById("logout_button").setVisible(true);
			getLabelById("NOTETAKER_text").setVisible(true);
			getPaneById("note_buttons").setDisable(false);
			getLabelById("newaccount").setVisible(false);
		}
		else {
			// Change GUI to present errors to user
			System.out.println(errors[0]);
			getTextById("login_fail_text").setText(errors[0]);
			getTextById("login_fail_text").setVisible(true); 
		}


	}

	@FXML protected void tryRegistration(Event e) throws IOException {
		String username = getTextFieldById("new_username").getText();
		String password = getPasswordFieldById("new_password").getText();
		String confirmPassword = getPasswordFieldById("confirm_password").getText();
		
		String[] errors = accountsController.register(username, password, confirmPassword);
		if ( errors.length == 0 ) {
			System.out.println("Account Created");
		}
		else {
			for (String error : errors ) {
				System.out.println(error);
			    getLabelById("create_account_error_text").setText(error); 
			}
		}		
	}
	
	@FXML protected void noteCreationAction(Event e) throws IOException {
		String noteTitle = getTextFieldById("note_title_input").getText();
		NotesController.create(noteTitle);
	}
	
	@FXML protected void logoutButtonClicked(MouseEvent e) throws IOException {
		  getGridPaneById("login_pane_root").setVisible(true);
		  getLabelById("NOTETAKER_text").setVisible(false); 
		  getAnchorPaneById("dashboard").setDisable(true);
		  getButtonById("logout_button").setVisible(false);
		  getPaneById("note_buttons").setDisable(true);
		  AccountsController.logout();
		  
	}
	
   
	
	@FXML protected void createNoteButtonClicked(MouseEvent e) throws IOException {
		getAnchorPaneById("create_note_pane").setVisible(true);
	}

	@FXML protected void noteButtonClicked(MouseEvent e) throws IOException{
		getLabelById("noteTaker_text").setText("YES");
	}

	@FXML protected void titleButtonClicked(MouseEvent e) throws IOException{
		getLabelById("noteTaker_text").setText("NO");
	}

	@FXML protected void modifiedButtonClicked(MouseEvent e) throws IOException{
		getLabelById("noteTaker_text").setText("MAYBE");
	}

	@FXML protected void dateButtonClicked(MouseEvent e) throws IOException{
		getLabelById("noteTaker_text").setText("..YES");
	}

	@FXML protected void tagsButtonClicked(MouseEvent e) throws IOException {
		getLabelById("noteTaker_text").setText("searchn' tags!");
	}

	@FXML protected void settingsButtonClicked(MouseEvent e) throws IOException {
		getNodeById("account_settings_pane").setVisible(true);
	}

	@FXML protected void createAccountClicked(MouseEvent e) throws IOException {
		getAnchorPaneById("create_account_pane").setVisible(true);
	}

	@FXML protected void tryAccount(MouseEvent e) throws IOException {

	}

	@FXML protected void cancelAccountButtonClicked(MouseEvent e) throws IOException {
		getAnchorPaneById("create_account_pane").setVisible(false);

	}

	@FXML protected void saveSettingsButtonClicked(MouseEvent e) throws IOException {
		getNodeById("account_settings_pane").setVisible(false);
	}

	@FXML protected void noteCancelAction(MouseEvent e) throws IOException {
		  getAnchorPaneById("create_note_pane").setVisible(false); 
		  
	}
	
}
