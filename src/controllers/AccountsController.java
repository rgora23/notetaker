package controllers;

import java.io.IOException;

import models.Account;
import models.Note;
import noteTaker.ErrorMessages;
import noteTaker.Session;
import requestHelpers.LoginRequest;
import requestHelpers.RegistrationRequest;

public class AccountsController extends Controller {

	/**
	 * The method that handles registration logic
	 * 
	 * This method is called when the user is trying to register. It creates a
	 * RegistrationRequest object using the user's desired username and password
	 * as well as the password confirmation String. It returns a string array
	 * that contains error messages that are created when the user incorrectly
	 * inputs information.
	 * 
	 * @param username
	 *            The username the user wants to register with
	 * @param password
	 *            The password the user wants to register with
	 * @param confirmPassword
	 *            A confirmation password to make sure the inputted password is
	 *            correct
	 * @return An array that contains error messages created by the user
	 * @throws IOException
	 */
	public String[] register(String username, String password, String confirmPassword) throws IOException {
		RegistrationRequest request = new RegistrationRequest(username, password, confirmPassword);
		Account.register(request);
		String[] errors = ArrayListToStringArray(request.getErrors());
		return errors;
	}

	/**
	 * The method that handles exceptions that occur during login
	 * 
	 * This method is called when the user is trying to log in. It creates a
	 * LoginRequest object using the username and password passed as
	 * construction parameters. It then checks for errors commited by the user
	 * such as leaving the username field empty or the password field empty. It
	 * relies on error messages supplied by the ErrorMessages class found in the
	 * noteTaker package and populates the loginRequest errors array with these
	 * errors. It also handles logging out with the logout method.
	 * 
	 * @param username
	 *            The username that is provided by the login view
	 * @param password
	 *            The password that is provided by the login view
	 * @return Errors that are committed by the user
	 */
	public static String[] login(String username, String password) {
		LoginRequest loginRequest = new LoginRequest(username, password);

		// Validate the provided fields before querying database.
		if (username.isEmpty()) loginRequest.addError(ErrorMessages.LOGIN_PROVIDE_USERNAME);
		if (password.isEmpty()) loginRequest.addError(ErrorMessages.LOGIN_PROVIDE_PASSWORD);

		// Try to authenticate user if provided fields are valid.
		if (loginRequest.noErrors()) {
			Account.authenticate(loginRequest);
			if (loginRequest.isAuthenticated()) {
				getSession().create(loginRequest.getAccount());
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
	
	/**
	 * The controller method for logging out
	 * 
	 * This method is called when the user is logging out of noteTaker. It
	 * destroys the session and displays a message to the user stating that the
	 * user has successfully logged out.
	 */
	public static void logout() {
		String username = getSession().getAccount().getUsername();
		getSession().destroy();
		System.out.println("Logged out of " + username + "'s account.");
	}

}




