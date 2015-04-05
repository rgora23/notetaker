package controllers;

import noteTaker.Session;
import models.Note;
import requestHelpers.NoteCreationRequest;



public class NotesController extends Controller {

	
	public static String[] create(String title) {
		NoteCreationRequest request = new NoteCreationRequest(title, getSession().getAccount().getID());
		Note.create(request);
		String[] errors = ArrayListToStringArray(request.getErrors());
		getSession().getAccount().updateNotes();
		return errors;
	}
	
}




