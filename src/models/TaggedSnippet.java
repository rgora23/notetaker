package models;

import java.util.ArrayList;
import java.util.Collections;

import csv.CSVReader;
import csv.CSVRecord;
import csv.CSVWriter;

public class TaggedSnippet extends Model {

	static String tablePath = "database/tags_snippets_table";
	static String[] tableHeaders = { "id", "snippet_id", "tag_id" };

	public static void write(String snippet_id, String tag_id) {
		CSVWriter writer = constructWriter();
		writer.append(snippet_id, tag_id);
		writer.write();
	}

	/**
	 * This method removes a record from the tags_snippets_table using the
	 * snippet id
	 * 
	 * The method destroys records in the tags_snippet_table using the snippet
	 * ids found in the passed array list. It iterates through the passed array
	 * list and compares the values found in the array list with the each
	 * snippets_tags_table record's "snippet_id" field. If there are matches,
	 * each record that is found to match is set to null by using a CSVWriter
	 * object. Once this is done, the method continues by removing all of the
	 * values in the table with a null value.
	 * 
	 * @param snippetIds
	 */
	public static void destroyBySnippetIds(ArrayList<String> snippetIds) {
		CSVWriter writer = constructWriter();

		for (int i = 0; i < writer.getTable().size(); i++) {
			CSVRecord r = writer.getTable().get(i);
			// Set the value to null where the record has one of the
			// snippet id's to remove
			String thisSnippetId = r.getValueAtField("snippet_id");
			if (snippetIds.contains(thisSnippetId)) {
				writer.getTable().set(i, null);
			}
		}

		// Remove all null values from this table.
		// Essentially all traces of the tags associated with the
		// snippet id's provided will be destroyed.
		writer.getTable().removeAll(Collections.singleton(null));
		writer.write();
	}
	
	public static ArrayList<Snippet> getSnippetsByTagId(String tagId) {
		ArrayList<Snippet> snippets = new ArrayList<Snippet>();
		CSVReader reader = constructReader();
		
		for ( CSVRecord record : reader.getTable() ) {

			String recordTagId = record.getValueAtField("tag_id");
			if ( tagId.equals(recordTagId) ) {
				String snippetId = record.getValueAtField("snippet_id");
				Snippet snippet = Snippet.getSnippetById(snippetId);
				snippets.add(snippet);
			}
			
		}
		
		return snippets;
	}

	private static CSVWriter constructWriter() {
		return Model.constructWriter(tablePath, tableHeaders);
	}

	private static CSVReader constructReader() {
		return Model.constructReader(tablePath, tableHeaders);
	}
}




