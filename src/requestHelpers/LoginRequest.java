package requestHelpers;

import models.Account;

/**
 * This class inherits directly from the Request class. When a user attempts to
 * login, an object of this class is created and the data found in the object is
 * used by the Account model class for authentication. This method can be used
 * to get the users authentication status(using the boolean instance variable
 * authenticated) and also display possible error messages through usage of the 
 * errors Array List it inherits from the Request class.
 */
public class LoginRequest extends Request {

	String username;
	String password;
	String userID;
	Account account;
	boolean authenticated;

	public LoginRequest(String username, String password) {
		setUsername(username);
		setPassword(password);

		// These variables must be validated against Account model
		// through its controller
		setAuthenticated(false);
		setAccount(null);
		setUserID(null);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isAuthenticated() {
		return authenticated;
	}

	public void setAuthenticated(boolean authenticated) {
		this.authenticated = authenticated;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Account getAccount() {
		return this.account;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

}




