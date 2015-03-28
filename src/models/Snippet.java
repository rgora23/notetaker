package models;

import java.util.ArrayList;

import requestHelpers.SnippetCreationRequest;
import csv.CSVReader;
import csv.CSVWriter;

public class Snippet {
	
	ArrayList<Tag> tags;
	String id;
	static String tablePath = "database/snippets_table";
	static String[] tableHeaders = {"id", "title", "note_id", "content"};
	
	public Snippet() {
		tags = new ArrayList<Tag>();
	}
	
	public static void create(SnippetCreationRequest request) {
		
	}

	private static CSVWriter constructWriter() {
		return Model.constructWriter(tablePath, tableHeaders);
	}
	
	private static CSVReader constructReader() {
		return Model.constructReader(tablePath, tableHeaders);
	}
}
