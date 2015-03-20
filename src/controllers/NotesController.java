package controllers;

import noteTaker.Session;
import models.Note;
import requestHelpers.NoteCreationRequest;



public class NotesController extends Controller {

	
	public static String[] create(String title) {
		String accountId = Session.getAccount().getID();
		NoteCreationRequest request = new NoteCreationRequest(title, accountId);
		Note.create(request);
		
		
		
		
		String[] errors = ArrayListToStringArray(request.getErrors());
		return errors;
	}
	
}
