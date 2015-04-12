package csv;

import java.util.ArrayList;

public class CSVRecordList {

	ArrayList<String> ids;
	ArrayList<String> valuesAtField;
	ArrayList<String> headers;
	String path;
	String field;

	public CSVRecordList(ArrayList<String> headers, ArrayList<CSVRecord> table, String field, String path) {
		this.headers = headers;
		this.field = field;
		this.path = path;

		ids = new ArrayList<String>();
		valuesAtField = new ArrayList<String>();

		for (CSVRecord record : table) {
			ids.add(record.getId());
			valuesAtField.add(record.getValueAtField(field));
		}
	}

	public CSVReader is(String value, boolean ignoreCase) {
		ArrayList<String> matchingIds = new ArrayList<String>();
		for (int i = 0; i < valuesAtField.size(); i++) {
			String id = ids.get(i);
			if (ignoreCase) {
				if (valuesAtField.get(i).equalsIgnoreCase(value))
					matchingIds.add(id);
			} else {
				if (valuesAtField.get(i).equals(value))
					matchingIds.add(id);
			}
		}

		ArrayList<CSVRecord> selectedRecords = new ArrayList<CSVRecord>();
		CSVReader reader = new CSVReader(path);
		reader.setHeaders(headers);
		reader.parse();

		if (matchingIds.size() > 0) {
			for (CSVRecord record : reader.getTable()) {
				int index = matchingIds.indexOf(record.getId());
				if (index >= 0)
					selectedRecords.add(record);
			}
		}
		// override the reader's table to only hold the matching records
		reader.setTable(selectedRecords);
		return reader;
	}

	public CSVReader isNot(String value, boolean ignoreCase) {
		ArrayList<String> matchingIds = new ArrayList<String>();
		for (int i = 0; i < valuesAtField.size(); i++) {
			String id = ids.get(i);
			if (ignoreCase) {
				if (!valuesAtField.get(i).equalsIgnoreCase(value))
					matchingIds.add(id);
			} else {
				if (!valuesAtField.get(i).equals(value))
					matchingIds.add(id);
			}
		}

		ArrayList<CSVRecord> filteredRecords = new ArrayList<CSVRecord>();
		CSVReader reader = new CSVReader(path);
		reader.setHeaders(headers);
		reader.parse();

		if (matchingIds.size() > 0) {
			for (CSVRecord record : reader.getTable()) {
				int index = matchingIds.indexOf(record.getId());
				if (index >= 0)
					filteredRecords.add(record);
			}
		}
		// override the reader's table to only hold the filtered records
		reader.setTable(filteredRecords);
		return reader;
	}

	public CSVReader is(String value) {
		return is(value, false);
	}

	public CSVReader isIgnoreCase(String value) {
		return is(value, true);
	}

	public CSVReader isNot(String value) {
		return isNot(value, false);
	}

	public CSVReader isNotIgnoreCase(String value) {
		return isNot(value, true);
	}

}




