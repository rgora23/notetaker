package models;

import java.io.IOException;
import java.util.ArrayList;

import requestHelpers.LoginRequest;
import requestHelpers.RegistrationRequest;
import csv.CSVReader;
import csv.CSVRecord;
import csv.CSVWriter;

public class Account {

	ArrayList<Note> myNotes;
	String id;
	String username;
	String password;
	static String tablePath = "database/accounts_table";
	static String[] tableHeaders = {"id", "username", "password"};
	
	// This constructor is an example of an ORM.
	// Every instance variable is mapped to a column in the table.
	// There are getters and setters that can change the record
	// in an object oriented fashion. The save method will save the
	// changes back to the database.
	public Account(ArrayList<String> row) {
		this.id = row.get(0);
		this.username = row.get(1);
		this.password = row.get(2);
	}
	
	public Account(CSVRecord record) {
		this(record.getRow());
	}
	
	// Called by the controller after record object is ready to save.
	public void save() {
		// This method is missing validations. Need to validate uniqueness
		// of username and ensure password matches required format.
		// (also password needs to be a password salt + hash).
		CSVWriter writer = Account.constructWriter();
		CSVRecord record = writer.getRecordById(this.id);
		record.setValueAtField(this.username, "username");
		record.setValueAtField(this.password, "password");
		writer.write();
	}
	
	private static CSVReader constructReader() {
		CSVReader reader = new CSVReader(tablePath);
		reader.setHeaders(tableHeaders);
		reader.parse();
		return reader;
	}
	
	private static CSVWriter constructWriter() {
		CSVWriter writer = new CSVWriter(tablePath);
		writer.setHeaders(tableHeaders);
		writer.parse();
		return writer;
	}
	
	public static Account getAccountById(String id) {
		// Query the users table for the username with ID
		CSVReader reader = constructReader();
		ArrayList<String> row = reader.getRecordById(id).getRow();
		return new Account(row);
	}

	public static LoginRequest authenticate(LoginRequest request) {
		// This is the part where I actually need to reference the users table
		// and determine whether the current user provided legitimate account credentials.
		// If valid, set request's authenticated to true and userID to the matching userID.
		CSVReader reader = constructReader();
		ArrayList<CSVRecord> records = reader.where("username", request.getUsername());
		CSVRecord record = records.get(0);
		Account account = new Account(record);
		// logic for authentication goes here
		request.setAuthenticated(true);
		return request;
	}
	
	
	
	public static RegistrationRequest register(RegistrationRequest request) throws IOException {
		CSVWriter accountTableWriter = constructWriter();
		
		if ( !request.getPassword().equals(request.getConfirmPassword()) ) {
			request.getErrors().add("Passwords don't match");
		}
		
		if ( request.getUsername().isEmpty() ) {
			request.getErrors().add("Username field is empty");
		}
		
		if ( request.getPassword().isEmpty() ) {
			request.getErrors().add("Password field is empty");
		}
		
		if ( request.getConfirmPassword().isEmpty() ) {
			request.getErrors().add("Password confirmation field is empty");
		}
		
		if ( !accountTableWriter.validateUniqueness("username", request.getUsername()) ) {
			request.getErrors().add("Username is taken.");			
		}
		
		if ( request.getErrors().isEmpty() ) {
			accountTableWriter.append(request.getUsername(), request.getPassword());
			accountTableWriter.write();
		}
		
		return request;
	}

		
}
