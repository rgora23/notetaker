package controllers;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import models.Note;
import models.Snippet;
import noteTaker.Session;
import requestHelpers.SnippetsCreationRequest;


public class SnippetsController extends Controller {

	public static String snippetSeparator = "<p><font><br></font></p>";

	// Since snippets make up a note, this method is in the snippets controller.
	public static void saveNote(Note note, String content) {

		// Instead of trying to change the existing snippets,
		// simply destroy all in the database and write new ones.
		Snippet.destroySnippetsByNoteId(note.getId());

		// Remove html, head, and body tags from content
		content = content
				.replaceAll(
						"<html dir=\"ltr\"><head></head><body contenteditable=\"true\">",
						"");
		content = content.replaceAll("</body></html>", "");
		content = content.replaceAll("<p><br></p>", "");

		if (content.length() > 0) {
			ArrayList<Snippet> snippets = new ArrayList<Snippet>();

			// Use <br> tags as the delimiter for the snippets
			String[] snippetTexts = content.split("<br>");

			for (int i = 0; i < snippetTexts.length; i++) {
				String c = snippetTexts[i];
				if (c.length() > 0) {

					// remove trailing closed HTML tags
					while (c.matches("^</.+?>.*")) {
						c = c.replaceAll("^</.+?>", "");
					}

					// remove trailing open HTML tags
					while (c.matches(".*<[^/><]+>$")) {
						c = c.replaceAll("<[^/><]+>$", "");
					}
					Snippet thisSnippet = new Snippet(c, i);

					ArrayList<String> tagTexts = getTagsFromContent(c);

					for (String tag : tagTexts) {
						thisSnippet.addTag(tag);
					}

					snippets.add(thisSnippet);
				}
			}

			Note currentNote = getSession().getCurrentNote();
			SnippetsCreationRequest request = new SnippetsCreationRequest(
					currentNote, snippets);
			Snippet.createSnippetsForNote(request);
		} else {
			// System.out.println("No content to write!");
		}

	}

	private static ArrayList<String> getPTagsFromContent(String content) {
		String regex = "<p>(.*?)<\\/p>";
		Pattern snippetPattern = Pattern.compile(regex);
		Matcher patternMatcher = snippetPattern.matcher(content);
		ArrayList<String> matches = new ArrayList<String>();
		while (patternMatcher.find()) {
			String match = patternMatcher.group();
			match = match.replaceAll("#", "");
			matches.add(match);
		}
		return matches;
	}

	/**
	 * The controller method for getting a tag
	 * 
	 * This method retrieves a tag from within a body of text. It determines
	 * which words in the content are tags by using regex to parse the words
	 * found in the note body and see which ones start with a hash sign. Once it
	 * finds a words that is is prefixed with a hash, it replaces the hash with
	 * an empty character and adds the word in to a String Array list. The
	 * method then returns this String array list.
	 * 
	 * @param content
	 *            The block of text the tag is located in
	 * @return
	 */
	private static ArrayList<String> getTagsFromContent(String content) {

		Pattern tagPattern = Pattern.compile("#[^ \t\n\b<]+");
		Matcher patternMatcher = tagPattern.matcher(content);
		ArrayList<String> matches = new ArrayList<String>();
		while (patternMatcher.find()) {
			String match = patternMatcher.group();
			match = match.replaceAll("#", "");
			matches.add(match);
		}
		return matches;
	}

}
