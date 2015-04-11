package exceptions;

public class NoSessionNoteSetException extends Exception {

	public NoSessionNoteSetException() {
	}

	public NoSessionNoteSetException(String message) {
		super(message);
	}
}