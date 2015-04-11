package controllers;

import java.util.ArrayList;

import models.Note;
import requestHelpers.NoteCreationRequest;
import requestHelpers.NoteTitleSearchRequest;



public class NotesController extends Controller {

	
	public static String[] create(String title) {
		NoteCreationRequest request = new NoteCreationRequest(title, getSession().getAccount().getID());
		Note.create(request);
		String[] errors = ArrayListToStringArray(request.getErrors());
		getSession().getAccount().updateNotes();
		return errors;
	}
	
	public static ArrayList<Note> search(String query) {
		NoteTitleSearchRequest request = new NoteTitleSearchRequest(query);
		Note.searchByTitle(request);
		return request.getResults();
	}
	
	public static void delete(Note note) {
		Note.destroy(note);
		
		// Update account in the session to show the removed notes
		getSession().getAccount().updateNotes();
		
		// reset the session variables to show that no note is being edited
		getSession().setCurrentNote(null);
		getSession().setEditingNote(false);
	}
}




