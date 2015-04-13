package requestHelpers;

import java.util.ArrayList;

import models.Note;
import models.Snippet;

/**
 * This class provides objects that facilitate the creation of Snippets. It is
 * used by the Snippet class to add a record to the snippts_table and provides
 * the note the snippet is being created for as well as the content of the
 * snippets.
 */
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




