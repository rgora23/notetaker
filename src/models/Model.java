package models;

import csv.CSVReader;
import csv.CSVWriter;

public class Model {
	
	
	protected static CSVReader constructReader(String tablePath, String[] tableHeaders) {
		CSVReader reader = new CSVReader(tablePath);
		reader.setHeaders(tableHeaders);
		reader.parse();
		return reader;
	}
	
	protected static CSVWriter constructWriter(String tablePath, String[] tableHeaders) {
		CSVWriter writer = new CSVWriter(tablePath);
		writer.setHeaders(tableHeaders);
		writer.parse();
		return writer;
	}
}
