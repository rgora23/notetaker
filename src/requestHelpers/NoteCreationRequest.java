package requestHelpers;

/**
 * This class inherits directly from the Request class. It is used during the
 * note creation process. The instance variables found in this class represent
 * the requested title a user would like to give to his/her new note and the
 * accountId of the user. The Note class uses the data found in the objects of
 * this class to add a new note record to the notes_table. The errors Array List
 * it inherits from the Request class also provides a way of error checking as
 * well during the note creation process.
 */
public class NoteCreationRequest extends Request {

	String title;
	String accountId;

	public NoteCreationRequest(String title, String accountId) {
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

	public void setAccountId(String account_id) {
		this.accountId = account_id;
	}

}




