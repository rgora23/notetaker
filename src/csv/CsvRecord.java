package csv;

import java.util.ArrayList;

public class CSVRecord extends CSVParser {

	ArrayList<String> row;

	/**
	 * Construct a new CsvRecord object from the supplied ArrayList<String>.
	 * 
	 * @param row
	 *            The ArrayList<String> object representing a table record.
	 * @author Brian Maxwell
	 */
	public CSVRecord(ArrayList<String> row) {
		super();
		this.row = row;
	}

	/**
	 * Construct a new CsvRecord object from the supplied String arguments.
	 * 
	 * @param values
	 *            The arguments that represent each field of the record
	 */
	public CSVRecord(String... values) {
		this.row = new ArrayList<String>();
		for (String value : values)
			this.row.add(value);
	}

	/**
	 * Get the value at the specified field.
	 * 
	 * @param header
	 *            the header corresponding to the field that needs to be
	 *            retrieved.
	 * @return The value at the current field or null if the field does not
	 *         exist.
	 * @author Brian Maxwell
	 */
	public String getValueAtField(String header) {
		String value = null;
		int columnIndex = this.headers.indexOf(header);
		if (columnIndex >= 0)
			value = row.get(columnIndex);
		return value;
	}

	/**
	 * Set the value at the specified field to a specified value.
	 * 
	 * @param value
	 *            the value to change the field to.
	 * @param header
	 *            the field to store the new value.
	 * @return The value that was set to the current field.
	 */
	public String setValueAtField(String value, String header) {
		int columnIndex = this.headers.indexOf(header);
		row.set(columnIndex, value);
		return value;
	}

	/**
	 * Shorthand method for retrieving the id of the method. Identical to
	 * calling: <div>
	 * 
	 * <pre>
	 * record.{@link #getValueAtField}("id")
	 * </pre>
	 * 
	 * </div>
	 * 
	 * @param header
	 *            the header corresponding to the field that needs to be
	 *            retrieved.
	 * @return The value at the current field or null if the field does not
	 *         exist.
	 * @author Brian Maxwell
	 */
	public String getId() {
		return getValueAtField("id");
	}

	/**
	 * Get the record as a ArrayList<String> object.
	 * 
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
