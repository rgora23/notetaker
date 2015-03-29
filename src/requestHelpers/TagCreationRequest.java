package requestHelpers;

public class TagCreationRequest {
	
	String snippetName;
	
	public TagCreationRequest(String snippetName){
		this.snippetName = snippetName;
	}
		
	public String getSnippetName() {
		return snippetName;
	}
	
	public void setSnippetName(String snippetName) {
		this.snippetName = snippetName;
	}
	
}
