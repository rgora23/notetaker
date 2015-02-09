package main;
import models.User;



public class NoteTakerMain {

	static User currentUser;
	
//	public static void main(String[] args) {
//
//		// User clicks to create a note
//		String title = "Physics";
//		Note thisNote = new Note(title);
//
//	}
	
	// Try to log in
	public static boolean tryLogin() {
		int userID = 1; // needs to be dynamic
		setUser(userID);
		return false;
	}
	
	// Query for a user
	public static User findUser(int userID) {
		User u = new User();
		return u;
	}
	
	// Set current user after login
	public static void setUser(int userID) {
		currentUser = findUser(userID);
	}

	
	
}
