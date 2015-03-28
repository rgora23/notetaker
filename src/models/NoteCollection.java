package models;

import java.util.ArrayList;

import csv.CSVReader;
import csv.CSVWriter;

public class NoteCollection {
	ArrayList<Note> notesCollection;
	String id;
	static String tablePath = "database/note_collections_table";
	static String[] tableHeaders = {"id", "name", "user_id"};
	
	public NoteCollection() {
		
	}
	
	private static CSVWriter constructWriter() {
		return Model.constructWriter(tablePath, tableHeaders);
	}
	
	private static CSVReader constructReader() {
		return Model.constructReader(tablePath, tableHeaders);
	}
}
