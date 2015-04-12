package requestHelpers;

import java.util.ArrayList;

import models.Tag;

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
