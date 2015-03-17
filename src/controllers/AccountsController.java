package controllers;

import java.io.IOException;
import java.util.ArrayList;

import models.Account;
import noteTaker.Session;
import requestHelpers.LoginRequest;
import requestHelpers.RegistrationRequest;

public class AccountsController extends Controller {


	public AccountsController() {

	}

	public RegistrationRequest register(String username, String password, String confirmPassword) throws IOException {
		RegistrationRequest request = new RegistrationRequest(username, password, confirmPassword);
		return Account.register(request);
	}

	public static String[] login(String username, String password) {
		LoginRequest loginRequest = new LoginRequest(username, password);

		// Validate the provided fields before querying database.
		if (username.isEmpty()) loginRequest.addError("You must provide a username.");
		if (password.isEmpty()) loginRequest.addError("You must provide a password.");

		// Try to authenticate user if provided fields are valid.
		if (loginRequest.noErrors()) {
			Account.authenticate(loginRequest);
			if (loginRequest.isAuthenticated()) {
				Session.create(loginRequest.getAccount());
			}
			else {
				loginRequest.addError("The account specified was not found");
			}
		}

		ArrayList<String> errorsArrayList = loginRequest.getErrors();
		
		// Send String array of errors back to the view controller
		String[] errors = errorsArrayList.toArray(new String[errorsArrayList.size()]);
		return errors;
	}

}
