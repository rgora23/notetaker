package viewControllers;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import requestHelpers.RegistrationRequest;
import controllers.AccountsController;
public class EventController extends ViewControllerParent {

	AccountsController accountsController;
	
	public EventController() {
		accountsController = new AccountsController();
	}
	
	@FXML protected void tryLogin(ActionEvent e) throws IOException {
		
//		String username = getTextFieldById("username_input").getText();
		
//		String password = ( getPasswordFieldById("password_input") ).getText();
//		LoginRequest loginRequest = new LoginRequest(username, password);
//
//		if (User.authenticate(loginRequest).isAuthenticated()) {
//			User currentUser = User.getUserById(loginRequest.getUserID());
//			getMain().setCurrentUser(currentUser);
//			getMain().transitionLoginSuccess();
//		}
//		else {
//			getMain().transitionLoginFailed();
//		}
//		System.out.println("This Worked");
		getGridPaneById("login_pane_root").setVisible(false);
		getAnchorPaneById("dashboard").setDisable(false);
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
			    getLabelById("create_account_error_text").setText(error); 
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

}
