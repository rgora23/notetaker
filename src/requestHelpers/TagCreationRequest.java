package requestHelpers;

/**
 * This class provides objects that facilitate the creation of Tags. It is
 * used by the Tag class to add a record to the tagss_table and provides
 * the snippet that will be tagged.
 */
public class TagCreationRequest {

	String snippetName;

	public TagCreationRequest(String snippetName) {
		this.snippetName = snippetName;
	}

	public String getSnippetName() {
		return snippetName;
	}

	public void setSnippetName(String snippetName) {
		this.snippetName = snippetName;
	}

}




