package requestHelpers;

import java.util.ArrayList;

public abstract class Request {

	ArrayList<String> errors;
	
	public Request() {
		this.errors = new ArrayList<String>();
	}
	

	public ArrayList<String> getErrors() {
		return errors;
	}
	
	public void addError(String message) {
		this.errors.add(message);
	}
	
	public boolean noErrors() {
		return this.errors.isEmpty();
	}

	public boolean hasErrors() {
		return (!noErrors());
	}
	
}




