package helpers;

import java.util.ArrayList;

public class RegistrationRequest {

	String username;
	String password;
	String confirm_password;
	ArrayList<String> errors;
	
	public RegistrationRequest(String username, String password, String confirm_password) {
		this.username = username;
		this.password = password;
		this.confirm_password = confirm_password;
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

	public String getConfirm_password() {
		return confirm_password;
	}

	public void setConfirm_password(String confirm_password) {
		this.confirm_password = confirm_password;
	}

	public ArrayList<String> getErrors() {
		return errors;
	}

	public void setErrors(ArrayList<String> errors) {
		this.errors = errors;
	}
	
	
	
	
	
}
