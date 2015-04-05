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
	static String[] tableHeaders = {"id", "note_id", "content", "order"};

	public Snippet(String content, int order) {
		tags = new ArrayList<Tag>();
		this.content = content;
		this.order = order;
	}
	
	public Snippet(CSVRecord record) {
		tags = new ArrayList<Tag>();
		this.id = record.getId();
		this.content = record.getValueAtField("content");
		String orderString = record.getValueAtField("order");
		int orderInt = 0;
		try {
			orderInt = Integer.parseInt(orderString);
		}
		catch (NumberFormatException e) {
			e.printStackTrace();
		}
		this.order = orderInt;
	}

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
	
	public static ArrayList<Snippet> getSnippetsByNoteId(String noteId) {
		ArrayList<Snippet> snippets = new ArrayList<Snippet>();
		CSVReader reader = constructReader();
		CSVReader filteredReader = reader.where("note_id").is(noteId);
		for ( CSVRecord r : filteredReader.getTable() ) {
			snippets.add(new Snippet(r));
		}
		return snippets;
	}
	
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




