package controllers;

import java.util.ArrayList;

import models.Snippet;
import models.Tag;
import models.TaggedSnippet;

public class TaggedSnippetController extends Controller {

	public static ArrayList<Snippet> getSnippetsThroughTags(ArrayList<Tag> tags) {
		ArrayList<Snippet> associatedSnippets = new ArrayList<Snippet>();
		for (Tag tag : tags) {
			String tagId = tag.getId();
			ArrayList<Snippet> snippets = TaggedSnippet.getSnippetsByTagId(tagId);
			associatedSnippets.addAll(snippets);
		}
		

		// Remove duplicate snippets that would be generated from a snippet
		// having multiple tags
		ArrayList<Snippet> uniqueAssociatedSnippets = new ArrayList<Snippet>();
		ArrayList<String> usedIds = new ArrayList<String>();
		for (Snippet s : associatedSnippets) {
			if (usedIds.indexOf(s.getId()) < 0) {
				usedIds.add(s.getId());
				uniqueAssociatedSnippets.add(s);
			}
		}
		
		return uniqueAssociatedSnippets;
	}

}
