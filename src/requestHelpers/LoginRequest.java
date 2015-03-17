package requestHelpers;

import models.Account;

// This is an object that holds the values of a login request. 
// It can be referenced to get authentication status and possible error messages.

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
