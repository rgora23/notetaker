package noteTaker;
/**
 * This class consists exclusively of constant String variables. The purpose of
 * this class is to provide customized error messages for all other classes.
 * These error messages are displayed in the various forms and interfaces of the
 * program and also populate the errors Array list inherited by many of the classes 
 * in the requestHelpers package.
 * 
 * @author Brian Maxwell
 * 
 */
public class ErrorMessages {

	// Login error messages
	public static final String LOGIN_PROVIDE_USERNAME = "You must provide a username.";
	public static final String LOGIN_PROVIDE_PASSWORD = "You must provide a password.";
	public static final String LOGIN_ACCOUNT_NOT_FOUND = "The specified account does not exist.";

	// Registration error messages
	public static final String REGISTRATION_PROVIDE_USERNAME = "Username field is empty";
	public static final String REGISTRATION_PROVIDE_PASSWORD = "Password field is empty";
	public static final String REGISTRATION_PROVIDE_CONFIRM_PASSWORD = "Password confirmation field is empty";
	public static final String REGISTRATION_USERNAME_TAKEN = "Username is taken.";
	public static final String REGISTRATION_PASSWORD_MISMATCH = "The passwords don't match.";

	// Note creation error messages
	public static final String NOTE_TITLE_TAKEN = "You already have a note with this same title.";
	public static final String NOTE_COLLECTION_TITLE_TAKEN = "You already have a collection with this same title.";
}




