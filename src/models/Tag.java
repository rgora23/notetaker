package models;

import requestHelpers.TagCreationRequest;
import csv.CSVReader;
import csv.CSVWriter;

public class Tag {
	private String title;
	private String id;

	static String tablePath = "database/tags_table";
	static String[] tableHeaders = { "id", "title" };

	public Tag(String title) {
		this.title = title;
	}

	public static void create(Tag t) {

	}

	/**
	 * This method creates tags for the snippet that is passed in as a parameter
	 * 
	 * This method adds the tags associated with a snippet in to the tags table.
	 * It does this by using the data found in the snippet that is passed in as
	 * a parameter. It iterates through the tags found in the the passed snippet
	 * and compares those tags with the already existing tags in the tags table.
	 * If no matches are found, the tags are appended to the tags table. The
	 * method also adds the relation between the tags and the snippets to the
	 * tags_snippets_table.
	 * 
	 * @param snippet
	 *            snippet object that contains tags to be added to tags table
	 */
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
