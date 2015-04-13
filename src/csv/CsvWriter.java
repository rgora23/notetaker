package csv;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * This class inherits directly from the CSVReader class and is a grand child of
 * the CSVParser class. NoteTaker uses CSV files to store data and this class is
 * used to write data on to those files. It has the ability to remove records
 * from the database as well as write records to the database. During the
 * writing process, if a record id is not supplied, it will intelligently add
 * one for you. When writing CSV files, fields are separated by double commas.
 * 
 * @author Brian Maxwell
 * 
 */
public class CSVWriter extends CSVReader {

	/**
	 * Creates a new object that can write changes to the database table
	 * provided.
	 * 
	 * @param path
	 *            The path to the table location.
	 * @author Brian Maxwell
	 */
	public CSVWriter(String path) {
		super(path);
	}

	/**
	 * Creates a new object that can write changes to the database table
	 * provided. Provided headers override default headers.
	 * 
	 * @param path
	 *            The path to the table location.
	 * @param headers
	 *            The table headers.
	 * @author Brian Maxwell
	 */
	public CSVWriter(String path, String... headers) {
		super(path, headers);
	}

	/**
	 * Adds a new row to the table. Will add ID if it is not included.
	 * 
	 * @param row
	 *            The row to add as a string, split on the set delimiter.
	 * @author Brian Maxwell
	 */
	public void append(String row) {
		append(row.split(delimiter));
	}

	/**
	 * Adds a new row to the table. Will add ID if it is not included.
	 * 
	 * @param row
	 *            String array representing the columns of the row.
	 * @author Brian Maxwell
	 * 
	 */
	public void append(String... values) {
		ArrayList<String> row = new ArrayList<String>();
		for (String value : values)
			row.add(value);
		append(row);
	}

	/**
	 * Adds a new row to the table. Will add ID if it is not included.
	 * 
	 * @param row
	 *            ArrayList of strings representing the columns of the row.
	 * @return Whether the record was written or not.
	 * @author Brian Maxwell
	 */
	public boolean append(ArrayList<String> row) {

		// If we are one item short we assume the ID was
		// left out and add it to the beginning of the row.
		if (row.size() + 1 == headers.size())
			row.add(0, getNextId());

		// otherwise do not write the new record and log error
		else if (row.size() != headers.size()) {
			System.out.println("The required headers are: " + headers);
			System.out.println("You provided " + row.size() + " fields");
			return false;
		}

		// The correct amount of fields have been provided, so
		// attempt to write the new record as long as the id is unique.
		boolean uniqueId = true;
		CSVRecord newRecord = new CSVRecord(row);
		if (validateUniqueness("id", newRecord.getId()))
			table.add(new CSVRecord(row));
		else
			System.out.println("The id " + row.get(0)
					+ " has already been allocated.");
		return uniqueId;
	}

	/**
	 * Removes record with supplied ID.
	 * 
	 * @param id
	 *            the ID to identify the record.
	 * @return The removed record or null if none is found.
	 * @author Brian Maxwell
	 */
	public CSVRecord removeRecord(String id) {
		CSVRecord removedRecord = null;
		for (CSVRecord record : table) {
			if (CSVHelper.checkEquality(id, record.getId())) {
				removedRecord = record;
				table.remove(record);

				break;
			}
		}
		return removedRecord;

	}

	public CSVRecord removeRecord(int id) {
		return removeRecord(id + "");
	}

	public CSVRecord removeRecord(CSVRecord r) {
		return removeRecord(r.getId());
	}

	/**
	 * Write database changes to file. This method must be called in order for
	 * any changes to take effect.
	 * 
	 * @author Brian Maxwell
	 */
	public void write() {
		PrintWriter writer;
		try {
			writer = new PrintWriter(file);
			writer.print(this.toString());
			writer.close();
		} catch (IOException e) {
		}
	}

}
