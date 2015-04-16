package models;

import java.util.ArrayList;

import requestHelpers.TagTitleSearchRequest;
import csv.CSVReader;
import csv.CSVRecord;
import csv.CSVWriter;

public class Tag {
	private String title;
	private String id;

	static String tableName = "tags_table";
	static String[] tableHeaders = { "id", "title" };

	public Tag(String title) {
		this.title = title;
	}

	public Tag(CSVRecord r) {
		this.id = r.getId();
		this.title = r.getValueAtField("title");
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
			CSVReader tagTitleMatches = writer.where("title").isIgnoreCase(
					t.getTitle());
			if (tagTitleMatches.getTable().isEmpty()) {
				writer.append(t.getTitle());
			}
		}
		writer.write();

		// reload the table changes
		writer.parse();

		for (Tag t : snippet.getTags()) {
			CSVReader tagTitleMatches = writer.where("title").isIgnoreCase(
					t.getTitle());
			String tagId = tagTitleMatches.getTable().get(0).getId();
			// add this snippet and tag relation in the tags_snippets_table
			TaggedSnippet.write(snippetId, tagId);
		}
	}

	/**
	 * The method that searches the database for tags
	 * 
	 * This method searches the tags_table for tags that match the query string
	 * provided by the TagTitleSearchRequest object that is passed as a
	 * parameter. It uses a CSVReader object to parse the tags_table for records
	 * that contain values in their title fields that match the query string
	 * provided by the search request TagTitleSearchRequest object. It does this
	 * case insensitively by converting both the query string and the title
	 * field value to lower case form. It then populates an ArrayList object
	 * with the matches that have been found.
	 * 
	 * @param request
	 *            The request helper that provides the data needed for a tag
	 *            search
	 * @return The TagTitleSearchRequest object
	 */
	public static TagTitleSearchRequest searchTagsByTitle(
			TagTitleSearchRequest request) {
		ArrayList<Tag> matchingTags = new ArrayList<Tag>();
		CSVReader reader = constructReader();
		for (CSVRecord record : reader.getTable()) {
			String recordTitle = record.getValueAtField("title");
			boolean queryMatches = recordTitle.toLowerCase().contains(
					request.getQuery().toLowerCase());
			if (queryMatches) {
				Tag thisTag = new Tag(record);
				request.addMatch(thisTag);
				matchingTags.add(thisTag);
			}
		}
		return request;
	}

	private static CSVWriter constructWriter() {
		return Model.constructWriter(tableName, tableHeaders);
	}

	private static CSVReader constructReader() {
		return Model.constructReader(tableName, tableHeaders);
	}

	public String getTitle() {
		return title;
	}

	public String getId() {
		return id;
	}

}
