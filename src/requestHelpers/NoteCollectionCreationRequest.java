package requestHelpers;

import models.Note;
/**
 * This class inherits directly form the Request class. It is responsible for
 * providing the data to the Note collection class when a note collection is
 * being created. The two instance variables, title and accountId, represent the
 * title the user wants to name the note collection and the accountId of the
 * user found in the accounts table.
 */
public class NoteCollectionCreationRequest extends Request {
	String title;
	String accountId; // Need to check if we are using accountId or userId

	public NoteCollectionCreationRequest(String title, String accountId) {
		this.title = title;
		this.accountId = accountId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

}




