package models;

import noteTaker.Session;
import csv.CSVReader;
import csv.CSVWriter;

public abstract class Model {
	String dateCreated;
	String dateModified;

	public String getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getDateModified() {
		return dateModified;
	}

	public void setDateModified(String dateModified) {
		this.dateModified = dateModified;
	}

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

	protected static Session getSession() {
		return Session.getInstance();
	}
}
