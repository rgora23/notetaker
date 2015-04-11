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
		content = content.replaceAll("<html dir=\"ltr\"><head></head><body contenteditable=\"true\">", "");
		content = content.replaceAll("</body></html>", "");
		content = content.replaceAll("<p><br></p>", "");

		if (content.length() > 0) {
			System.out.println(content);

			// This is the regex used to split the content into snippets
			// When putting the snippets back together, must join them with the
			// following string:
			// "<p><font><br></font></p>" which will put a break between them in
			// the HTML editor.
			String regex = "<p><font( face=\"[a-zA-Z ]*\")?><br></font></p>";
			String snippetContents[] = content.split(regex);

			ArrayList<Snippet> snippets = new ArrayList<Snippet>();

			for (int i = 0; i < snippetContents.length; i++) {
				String c = snippetContents[i];
				if (c.equals(""))
					continue;

				Snippet thisSnippet = new Snippet(c, i);
				for (String tag : getTagsFromContent(c)) {
					thisSnippet.addTag(tag);
				}

				snippets.add(thisSnippet);
			}

			SnippetsCreationRequest request =
					new SnippetsCreationRequest(Session.getInstance().getCurrentNote(), snippets);
			Snippet.createSnippetsForNote(request);
		} else {
			// System.out.println("No content to write!");
		}

	}

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
