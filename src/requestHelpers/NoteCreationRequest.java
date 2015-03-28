package requestHelpers;

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
	
	public String getAccountId()
	{
		return accountId;
	}
	
	public void setAccountId(String account_id)
	{
		this.accountId = account_id;
	}
	
	
	
	
}
