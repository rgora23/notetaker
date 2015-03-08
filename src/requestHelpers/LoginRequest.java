package requestHelpers;


// This is an object that holds the value of a login request. 
// It can be referenced to get authentication status and possible error messages.

public class LoginRequest extends Request {

	String username;
	String password;
	String userID;
	boolean authenticated;
	
	public LoginRequest(String username, String password) {
		setAuthenticated(false);
		setUsername(username);
		setPassword(password);
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

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

}
