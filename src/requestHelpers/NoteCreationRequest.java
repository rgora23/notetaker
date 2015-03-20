package requestHelpers;

public class NoteCreationRequest extends Request {

	String title;
	String account_id;
	
	public NoteCreationRequest(String title, String account_id) {
		this.title = title;
		this.account_id = account_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getAccountId()
	{
		return account_id;
	}
	
	public void setAccountId(String account_id)
	{
		this.account_id = account_id;
	}
	
	
	
	
}
