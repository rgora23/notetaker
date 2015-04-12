package requestHelpers;

import java.util.ArrayList;
/**
 * This class is the abstract class that many of the request classes found in
 * the requestHelpers package inherits from. It provides an array list object
 * that will be populated by any errors that occur during the creation of a
 * request. These errors are provided by the constant String objects found
 * in the ErrorMessages class in the noteTaker package.
 */
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




