package requestHelpers;

import java.util.ArrayList;

import models.Tag;

/**
 * This class inherits directly from the Request class. An object of this class
 * is created when the user wishes to search for note content using a snippet
 * name. The object is passed in to the searchTagsByTitle method found in the
 * Tag class and provides the name of the tag the user would like to search for. 
 * The ArrayList instance variable acts as a container for any tags that
 * are matches to the users query and is populated after a successful search.
 * The errors Array List it inherits from the Request class also handles error
 * detection if the user searches incorrectly.
 */
public class TagTitleSearchRequest extends Request {

	private String query;
	ArrayList<Tag> matches;

	public TagTitleSearchRequest(String query) {
		this.query = query;
		this.matches = new ArrayList<Tag>();
	}

	public String getQuery() {
		return this.query;
	}

	public ArrayList<Tag> getMatches() {
		return this.matches;
	}

	public void addMatch(Tag t) {
		this.matches.add(t);
	}
}
