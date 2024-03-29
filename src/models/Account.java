package models;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import noteTaker.ErrorMessages;
import noteTaker.PasswordSecurityFactory;
import requestHelpers.ChangePasswordRequest;
import requestHelpers.LoginRequest;
import requestHelpers.RegistrationRequest;
import csv.CSVReader;
import csv.CSVRecord;
import csv.CSVWriter;

public class Account extends Model {

	private String id;
	private String username;
	private String password;
	private static String tableName = "accounts_table";
	private static String[] tableHeaders = { "id", "username", "salt", "hash" };

	private ArrayList<Note> accountNotes;

	// This constructor is an example of an ORM.
	// Every instance variable is mapped to a column in the table.
	// There are getters and setters that can change the record
	// in an object oriented fashion. The save method will save the
	// changes back to the database.
	/**
	 * Construction method for an Account object
	 * 
	 * @param record
	 *            the corresponding record found in the accounts table
	 */
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

	/**
	 * Find an account record in the accounts_table and return it's field values
	 * in an array
	 * 
	 * @param id
	 *            the id of the account in the accounts_table
	 * @return the field values of the account in an array
	 */
	public static Account getAccountById(String id) {
		// Query the users table for the username with ID
		CSVReader reader = constructReader();
		ArrayList<String> row = reader.getRecordById(id).getRow();
		return new Account(row);
	}

	/**
	 * Authenticates a user trying to log in
	 * 
	 * This method uses the data found in the LoginRequest object provided as a
	 * parameter to check if the user trying to login has credentials that match
	 * a record in the accounts_table. It does this by first checking to see if
	 * the username provided exists in the the username field of the
	 * accounts_table. If it does exist, it will return an array with accounts
	 * that have the provided username as a value in their username field. Since
	 * there can only be one account with that username, the array length should
	 * be one. If the array length is zero, it means an account does not exist
	 * with the provided username. The next step of authentication is to check
	 * to see if the provided password is correct. The method does this by using
	 * the password provided in the LoginRequest object and the salt found in
	 * the retrieved table record and passing them in to a hash function. If the
	 * result returned by the hash function matches the value found in the hash
	 * field of the retrieved account record, the password found in the
	 * LoginRequest object is valid and the user is authenticated.
	 * 
	 * @param request
	 *            LoginRequest object that provides the credentials of the user
	 *            trying to log in
	 */
	public static void authenticate(LoginRequest request) {
		// Try to find specified account
		CSVReader reader = constructReader();

		// Get only the records that match the given username
		CSVReader filteredReader = reader.where("username").is(request.getUsername());

		// Get the ArrayList of CSVRecord objects belonging to the filtered
		// reader
		ArrayList<CSVRecord> matchingRecords = filteredReader.getTable();
		if (matchingRecords.size() > 0) {
			// Get first record in match (there should only be one)
			CSVRecord record = matchingRecords.get(0);
			String recordSalt = record.getValueAtField("salt");
			String recordHash = record.getValueAtField("hash");
			String hashGenerated = PasswordSecurityFactory.getSHA1Hash(request.getPassword(), recordSalt);
			boolean authenticated = hashGenerated.equals(recordHash);

			if (authenticated)
				request.setAccount(new Account(record));
			request.setAuthenticated(authenticated);
		}

	}

