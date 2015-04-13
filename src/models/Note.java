package models;

import java.util.ArrayList;
import java.util.Collections;

import noteTaker.ErrorMessages;
import requestHelpers.NoteCreationRequest;
import requestHelpers.NoteTitleSearchRequest;
import csv.CSVReader;
import csv.CSVRecord;
import csv.CSVWriter;
/**
 * This class inherits directly from the Model class and is the model for notes.
 * It includes methods for creating note records, deleting note records, and
 * retrieving note records. It relies heavily on classes from the CSV package
 * such as CSVRecord, CSVWriter, and CSVReader. These classes help with
 * functions such as indexing and retrieving notes.This class also relies on the
 * NoteCreationRequest and NoteTitleSearch class from the requestHelpers
 * package. The classes from the requestHelpers package provide data for the
 * note creation and title search functions.
 * 
 * 
 * @author Brian Maxwell
 * @author Tony Benny
 * @author Matt Yeager
 * 
 */
public class Note extends Model {

	String title;
	String id;
	String account_id;
	String note_collection_id;
	ArrayList<Snippet> snippets;

	static String tableName = "notes_table";
	static String[] tableHeaders = { "id", "title", "account_id", "note_collection_id" };

	public Note(CSVRecord noteRecord) {
		this.id = noteRecord.getId();
		this.title = noteRecord.getValueAtField("title");
		this.account_id = noteRecord.getValueAtField("account_id");
		this.note_collection_id = noteRecord.getValueAtField(note_collection_id);
		this.snippets = Snippet.getSnippetsByNoteId(id);
	}

	/**
	 * The method that creates a note
	 * 
	 * This method uses the data provided by the NoteCreationRequest object to
	 * create a new note. It first checks to see if the new note has a unique
	 * title. It does this by using the account id and title values found in the
	 * NoteCreationRequest object. First, it creates a CSVReader object by using
	 * the provided account id and title. If the provided account id and title
	 * values create an empty CSVReader object, it means that the note title is
	 * unique and can be created. Otherwise, an error is thrown stating that a
	 * note with the same table already exists for that account.
	 * 
	 * @param request
	 *            the NoteCreationRequest object that holds the data used for
	 *            the creation of a note
	 */
	public static void create(NoteCreationRequest request) {
		CSVWriter noteTableWriter = constructWriter();

		// Get other notes belonging to this user with the same title
		// Validate that this table is empty (otherwise they can't use this
		// title).
		CSVReader notesWithSameTitle =
				noteTableWriter.where("account_id").is(request.getAccountId()).where("title")
						.isIgnoreCase(request.getTitle());
		if (notesWithSameTitle.getTable().isEmpty()) {
			// 0 means it does not belong to any collections
			String noteCollectionId = "0";
			noteTableWriter.append(request.getTitle(), request.getAccountId(), noteCollectionId);
			noteTableWriter.write();
		} else {
			request.addError(ErrorMessages.NOTE_TITLE_TAKEN);
		}

	}

	/**
	 * The method that returns all notes associated with an account
	 * 
	 * This method is only used if the user is logged in. If a session is
	 * currently active, the method parses through the notes table using two
	 * CSVReader objects. The first CSVReader object holds the whole notes table
	 * and the second CSVReader holds a filtered version of the notes table with
	 * only notes belonging to the logged in user. The notes belonging to the
	 * user are returned in an array list. THe method also displays an error
	 * message if the current user is not logged in.
	 * 
	 * @return An array list containing all note objects associated with the
	 *         account
	 */
	public static ArrayList<Note> getAccountNotes() {
		ArrayList<Note> matchingNotes = new ArrayList<Note>();

		// If user is logged in to an account, get all matching notes
		// and construct them using the ORM. Populate ArrayList of Note objects
		// to return to controller.
		if (getSession().getAccount() != null) {
			CSVReader noteTableReader = constructReader();
			CSVReader accountNotes = noteTableReader.where("account_id").is(getSession().getAccount().getID());
			for (CSVRecord record : accountNotes.getTable()) {
				matchingNotes.add(new Note(record));
			}
		} else
			System.out.println("Not logged in.");
		return matchingNotes;
	}

