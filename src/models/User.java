package models;

import helpers.LoginRequest;
import helpers.RegistrationRequest;

import java.util.ArrayList;

public class User {

	ArrayList<Note> myNotes;
	
	public User() {
		
	}
	
	public static User getUserById(int ID) {
		// This needs to query the users table for the username with ID
		return new User();
	}

	public static LoginRequest authenticate(LoginRequest request) {
		// This is the part where I actually need to reference the users table
		// and determine whether the current user is valid.
		// If valid, set authenticated to true and set userID to the matching userID
		int userID = 0;
		request.setAuthenticated(true);
		request.setUserID(userID);
		return request;
	}
	
	public static RegistrationRequest register(RegistrationRequest request) {
		
		
		return request;
	}
	
}
