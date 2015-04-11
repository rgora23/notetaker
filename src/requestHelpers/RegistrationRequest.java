package requestHelpers;

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
