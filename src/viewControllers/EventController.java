package viewControllers;
import java.io.IOException;

import noteTaker.Session;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import requestHelpers.RegistrationRequest;
import controllers.AccountsController;
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
		AccountsController.login(username, password);
		if (errors.length == 0) {
			// Change GUI to show logged in user
			// Can reference account for info by referencing Session class.
			// Session.getAccount().getUsername();
			System.out.println("Welcome " + Session.getAccount().getUsername() + "!");
		}
		else {
			// Change GUI to present errors to user
			System.out.println(errors[0]);
		}
	}

	@FXML protected void tryRegistration(Event e) throws IOException {
		String username = getTextFieldById("new_username").getText();
		String password = getPasswordFieldById("new_password").getText();
		String confirmPassword = getPasswordFieldById("confirm_password").getText();
		
		RegistrationRequest request = accountsController.register(username, password, confirmPassword);
		if ( request.getErrors().isEmpty() ) {
			System.out.println("Account Created");
		}
		else {
			for (String error : request.getErrors() ) {
				System.out.println(error);
			}
		}		
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

	@FXML protected void createNoteButtonClicked(MouseEvent e) throws IOException {
		getLabelById("noteTaker_text").setText("new Note!");
	}

	@FXML protected void tagsButtonClicked(MouseEvent e) throws IOException {
		getLabelById("noteTaker_text").setText("searchn' tags!");
	}

	@FXML protected void settingsButtonClicked(MouseEvent e) throws IOException {
		getLabelById("noteTaker_text").setText("viewSettings!");
	}

	@FXML protected void createAccountClicked(MouseEvent e) throws IOException {
		getAnchorPaneById("create_account_pane").setVisible(true);
		getNodeById("account_settings_pane").setVisible(false);
	}

	@FXML protected void tryAccount(MouseEvent e) throws IOException {

	}

	@FXML protected void cancelAccountButtonClicked(MouseEvent e) throws IOException {
		getAnchorPaneById("create_account_pane").setVisible(false);
		getNodeById("account_settings_pane").setVisible(true);
	}

	@FXML protected void saveSettingsButtonClicked(MouseEvent e) throws IOException {

	}

}
