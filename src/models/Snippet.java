package models;

import java.util.ArrayList;

import csv.CSVReader;
import csv.CSVWriter;

public class Snippet {
	
	ArrayList<Tag> tags;
	String id;
	static String tablePath = "database/snippets_table";
	static String[] tableHeaders = {"name"};
	
	public Snippet() {
		tags = new ArrayList<Tag>();
	}

	private static CSVWriter constructWriter() {
		return Model.constructWriter(tablePath, tableHeaders);
	}
	
	private static CSVReader constructReader() {
		return Model.constructReader(tablePath, tableHeaders);
	}
}
