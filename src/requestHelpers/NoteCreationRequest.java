package requestHelpers;

public class NoteCreationRequest extends Request {

	String title;
	
	public NoteCreationRequest(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	
	
	
}
