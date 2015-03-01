package csv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class CsvReader extends CsvParser {

	File file;
	String path;
	ArrayList<CsvRecord> table;
	String delimiter;
	
	/**
	 * Creates a new object that can read the entire database and retrieve values.
	 * This is only suitable for small databases. Default headers are ["id"].
	 * 
	 * @param path   the path to the table
	 * @author Brian Maxwell
	 */
	public CsvReader(String path) {
		super();
		this.path = path;
		setDelimiter(",");
	}
	
	/**
	 * Creates a new object that can read the entire database and retrieve values.
	 * This is only suitable for small databases. Provided headers override default headers.
	 * 
	 * @param path   the path to the table
	 * @param headers   the
	 * @author Brian Maxwell
	 */
	public CsvReader(String path, String... headers) {
		super(headers);
		this.path = path;
		setDelimiter(",");
	}
	
	/**
	 * Creates a CsvTable object from database table using the provided delimiter.
	 * The table can be retrieved using a getter on the CsvReader object.
	 * @return The created CsvTable object
	 * @author Brian Maxwell
	 */
	public ArrayList<CsvRecord> parse(String delimiter) {
		this.delimiter = delimiter;
		return createTableFromFile();
	}
	
	public String getNextId() {
		CsvRecord lastRecord = table.get(table.size() - 1);
		String lastIdString = lastRecord.getValueAtField("id");
		int lastId = -1;
		try {
			lastId = Integer.parseInt(lastIdString);			
		} catch (NumberFormatException e) { }
		return (lastId + 1) + "";
	}
	
	/**
	 * Creates a CsvTable object from database table using the set delimiter.
	 * The table can be retrieved using a getter on the CsvReader object.
	 * @return The created CsvTable object
	 * @author Brian Maxwell
	 */
	public ArrayList<CsvRecord> parse() {
		return createTableFromFile();
	}
	
	/**
	 * Searches through the table and returns a new filtered table where all the values
	 * of the supplied header hold the supplied value.
	 * <br>
	 * <div><strong>Example: </strong></div>
	 * <pre>
	 * CsvReader reader = new {@link #CsvReader}("inventory_table", "name", "price", "category");
	 * reader.{@link #parse()};
	 * CsvTable cleaningProducts =  reader.{@link #where}("category", "cleaning");
	 * </pre>
	 * 
	 * @param header   The header of each record to compare the value to
	 * @param value    The value to match against
	 * @return A new CsvTable object holding matched records
	 * @author Brian Maxwell
	 */
	public ArrayList<CsvRecord> where(String header, String value) {
		ArrayList<CsvRecord> filteredTable = new ArrayList<CsvRecord>();
		for (CsvRecord record : table) {
			if (value.equals(record.getValueAtField(header))) filteredTable.add(record);
		}
		return filteredTable;
	}
	
	/**
	 * Search the table for the record with the matching id
	 * @param id   the integer id of the record to retrieve
	 * @return the matching record or null if nothing is found
	 * @author Brian Maxwell
	 */
	public CsvRecord getRecordById(int id) {
		return getRecordById(id + "");
	}
	
	/**
	 * Search the table for the record with the matching id
	 * @param id   the string id of the record to retrieve
	 * @return the matching record or null if nothing is found
	 * @author Brian Maxwell
	 */
	public CsvRecord getRecordById(String id) {
		CsvRecord match = null;
		for (CsvRecord record : table) {
			String value = record.getValueAtField("id");
			if (value != null && value.equals(id)) {
				match = record;
				break;
			}
		}
		return match;
	}
	
	@Override
	public String toString() {
		ArrayList<String> rows = new ArrayList<String>();
		for (CsvRecord record : table) rows.add(CsvHelpers.join(record, getDelimiter()));
		return CsvHelpers.join(rows, "\n");
	}
	
	///////////////////////////
	//	Getters and Setters  //
	///////////////////////////
	
	
	/**
	 * Get the corresponding CsvTable object mapping of this database table.
	 * @return CsvTable object holding CsvRecords
	 * @author Brian Maxwell
	 */
	public ArrayList<CsvRecord> getTable() {
		return this.table;
	}
	
	/**
	 * Set the delimiter to specified string. Default is ",".
	 * @param delimiter   the delimiter to separate each record field.
	 * @author Brian Maxwell
	 */
	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}
	
	/**
	 * Get the current delimiter
	 * @return The current delimiter being used to parse table records.
	 * @author Brian Maxwell
	 */
	public String getDelimiter() {
		return this.delimiter;
	}
	
	
	/////////////////////////
	//	Protected methods  //
	/////////////////////////
	
	protected ArrayList<CsvRecord> createTableFromFile() {
		table = null;
		BufferedReader reader = null;
		file = new File(path);
		try {
			// Create file if it doesn't exist
			file.createNewFile();
			table = new ArrayList<CsvRecord>();
			reader = new BufferedReader(new FileReader(file));
			String line;
			while ((line = reader.readLine()) != null) {
				ArrayList<String> row = CsvHelpers.StringArrayToArrayList(line.split(delimiter));
				CsvRecord record = new CsvRecord(row);
				table.add(record);
			}
			reader.close();
		} catch (IOException e) { }
		return table;
	}

	
}
