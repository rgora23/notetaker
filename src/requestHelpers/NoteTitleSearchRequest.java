package requestHelpers;

import java.util.ArrayList;

import models.Note;

/**
 * This class inherits directly from the Request class. An object of this class
 * is created when the user attempts to search for one of his/her notes by the
 * title. The object is passed in to the searchNoteByTitle method found in the
 * Note class and provides the name of the note the user would like to search
 * for. The ArrayList instance variable acts as a container for any notes that
 * are matches to the users query and is populated after a successful search.
 * The errors Array List it inherits from the Request class also handles error
 * detection if the user searches incorrectly.
 */
public class NoteTitleSearchRequest extends Request {

	private String query;
	private ArrayList<Note> results;

	public NoteTitleSearchRequest(String query) {
		this.query = query;
		this.results = new ArrayList<Note>();
	}

	public ArrayList<Note> getResults() {
		return results;
	}

	public void setResults(ArrayList<Note> results) {
		this.results = results;
	}

	public String getQuery() {
		return query;
	}

}




