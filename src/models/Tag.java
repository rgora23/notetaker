package models;

import requestHelpers.TagCreationRequest;
import csv.CSVReader;
import csv.CSVWriter;

public class Tag {
	String title;
	String id;
	static String tablePath = "database/tags_table";
	static String[] tableHeaders = {"id", "name", "snippet_id"}; 
	
	public Tag(String title) {
		this.title = title;
	}
	
	public static void create(TagCreationRequest request){
		
	}
	
	private static CSVWriter constructWriter() {
		return Model.constructWriter(tablePath, tableHeaders);
	}
	
	private static CSVReader constructReader() {
		return Model.constructReader(tablePath, tableHeaders);
	}
}
