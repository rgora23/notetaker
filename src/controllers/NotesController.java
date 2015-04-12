package controllers;

import java.util.ArrayList;

import models.Note;
import requestHelpers.NoteCreationRequest;
import requestHelpers.NoteTitleSearchRequest;

public class NotesController extends Controller {

	/**
	 * The controller method for note creation
	 * 
	 * This method is called when the user initiates the note creation process.
	 * It creates a NoteCreationRequest using the title that is passed as a
	 * parameter and the ID of the user creating the note, which is retrieved
	 * using methods from the Session and Account classes. It also detects
	 * errors and populates a String array with them. The most common error that
	 * would be produced is the NOTE_TITLE_TAKEN error found in the
	 * ErrorMessages class. This occurs if the title provided matches the title
	 * of a note the user already owns.
	 * 
	 * @param title
	 *            The title the user wants for the new note
	 * @return String array that contains errors created during the note
	 *         creation process
	 */
	public static String[] create(String title) {
		NoteCreationRequest request = new NoteCreationRequest(title, getSession().getAccount().getID());
		Note.create(request);
		String[] errors = ArrayListToStringArray(request.getErrors());
		getSession().getAccount().updateNotes();
		return errors;
	}

	/**
	 * The controller method for note searching
	 * 
	 * This method is called when the user attempts to search for a note. It
	 * creates a NoteTitleRequest object using the query String passed in to the
	 * method and then uses that NoteTitleRequest object as a parameter for the
	 * method searchByTitle found in the Note class. The method returns an Array
	 * List containing the matching note object if the search is successful. If
	 * the search is unsuccessful, it returns an empty Array List.
	 * 
	 * @param query
	 *            The title of the note to search for
	 * @return Array list containing matching note
	 */
	public static ArrayList<Note> search(String query) {
		NoteTitleSearchRequest request = new NoteTitleSearchRequest(query);
		Note.searchByTitle(request);
		return request.getResults();
	}

	/**
	 * The controller method for deleting a note
	 * 
	 * This method is called when the user wishes to delete a note. It uses the
	 * destroy method found in the Note class and updates the session to show
	 * that no note is currently being edited.
	 * 
	 * @param note
	 *            The note to be deleted
	 */
	public static void delete(Note note) {
		Note.destroy(note);

		// Update account in the session to show the removed notes
		getSession().getAccount().updateNotes();

		// reset the session variables to show that no note is being edited
		getSession().setCurrentNote(null);
		getSession().setEditingNote(false);
	}
}




