package requestHelpers;

public class SnippetCreationRequest {
	
	String noteName;
	String tagName;
	
	public SnippetCreationRequest(String noteName, String tagName){
		this.noteName = noteName;
		this.tagName = tagName;
	}

	public String getNoteName() {
		return noteName;
	}

	public void setNoteName(String noteName) {
		this.noteName = noteName;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	
	
}