	/**
	 * The method that returns a note using its title
	 * 
	 * This method returns a note using the String parameter that is passed in
	 * to it. It creates a CSVReader object that uses the String parameter
	 * passed to parse the notes table and find records which contain the passed
	 * parameter as the value in their title field. If the CSVReader object is
	 * not empty, it means that a note with that title exists and it is
	 * returned. Otherwise, a note with that title does not exist and a null
	 * value is passed.
	 * 
	 * @param title
	 *            String variable that holds the name of the note to be returned
	 * @return the note object with the matching title
	 */
	public static Note getNoteByTitle(String title) {
		CSVReader noteTableReader = constructReader();
		CSVReader filteredReader = noteTableReader.where("title").isIgnoreCase(title);

		if (!filteredReader.getTable().isEmpty()) {
			CSVRecord record = filteredReader.getTable().get(0);
			return new Note(record);
		} else
			return null;
	}

	/**
	 * The method that returns a note using its id
	 * 
	 * This method returns a note using the String parameter that is passed in
	 * to it. It creates a CSVReader object that uses the String parameter
	 * passed to parse the notes table and find the record which contains the
	 * passed parameter as the value in its id field.
	 * 
	 * @param title
	 *            String variable that holds the id of the note to be returned
	 * @return the note object with the matching id
	 */
	public static Note getNoteById(String id) {
		CSVReader noteTableReader = constructReader();
		Note note = new Note(noteTableReader.getRecordById(id));
		return note;
	}

	/**
	 * The method that allows for searching by a note's title
	 * 
	 * This method searches for notes by comparing a string that holds the note
	 * title of interest with all of the notes belonging to a user. Two array
	 * lists are initialized, one that holds all of the notes belonging to the
	 * user and another that will hold the note that matches the string. The
	 * string that holds the title of the note of interest is compared with each
	 * element of the array list that holds the users notes and every match is
	 * then added to the matching matching notes array list.
	 * 
	 * @param request
	 *            The NoteTitleSearchRequest object that holds the title
	 * @return
	 */
	public static NoteTitleSearchRequest searchByTitle(NoteTitleSearchRequest request) {
		ArrayList<Note> notes = getSession().getAccount().getNotes();
		ArrayList<Note> matchingNotes = new ArrayList<Note>();
		for (Note note : notes) {
			String title = note.getTitle();
			boolean queryMatches = title.toLowerCase().contains(request.getQuery().toLowerCase());
			if (queryMatches) {
				matchingNotes.add(note);
			}
		}
		request.setResults(matchingNotes);
		return request;
	}

	/**
	 * Method that handles deleting a note
	 * 
	 * This method handles the deletion of notes from the notes table. It first
	 * destroys all snippets associated with the note by using the
	 * destroySnippetsByNoteId static method provided by the Snippets class.
	 * Next, it parses through the notes table using the record id which is
	 * provided by a CSVRecord object. Finally, using a CSVWriter object the
	 * record is actually deleted.
	 * 
	 * @param note
	 *            The Note object that is going to be deleted
	 */
	public static void destroy(Note note) {
		Snippet.destroySnippetsByNoteId(note.id);
		CSVWriter writer = constructWriter();
		CSVRecord record = writer.getRecordById(note.getId());

		// set the value in the table to null
		int recordIndex = writer.getTable().indexOf(record);
		writer.getTable().set(recordIndex, null);

		// Remove all null values from this table, effectively deleting the
		// record
		writer.getTable().removeAll(Collections.singleton(null));
		writer.write();
	}

	private static CSVWriter constructWriter() {
		return Model.constructWriter(tableName, tableHeaders);
	}

	private static CSVReader constructReader() {
		return Model.constructReader(tableName, tableHeaders);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAccount_id() {
		return account_id;
	}

	public void setAccount_id(String account_id) {
		this.account_id = account_id;
	}

	public ArrayList<Snippet> getSnippets() {
		return snippets;
	}

	public void setNoteCollection_id(String note_collection_id) {
		this.note_collection_id = note_collection_id;
	}

	public String getNoteCollection_id() {
		return note_collection_id;
	}

}




