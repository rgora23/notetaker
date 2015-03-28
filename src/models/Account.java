package models;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import noteTaker.ErrorMessages;
import noteTaker.PasswordSecurityFactory;
import requestHelpers.LoginRequest;
import requestHelpers.RegistrationRequest;
import csv.CSVReader;
import csv.CSVRecord;
import csv.CSVWriter;

public class Account extends Model {

	private String id;
	private String username;
	private String password;
	private static String tablePath = "database/accounts_table";
	private static String[] tableHeaders = {"id", "username", "salt", "hash"};

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

	public String getUsername() {
		return this.username;
	}

	public String getID() {
		return this.id;
	}


	public static Account getAccountById(String id) {
		// Query the users table for the username with ID
		CSVReader reader = constructReader();
		ArrayList<String> row = reader.getRecordById(id).getRow();
		return new Account(row);
	}

	public static void authenticate(LoginRequest request) {
		// Try to find specified account
		CSVReader reader = constructReader();
		
		// Get only the records that match the given username
		CSVReader filteredReader = reader.where("username").is(request.getUsername());

		// Get the ArrayList of CSVRecord objects belonging to the filtered reader 
		ArrayList<CSVRecord> matchingRecords = filteredReader.getTable();
		if (matchingRecords.size() > 0) {
			// Get first record in match (there should only be one)
			CSVRecord record = matchingRecords.get(0);
			String recordSalt = record.getValueAtField("salt");
			String recordHash = record.getValueAtField("hash");
			String hashGenerated = PasswordSecurityFactory.getSHA1Hash(request.getPassword(), recordSalt);
			boolean authenticated = hashGenerated.equals(recordHash);

			if (authenticated) request.setAccount(new Account(record));
			request.setAuthenticated(authenticated);
		}

	}


	public static void register(RegistrationRequest request) throws IOException {
		CSVWriter accountTableWriter = constructWriter();

		if ( !request.getPassword().equals(request.getConfirmPassword()) ) {
			request.getErrors().add(ErrorMessages.REGISTRATION_PASSWORD_MISMATCH);
		}

		if ( request.getUsername().isEmpty() ) {
			request.getErrors().add(ErrorMessages.REGISTRATION_PROVIDE_USERNAME);
		}

		if ( request.getPassword().isEmpty() ) {
			request.getErrors().add(ErrorMessages.REGISTRATION_PROVIDE_PASSWORD);
		}

		if ( request.getConfirmPassword().isEmpty() ) {
			request.getErrors().add(ErrorMessages.REGISTRATION_PROVIDE_CONFIRM_PASSWORD);
		}

		if ( !accountTableWriter.validateUniqueness("username", request.getUsername()) ) {
			request.getErrors().add(ErrorMessages.REGISTRATION_USERNAME_TAKEN);			
		}

		if ( request.getErrors().isEmpty() ) {
			String newSalt = null;
			try {
				newSalt = PasswordSecurityFactory.getSalt();
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(newSalt != null) {
				String newHash = PasswordSecurityFactory.getSHA1Hash(request.getPassword(), newSalt);
				accountTableWriter.append(request.getUsername(), newSalt, newHash);
				accountTableWriter.write();
			}
		}

	}

	private static CSVWriter constructWriter() {
		return Model.constructWriter(tablePath, tableHeaders);
	}

	private static CSVReader constructReader() {
		return Model.constructReader(tablePath, tableHeaders);
	}

}
