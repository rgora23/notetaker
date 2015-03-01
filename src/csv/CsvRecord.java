package csv;

import java.util.ArrayList;


public class CsvRecord extends CsvParser {

	ArrayList<String> row;
	
	/**
	 * Construct a new CsvRecord object from the supplied ArrayList<String>.
	 * @param row   The ArrayList<String> object representing a table record.
	 * @author Brian Maxwell
	 */
	public CsvRecord(ArrayList<String> row) {
		super();
		this.row = row;
	}

	/**
	 * Construct a new CsvRecord object from the supplied String arguments.
	 * @param values   The arguments that represent each field of the record
	 */
	public CsvRecord(String... values) {
		this.row = new ArrayList<String>();
		for (String value : values) this.row.add(value);
	}

	
	/**
	 * Get the value at the specified field.
	 * @param header   the header corresponding to the field that needs to be retrieved.
	 * @return The value at the current field or null if the field does not exist.
	 * @author Brian Maxwell
	 */
	public String getValueAtField(String header) {
		String value = null;
		int columnIndex = this.headers.indexOf(header);
		if (columnIndex >= 0) value = row.get(columnIndex);
		return value;
	}
	
	/**
	 * Get the record as a ArrayList<String> object.
	 * @return ArrayList<String> object representing the record information.
	 * @author Brian Maxwell
	 */
	public ArrayList<String> getRow() {
		return this.row;
	}
	
	@Override
	public String toString() {
		return row.toString();
	}
	
	
	
}
