package models;

import requestHelpers.TagCreationRequest;
import csv.CSVReader;
import csv.CSVWriter;

public class Tag {
	private String title;
	private String id;
	
	static String tablePath = "database/tags_table";
	static String[] tableHeaders = {"id", "title"}; 
	
	public Tag(String title) {
		this.title = title;
	}
	
	public static void create(Tag t){
		
	}
	
	public static void createTagsForSnippet(Snippet snippet) {
		String snippetId = snippet.getId();
		
		// Create tag in tags_table if it doesn't already exist
		CSVWriter writer = constructWriter();
		for (Tag t : snippet.getTags()) {
			CSVReader tagTitleMatches = writer.where("title").isIgnoreCase(t.getTitle());
			if (tagTitleMatches.getTable().isEmpty()) {
				writer.append(t.getTitle());
			}
		}
		writer.write();
		
		// reload the table changes
		writer.parse();
		
		for (Tag t : snippet.getTags()) {
			CSVReader tagTitleMatches = writer.where("title").isIgnoreCase(t.getTitle());
			String tagId = tagTitleMatches.getTable().get(0).getId();
			// add this snippet and tag relation in the tags_snippets_table
			TaggedSnippet.write(snippetId, tagId);
		}
	}
	
	private static CSVWriter constructWriter() {
		return Model.constructWriter(tablePath, tableHeaders);
	}
	
	private static CSVReader constructReader() {
		return Model.constructReader(tablePath, tableHeaders);
	}

	public String getTitle() {
		return title;
	}
	
	public String getId() {
		return id;
	}
	
	
	
	
}




