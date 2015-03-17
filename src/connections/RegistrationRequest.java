package connections;

import java.util.ArrayList;

public class RegistrationRequest {

	String username;
	String password;
	String confirmPassword;
	ArrayList<String> errors;
	
	public RegistrationRequest(String username, String password, String confirm_password) {
		this.username = username;
		this.password = password;
		this.confirmPassword = confirm_password;
		this.errors = new ArrayList<String>();
		
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

	public ArrayList<String> getErrors() {
		return errors;
	}

	public void setErrors(ArrayList<String> errors) {
		this.errors = errors;
	}
	
	
	
	
	
}