	/**
	 * Creates an account and adds a record to the accounts_table
	 * 
	 * This method handles registration in the NoteTaker application. The first
	 * step of registration is to determine if all the necessary fields
	 * associated with registration have been filled. If any one of these fields
	 * is left empty, an item is added to the getErrors array found in the
	 * passed RegistrationRequest object. The method also checks to see if the
	 * password and confirmation password are the same. If not, another item is
	 * added to the getErrors array. If the getErrors array has a length greater
	 * than zero, the registration process can not proceed. Once it is
	 * determined that the getErrors array has a length of zero, a salt is
	 * generated for the new account. Once it is generated, the salt and the
	 * password provided by the RegistrationRequest object and passed in to a
	 * hash function and generate a hash value. After this is done, the username
	 * and password provided by the RegistrationRequest object and the generated
	 * salt and hash are added as a new record in the accounts table(the append
	 * method handles indexing automatically).
	 * 
	 * @param request
	 *            the RegistrationRequest object that provides the data for
	 *            registration
	 * @throws IOException
	 */
	public static void register(RegistrationRequest request) throws IOException {
		CSVWriter accountTableWriter = constructWriter();

		if (!request.getPassword().equals(request.getConfirmPassword())) {
			request.getErrors().add(ErrorMessages.REGISTRATION_PASSWORD_MISMATCH);
		}

		if (request.getUsername().isEmpty()) {
			request.getErrors().add(ErrorMessages.REGISTRATION_PROVIDE_USERNAME);
		}

		if (request.getPassword().isEmpty()) {
			request.getErrors().add(ErrorMessages.REGISTRATION_PROVIDE_PASSWORD);
		}

		if (request.getConfirmPassword().isEmpty()) {
			request.getErrors().add(ErrorMessages.REGISTRATION_PROVIDE_CONFIRM_PASSWORD);
		}

		if (!accountTableWriter.validateUniqueness("username", request.getUsername())) {
			request.getErrors().add(ErrorMessages.REGISTRATION_USERNAME_TAKEN);
		}

		if (request.getErrors().isEmpty()) {
			String newSalt = null;
			try {
				newSalt = PasswordSecurityFactory.getSalt();
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (newSalt != null) {
				String newHash = PasswordSecurityFactory.getSHA1Hash(request.getPassword(), newSalt);
				accountTableWriter.append(request.getUsername(), newSalt, newHash);
				accountTableWriter.write();
			}
		}

	}
	
	/**
	 * The method that handles changing a password
	 * 
	 * This method is called when the user wishes to change his/her password. It handles errors created by the 
	 * user when they enter incorrect information in to the password change form. It checks to see that all 
	 * necessary fields in the password change form are filled, the new password and confirm new password are 
	 * the same, and that the new password is not the old password. If any of these violations are detected, the 
	 * array inside the request object is populated. If the errors array is empty, the method continues by
	 * retrieving the user's current salt and uses that salt and the new password to create a new hash. It then
	 * updates the user's hash field with the newly generated hash.
	 * 
	 * @param request The ChangePasswordRequest object that provides the information needed to change a password
	 * 
	 */
	public static void changePassword(ChangePasswordRequest request){
		CSVWriter accountTableWriter = constructWriter();
		CSVReader accountTableReader = constructReader();

		if (!request.getNewPassword().equals(request.getConfirmNewPassword())) {
			request.getErrors().add(ErrorMessages.CHANGE_PASSWORD_MISMATCH);
		}

		if (request.getNewPassword().isEmpty()) {
			request.getErrors().add(ErrorMessages.NEW_PASSWORD_FIELD_EMPTY);
		}

		if (request.getConfirmNewPassword().isEmpty()) {
			request.getErrors().add(ErrorMessages.CONFIRM_NEW_PASSWORD_FIELD_EMPTY);
		}

		if (request.getOldPassword().equals(request.getNewPassword())) {
			request.getErrors().add(ErrorMessages.PASSWORD_IN_USE);
		}

		if (request.getErrors().isEmpty()) {
			CSVReader filteredReader = accountTableReader.where("id").is(request.getAccountId());
			ArrayList<CSVRecord> matchingRecords = filteredReader.getTable();
			CSVRecord userRecord = matchingRecords.get(0);
			String recordSalt = userRecord.getValueAtField("salt");
			
			//Generating new hash and adding to table
			String newHash = PasswordSecurityFactory.getSHA1Hash(request.getNewPassword(), recordSalt);
			for(CSVRecord r : accountTableWriter.getTable()) {
				if(r.getId().equals(request.getAccountId())) {
					r.setValueAtField(newHash, "hash");
					break;
				}
			}
			accountTableWriter.write();
		}
	}

	private static CSVWriter constructWriter() {
		return Model.constructWriter(tableName, tableHeaders);
	}

	private static CSVReader constructReader() {
		return Model.constructReader(tableName, tableHeaders);
	}

	public String getUsername() {
		return this.username;
	}

	public String getID() {
		return this.id;
	}

	public ArrayList<Note> getNotes() {
		return accountNotes;
	}

	public void updateNotes() {
		this.accountNotes = Note.getAccountNotes();
	}

}




