package models;

import csv.CSVReader;
import csv.CSVWriter;
import requestHelpers.NoteCreationRequest;


public class Note {
	
	static String tablePath = "database/notes_table";
	static String[] tableHeaders = {"id", "title", "account_id"};
	
	public Note(String title) {
		
	}

	public static void create(NoteCreationRequest request) {
		CSVWriter writer = constructWriter();
		
		
		
	}
	
	
	private static CSVWriter constructWriter() {
		return Model.constructWriter(tablePath, tableHeaders);
	}
	
	private static CSVReader constructReader() {
		return Model.constructReader(tablePath, tableHeaders);
	}
	
}
