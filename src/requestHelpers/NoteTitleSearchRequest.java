package requestHelpers;

import java.util.ArrayList;

import models.Note;

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
