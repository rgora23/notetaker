package controllers;

import java.util.ArrayList;

import models.Snippet;
import models.Tag;
import requestHelpers.TagTitleSearchRequest;

public class TagsController extends Controller {

	public static void search(String query) {
		TagTitleSearchRequest request = new TagTitleSearchRequest(query);
		Tag.searchTagsByTitle(request);
		ArrayList<Snippet> matchingSnippets = new ArrayList<Snippet>();
		matchingSnippets = TaggedSnippetController.getSnippetsThroughTags(request.getMatches());
		
		getSession().setMatchingSnippets(matchingSnippets);
	}

}
