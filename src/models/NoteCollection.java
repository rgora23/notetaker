package models;

import java.util.ArrayList;

import requestHelpers.NoteCollectionCreationRequest;
import csv.CSVReader;
import csv.CSVWriter;

public class NoteCollection {
	ArrayList<Note> notesCollection;
	String id;
	static String tablePath = "database/note_collections_table";
	static String[] tableHeaders = {"id", "title", "account_id"};
	
	public NoteCollection() {
		
	}
	
	public static void create(NoteCollectionCreationRequest request){
		
	}
	
	private static CSVWriter constructWriter() {
		return Model.constructWriter(tablePath, tableHeaders);
	}
	
	private static CSVReader constructReader() {
		return Model.constructReader(tablePath, tableHeaders);
	}
}
