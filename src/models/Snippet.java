package models;

import java.util.ArrayList;
import java.util.Collections;

import requestHelpers.SnippetsCreationRequest;
import csv.CSVReader;
import csv.CSVRecord;
import csv.CSVWriter;

public class Snippet {

	ArrayList<Tag> tags;
	String id;
	String content;
	int order;

	static String tablePath = "database/snippets_table";
	static String[] tableHeaders = { "id", "note_id", "content", "order" };

	/**
	 * The Snippet construction method that allows manual entry of construction
	 * parameters
	 * 
	 * @param content
	 *            The text body of the Snippet
	 * @param order
	 *            The order based on the body of the note
	 */
	public Snippet(String content, int order) {
		tags = new ArrayList<Tag>();
		this.content = content;
		this.order = order;
	}

	/**
	 * The Snippet constructor method that allows a CSVRecord object as a
	 * parameter
	 * 
	 * This construction method allows Snippets to be created using the data
	 * found in the CSVRecord object that is passed. Rather than manually
	 * passing in the id, content, and order, the method takes the data from the
	 * construction parameter.
	 * 
	 * @param record
	 *            the CSVRecord object to be used as a construction parameter
	 */
	public Snippet(CSVRecord record) {
		tags = new ArrayList<Tag>();
		this.id = record.getId();
		this.content = record.getValueAtField("content");
		String orderString = record.getValueAtField("order");
		int orderInt = 0;
		try {
			orderInt = Integer.parseInt(orderString);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		this.order = orderInt;
	}

	/**
	 * Creates snippets for a note
	 * 
	 * This method is supplied with a SnippetsCreationRequest object that holds
	 * all the snippets needed to create a note. The SnippetCreationRequest
	 * object holds the id of the note the snippets belong to, the content of
	 * the snippets, and the order of the snippets. The order of the snippets
	 * provides the structure of the body of the note object and ensures the
	 * information in the note is presented in a chronological order. The method
	 * also creates a CSVWriter object and makes use of the getNextId method
	 * found in the CSVWriter class to determine the ids of all the snippets.
	 * The final step of the snippet creation process is to account for the tags
	 * associated with the snippets. The method relies on help from methods
	 * found in the Tag class to accomplish this task. the createTagsForSnippet
	 * method parses through the content body of the snippet and searches for
	 * strings that are prefixed with a hash(this is the syntax of a snippet).
	 * If tags are found, the tags_table is populated with the found snippets.
	 * 
	 * @param request
	 *            the SnippetsCreationRequest object that holds all the data
	 *            needed for the snippets to be created
	 */
	public static void createSnippetsForNote(SnippetsCreationRequest request) {
		String noteId = request.getNote().getId();
		CSVWriter writer = constructWriter();
		for (Snippet snippet : request.getSnippets()) {
			// Get the next id to use in the table
			String nextId = writer.getNextId();
			writer.append(noteId, snippet.getContent(), snippet.getOrder() + "");
			// For the given snippets, write out its tags to the tags_table
			// Set the id for this snippet for the associative table
			snippet.id = nextId;
			Tag.createTagsForSnippet(snippet);
		}
		writer.write();
	}

	/**
	 * Retrieves the snippets belonging to a note using that notes id
	 * 
	 * This method takes a note id as a parameter and parses the snippets table
	 * to see which snippets belong to that note. It makes use of the CSVReader
	 * class and its methods to accomplish this task. To retrieve the correct
	 * snippets, the "where" and "is" methods belonging to the CSVReader class
	 * are used and the snippets are returned in an array list.
	 * 
	 * @param noteId
	 *            the id of the note the snippets belong to
	 * @return the array list that holds all of the snippets in object form
	 */
	public static ArrayList<Snippet> getSnippetsByNoteId(String noteId) {
		ArrayList<Snippet> snippets = new ArrayList<Snippet>();
		CSVReader reader = constructReader();
		CSVReader filteredReader = reader.where("note_id").is(noteId);
		for (CSVRecord r : filteredReader.getTable()) {
			snippets.add(new Snippet(r));
		}
		return snippets;
	}

	/**
	 * Destroy snippets by using a note id
	 * 
	 * This method removes snippets from the snippets table using a note id. To
	 * do this it creates a CSVWriter object and CSVRecord object and uses these
	 * objects to interact with the snippets table. It parses through each of
	 * the records in the snippets table and checks to see which records match
	 * the provided note id in their note id field. If they match, the records
	 * id is added to a snippetIds array list and the record itself is set to
	 * null. The method also removes all records that are null from the table
	 * using methods from the CSVWriter and Collections class and removes the
	 * tags associated with the removed snippet using the array list that holds
	 * the ids of the recently removed snippets.
	 * 
	 * @param noteId
	 *            the id of the note that is having its snippets destroyed
	 */
	public static void destroySnippetsByNoteId(String noteId) {
		CSVWriter writer = constructWriter();
		ArrayList<String> snippetIds = new ArrayList<String>();

		// Set all snippets belonging to this note to null
		for (int i = 0; i < writer.getTable().size(); i++) {
			CSVRecord r = writer.getTable().get(i);
			if (r.getValueAtField("note_id").equals(noteId)) {
				snippetIds.add(r.getId());
				writer.getTable().set(i, null);
			}
		}
		// Remove all null values from this table
		writer.getTable().removeAll(Collections.singleton(null));
		writer.write();

		// Remove all associated records in the tags_snippets_table
		TaggedSnippet.destroyBySnippetIds(snippetIds);
	}

	private static CSVWriter constructWriter() {
		return Model.constructWriter(tablePath, tableHeaders);
	}

	private static CSVReader constructReader() {
		return Model.constructReader(tablePath, tableHeaders);
	}

	public String getContent() {
		return content;
	}

	public int getOrder() {
		return order;
	}

	public String getId() {
		return id;
	}

	public void addTag(String tagContent) {
		Tag tag = new Tag(tagContent);
		this.tags.add(tag);
	}

	public ArrayList<Tag> getTags() {
		return tags;
	}

}
