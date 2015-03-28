package models;

import csv.CSVReader;
import csv.CSVWriter;
import requestHelpers.NoteCreationRequest;


public class Note {
	
	String title;
	String id;
	String account_id;
	static String tablePath = "database/notes_table";
	static String[] tableHeaders = {"id", "title", "account_id"};
	
	public Note(String title) {
		
	}

	public static void create(NoteCreationRequest request) {
		CSVWriter noteTableWriter = constructWriter();
		noteTableWriter.append(request.getTitle(), request.getAccountId());
		noteTableWriter.write();
	}
	
	private static CSVWriter constructWriter() {
		return Model.constructWriter(tablePath, tableHeaders);
	}
	
	private static CSVReader constructReader() {
		return Model.constructReader(tablePath, tableHeaders);
	}
	
}
