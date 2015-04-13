package requestHelpers;

/**
 * This class inherits directly from the Request class. Objects of this class
 * are created when a user attempts to register for an account. This class
 * provides data to the Account class' register method. The username instance
 * variable represents the account username the user would like to have and the
 * password and confirmPassword instance variables are used to make sure the
 * user entered his/her desired password correctly. The password instance
 * variable is also passed in to the getSalt and getSHA1Hash methods found in
 * the PasswordSecurityFactory class. It also holds the error messages produced
 * during an unsuccessful registration through usage of the errors ArrayList it
 * inherits from the Request class.
 */
public class RegistrationRequest extends Request {

	String username;
	String password;
	String confirmPassword;

	public RegistrationRequest(String username, String password, String confirm_password) {
		this.username = username;
		this.password = password;
		this.confirmPassword = confirm_password;
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

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

}




