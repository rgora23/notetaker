package requestHelpers;

import models.Note;

public class NoteCollectionCreationRequest extends Request {
	String title;
	String accountId; //Need to check if we are using accountId or userId
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
	
	public String getAccountId()
	{
		return accountId;
	}
	
	public void setAccountId(String accountId)
	{
		this.accountId = accountId;
	}

}




