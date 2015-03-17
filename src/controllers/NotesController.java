package controllers;

import models.Note;
import requestHelpers.NoteCreationRequest;



public class NotesController extends Controller {

	
	public static String[] create(String title) {
		NoteCreationRequest request = new NoteCreationRequest(title);
		Note.create(request);
		
		
		
		
		String[] errors = ArrayListToStringArray(request.getErrors());
		return errors;
	}
	
}
