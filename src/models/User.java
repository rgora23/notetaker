package models;

import helpers.LoginRequest;
import helpers.RegistrationRequest;

import java.util.ArrayList;

import csv.CsvReader;
import csv.CsvRecord;

public class User {

	ArrayList<Note> myNotes;
	String id;
	String username;
	String password;
	static String tablePath = "database/accounts_table";
	public User(ArrayList<String> row) {
		this.id = row.get(0);
		this.username = row.get(1);
		this.password = row.get(2);
	}
	
	public static User getUserById(String id) {
		// This needs to query the users table for the username with ID
		CsvReader reader = new CsvReader(tablePath);
		reader.setHeaders("id", "username", "password");
		ArrayList<String> row = reader.getRecordById(id).getRow();
		return new User(row);
	}

	public static LoginRequest authenticate(LoginRequest request) {
		// This is the part where I actually need to reference the users table
		// and determine whether the current user is valid.
		// If valid, set authenticated to true and set userID to the matching userID
		
		CsvReader reader = new CsvReader(tablePath);
		reader.setHeaders("id", "username", "password");
		ArrayList<CsvRecord> records = reader.where("username", request.getUsername());
		String userID = records.get(0).getValueAtField("id");
		request.setAuthenticated(true);
		request.setUserID(userID);
		return request;
	}
	
	public static RegistrationRequest register(RegistrationRequest request) {
		
		
		return request;
	}
	
}
