package exceptions;

/**
 * This is a custom exception created for the event where a user attempts to
 * delete a note that does not exist. This event usually occurs when the user
 * forgets to select a note by clicking on it and hits the delete note button.
 */
public class DeleteNullNoteException extends Exception {
	public DeleteNullNoteException() {
	}

	public DeleteNullNoteException(String message) {
		super(message);
	}
}
