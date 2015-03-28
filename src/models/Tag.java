package models;

import csv.CSVReader;
import csv.CSVWriter;

public class Tag {
	String title;
	String id;
	static String tablePath = "database/tags_table";
	static String[] tableHeaders = {"id", "name"}; 
	
	public Tag(String title) {
		this.title = title;
	}
	
	private static CSVWriter constructWriter() {
		return Model.constructWriter(tablePath, tableHeaders);
	}
	
	private static CSVReader constructReader() {
		return Model.constructReader(tablePath, tableHeaders);
	}
}
