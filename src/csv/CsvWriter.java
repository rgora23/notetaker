package csv;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;


public class CsvWriter extends CsvReader {

	/**
	 * Creates a new object that can write changes to the database
	 * table provided.
	 * @param path   The path to the table location.
	 * @author Brian Maxwell
	 */
	public CsvWriter(String path) {
		super(path);
	}
	
	/**
	 * Creates a new object that can write changes to the database
	 * table provided. Provided headers override default headers.
	 * @param path   The path to the table location.
	 * @param headers   The table headers.
	 * @author Brian Maxwell
	 */
	public CsvWriter(String path, String... headers) {
		super(path, headers);
	}

	/**
	 * Adds a new row to the table. Will add ID if it is not included.
	 * @param row   The row to add as a string, split on the set delimiter.
	 * @author Brian Maxwell
	 */
	public void append(String row) {
		append(row.split(delimiter));
	}

	/**
	 * Adds a new row to the table. Will add ID if it is not included.
	 * @param row   String array representing the columns of the row.
	 * @author Brian Maxwell
	 * 
	 */
	public void append(String... values) {
		ArrayList<String> row = new ArrayList<String>();
		for (String value : values) row.add(value);
		append(row);
	}
	
	/**
	 * Adds a new row to the table. Will add ID if it is not included.
	 * @param row   ArrayList of strings representing the columns of the row.
	 * @return Whether the record was written or not.
	 * @author Brian Maxwell
	 */
	public boolean append(ArrayList<String> row) {
		
		// If we are one item short we assume the ID was
		// left out and add it to the beginning of the row.
		if (row.size() + 1 == headers.size()) row.add(0, getNextId());
		
		// otherwise do not write the new record and log error
		else if (row.size() != headers.size()) {
			System.out.println("The required headers are: " + headers);
			System.out.println("You provided " + row.size() + " fields");
			return false;
		}
		
		// The correct amount of fields have been provided, so
		// attempt to write the new record as long as the id is unique.
		boolean uniqueId = true;
		CsvRecord newRecord = new CsvRecord(row);
		if (validateUniqueness("id", newRecord.getId())) table.add(new CsvRecord(row));
		else System.out.println("The id " + row.get(0) + " has already been allocated.");
		return uniqueId;
	}
	
	/**
	 * Removes record with supplied ID.
	 * @param id   the ID to identify the record.
	 * @return The removed record or null if none is found.
	 * @author Brian Maxwell
	 */
	public CsvRecord removeRecord(int id) {
		String idString = id + "";
		CsvRecord removedRecord = null;
		for (CsvRecord record : table) {
			if ( CsvHelpers.checkEquality(idString, record.getId()) ) {
				removedRecord = record;
				table.remove(record);
				break;
			}
		}
		return removedRecord;
	}

	/**
	 * Write database changes to file. This method
	 * must be called in order for any changes to take effect.
	 * @author Brian Maxwell
	 */
	public void save() {
		PrintWriter writer;
		try {
			writer = new PrintWriter(file);
			writer.print(this.toString());
			writer.close();
		} catch (IOException e) { }
	}

}
