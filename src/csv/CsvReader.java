package csv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class inherits directly from the CSVParser class. It grants the program
 * the ability to read CSV files and obtain different forms of data. The CSV
 * files used in the program are delimited by double commas. It retrieves CSV
 * Records from the various tables in the database using methods that take
 * various types of input. It also has the ability to check the uniqueness of a
 * value of data inside a record.
 * 
 * @author Brian Maxwell
 * 
 */
public class CSVReader extends CSVParser {

	protected File file;
	private String path;
	protected ArrayList<CSVRecord> table;

	static String delimiter = ",,";

	/**
	 * Creates a new object that can read the entire database and retrieve
	 * values. This is only suitable for small databases. Default headers are
	 * ["id"].
	 * 
	 * @param path
	 *            the path to the table
	 * @author Brian Maxwell
	 */
	public CSVReader(String path) {
		super();
		this.path = path;
	}

	/**
	 * Creates a new object that can read the entire database and retrieve
	 * values. This is only suitable for small databases. Provided headers
	 * override default headers.
	 * 
	 * @param path
	 *            the path to the table
	 * @param headers
	 *            the
	 * @author Brian Maxwell
	 */
	public CSVReader(String path, String... headers) {
		super(headers);
		this.path = path;
	}

	/**
	 * Finds the next ID to use in order to append a record to the table.
	 * 
	 * @return String representation of the next ID to use in the table.
	 */
	public String getNextId() {
		int lastId = -1;
		try {
			if (lastRecord() == null)
				lastId = 0;
			else
				lastId = Integer.parseInt(lastRecord().getId());
		} catch (NumberFormatException e) {
		}
		return (lastId + 1) + "";
	}

	/**
	 * Gets the last record in the table
	 * 
	 * @return CsvRecord object of last record in the table.
	 * @author Brian Maxwell
	 */
	public CSVRecord lastRecord() {
		if (table.size() == 0) {
			return null;
		} else {
			return table.get(table.size() - 1);
		}
	}

	/**
	 * Gets the first record in the table
	 * 
	 * @return CsvRecord object of first record in the table.
	 * @author Brian Maxwell
	 */
	public CSVRecord firstRecord() {
		return table.get(0);
	}

	/**
	 * Creates a CsvTable object from database table using the set delimiter.
	 * The table can be retrieved using a getter on the CsvReader object.
	 * 
	 * @return The created CsvTable object
	 * @author Brian Maxwell
	 */
	public ArrayList<CSVRecord> parse() {
		return createTableFromFile();
	}

	/**
	 * Searches through the table and returns a new filtered table where all the
	 * values of the supplied header hold the supplied value. <br>
	 * <div><strong>Example: </strong></div>
	 * 
	 * <pre>
	 * CsvReader reader = new {@link #CsvReader}("inventory_table", "name", "price", "category");
	 * reader.{@link #parse()};
	 * CsvTable cleaningProducts =  reader.{@link #where}("category", "cleaning");
	 * </pre>
	 * 
	 * @param header
	 *            The header of each record to compare the value to
	 * @param value
	 *            The value to match against
	 * @return A new CsvTable object holding matched records
	 * @author Brian Maxwell
	 */
	public ArrayList<CSVRecord> where(String header, String value) {
		ArrayList<CSVRecord> filteredTable = new ArrayList<CSVRecord>();
		for (CSVRecord record : table) {
			String headerValue = record.getValueAtField(header);
			if (CSVHelper.checkEquality(headerValue, value))
				filteredTable.add(record);
		}
		return filteredTable;
	}

	public CSVRecordList where(String field) {
		CSVRecordList fields = new CSVRecordList(headers, table, field, path);
		return fields;
	}

	/**
	 * Checks if the given value for a header has been taken for any of the
	 * records in the table.
	 * 
	 * @param header
	 *            The header of each record to compare the value to
	 * @param value
	 *            The value to match against
	 * @return returns if the value for the given header is unique.
	 * @author Brian Maxwell
	 */
	public boolean validateUniqueness(String header, String value) {
		return where(header, value).isEmpty();
	}

	/**
	 * Search the table for the record with the matching id
	 * 
	 * @param id
	 *            the integer id of the record to retrieve
	 * @return the matching record or null if nothing is found
	 * @author Brian Maxwell
	 */
	public CSVRecord getRecordById(int id) {
		return getRecordById(id + "");
	}

	/**
	 * Search the table for the record with the matching id
	 * 
	 * @param id
	 *            the string id of the record to retrieve
	 * @return the matching record or null if nothing is found
	 * @author Brian Maxwell
	 */
	public CSVRecord getRecordById(String id) {
		CSVRecord match = null;
		for (CSVRecord record : table) {
			String value = record.getValueAtField("id");
			if (CSVHelper.checkEquality(id, value)) {
				match = record;
				break;
			}
		}
		return match;
	}

	public CSVRecord getRecordByNameField(String nameField) {
		CSVRecord match = null;
		for (CSVRecord record : table) {
			String value = record.getValueAtField("name");
			if (CSVHelper.checkEquality(nameField, value)) {
				match = record;
				break;
			}
		}
		return match;
	}

	@Override
	public String toString() {
		ArrayList<String> rows = new ArrayList<String>();
		for (CSVRecord record : table) {
			ArrayList<String> row = record.getRow();
			for (int i = 0; i < row.size(); i++) {
				// remove all instances of delimiter in field before converting
				// to string.
				String d = CSVReader.delimiter;
				// first letter of the delimiter. The field cannot begin or end
				// with
				// a character that the delimiter begins with.
				char fl = d.charAt(0);
				String regex = "(" + d + ")|(" + fl + "+$)|(^" + fl + "+)";
				String newValue = row.get(i).replaceAll(regex, "");
				row.set(i, newValue);
			}
			rows.add(CSVHelper.join(record, CSVReader.delimiter));
		}
		return CSVHelper.join(rows, "\n");
	}

	// /////////////////////////
	// Getters and Setters //
	// /////////////////////////

	/**
	 * Get the corresponding CsvTable object mapping of this database table.
	 * 
	 * @return CsvTable object holding CsvRecords
	 * @author Brian Maxwell
	 */
	public ArrayList<CSVRecord> getTable() {
		return this.table;
	}

	// ///////////////////////
	// Protected methods //
	// ///////////////////////

	protected void setTable(ArrayList<CSVRecord> table) {
		this.table = table;
	}

	protected ArrayList<CSVRecord> createTableFromFile() {
		table = null;
		BufferedReader reader = null;
		file = new File(path);
		try {
			// Create file if it doesn't exist
			file.createNewFile();
			table = new ArrayList<CSVRecord>();
			reader = new BufferedReader(new FileReader(file));
			String line;
			while ((line = reader.readLine()) != null) {
				ArrayList<String> row = CSVHelper.StringArrayToArrayList(line
						.split(delimiter));
				CSVRecord record = new CSVRecord(row);
				record.setHeaders(this.headers);
				table.add(record);
			}
			reader.close();
		} catch (IOException e) {
		}
		return table;
	}

}
