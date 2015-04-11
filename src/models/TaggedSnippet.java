package models;

import java.util.ArrayList;
import java.util.Collections;

import csv.CSVReader;
import csv.CSVRecord;
import csv.CSVWriter;

public class TaggedSnippet extends Model {

	static String tablePath = "database/tags_snippets_table";
	static String[] tableHeaders = {"id", "snippet_id", "tag_id"}; 

	public static void write(String snippet_id, String tag_id) {
		CSVWriter writer = constructWriter();
		writer.append(tag_id, snippet_id);
		writer.write();
	}

	public static void destroyBySnippetIds(ArrayList<String> snippetIds) {
		CSVWriter writer = constructWriter();

		for (int i = 0; i < writer.getTable().size(); i++) {
			CSVRecord r = writer.getTable().get(i);
			// Set the value to null where the record has one of the
			// snippet id's to remove
			String thisSnippetId = r.getValueAtField("snippet_id"); 
			if ( snippetIds.contains(thisSnippetId) ) {
				writer.getTable().set(i, null);	
			}
		}

		// Remove all null values from this table.
		// Essentially all traces of the tags associated with the
		// snippet id's provided will be destroyed.
		writer.getTable().removeAll(Collections.singleton(null));
		writer.write();
	}

	private static CSVWriter constructWriter() {
		return Model.constructWriter(tablePath, tableHeaders);
	}

	private static CSVReader constructReader() {
		return Model.constructReader(tablePath, tableHeaders);
	}
}
