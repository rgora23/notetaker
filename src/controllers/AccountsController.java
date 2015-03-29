package controllers;

import java.io.IOException;

import models.Account;
import models.Note;
import noteTaker.ErrorMessages;
import noteTaker.Session;
import requestHelpers.LoginRequest;
import requestHelpers.RegistrationRequest;

public class AccountsController extends Controller {


	public AccountsController() {
		

	}

	public String[] register(String username, String password, String confirmPassword) throws IOException {
		RegistrationRequest request = new RegistrationRequest(username, password, confirmPassword);
		Account.register(request);
		String[] errors = ArrayListToStringArray(request.getErrors());
		return errors;
	}

	public static String[] login(String username, String password) {
		LoginRequest loginRequest = new LoginRequest(username, password);

		// Validate the provided fields before querying database.
		if (username.isEmpty()) loginRequest.addError(ErrorMessages.LOGIN_PROVIDE_USERNAME);
		if (password.isEmpty()) loginRequest.addError(ErrorMessages.LOGIN_PROVIDE_PASSWORD);

		// Try to authenticate user if provided fields are valid.
		if (loginRequest.noErrors()) {
			Account.authenticate(loginRequest);
			if (loginRequest.isAuthenticated()) {
				Session.create(loginRequest.getAccount());
				loginRequest.getAccount().updateNotes();
				System.out.println("Successfully logged in.");
			}
			else {
				loginRequest.addError(ErrorMessages.LOGIN_ACCOUNT_NOT_FOUND);
				System.out.println("Could not log in.");
			}
		}
		
		// Send String array of errors back to the view controller
		String[] errors = ArrayListToStringArray(loginRequest.getErrors());
		return errors;
	}
	
	public static void logout() {
		String username = Session.getAccount().getUsername();
		Session.destroy();
		System.out.println("Logged out of " + username + "'s account.");
	}

}
