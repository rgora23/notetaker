package requestHelpers;

import java.util.ArrayList;

import models.Note;
import models.Snippet;

public class SnippetsCreationRequest {

	private Note note;
	private ArrayList<Snippet> snippets;

	public SnippetsCreationRequest(Note note, ArrayList<Snippet> snippets) {
		this.note = note;
		this.snippets = snippets;
	}

	public Note getNote() {
		return note;
	}

	public ArrayList<Snippet> getSnippets() {
		return snippets;
	}

}
