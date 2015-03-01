package models;

import helpers.LoginRequest;
import helpers.RegistrationRequest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

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
	
	public static RegistrationRequest register(RegistrationRequest request) throws IOException {
		boolean uniqueUserName;
		String tablePath = "database/accounts_table";
		CsvReader accountTableReader = new CsvReader(tablePath);
		
		if(!request.getPassword().equals(request.getConfirmPassword())) {
			request.getErrors().add("Passwords don't match");
		}
		
		if(request.getUsername().isEmpty()) {
			request.getErrors().add("Username field is empty");
		}
		
		if(request.getPassword().isEmpty()) {
			request.getErrors().add("Password field is empty");
		}
		
		if(request.getConfirmPassword().isEmpty()) {
			request.getErrors().add("Password confirmation field is empty");
		}
		
		if(!checkUniquenessOf(1, request.getUsername(), tablePath))
		{
			request.getErrors().add("Username is taken.");
		}
		
		accountTableReader.close();
		
		if(request.getErrors().isEmpty())
		{
			CsvWriter accountTableWriter = new CsvWriter(tablePath);
			String[] recordInfo = {getNewID(tablePath), request.getUsername(), request.getPassword()};
			accountTableWriter.endRecord();
			accountTableWriter.writeRecord(recordInfo);
			accountTableWriter.close();
		}
		
		
		return request;
	}
	
	//This method checks the uniqueness of an element in the table given the column number
	public static boolean checkUniquenessOf(int columnNum, String field, String tablePath) throws FileNotFoundException {
		boolean unique = true;
		CsvReader tableReader = new CsvReader(tablePath);
		try {
			while(tableReader.readRecord()) {
				if(field.equalsIgnoreCase(tableReader.get(columnNum)))
				{
					unique = false;
					break;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		tableReader.close();
		return unique;
	}
	
	public static String getLastLine(String tablePath) throws FileNotFoundException{
		String content = "0";
		try{
			content = new Scanner(new File(tablePath)).useDelimiter("\\Z").next();
		} catch(NoSuchElementException e){ }
		
		String[] lines = content.split("\n");
		return lines[lines.length - 1];
	}
	
	public static String getNewID(String tablePath) throws FileNotFoundException {
		String lastLineString = getLastLine(tablePath);
		CsvReader lastLine = CsvReader.parse(lastLineString);
		
		int lastID;
		try {
			lastLine.readRecord();
			lastID = Integer.parseInt(lastLine.get(0));
		} catch (NumberFormatException | IOException e) {
			lastID = -1;
		}
		
		lastLine.close();
		return (lastID + 1) + "";
	}
	
}
