package models;

import java.util.ArrayList;

import noteTaker.ErrorMessages;
import noteTaker.Session;
import requestHelpers.NoteCreationRequest;
import requestHelpers.SnippetsCreationRequest;
import csv.CSVReader;
import csv.CSVRecord;
import csv.CSVWriter;


public class Note extends Model {

	String title;
	String id;
	String account_id;
	String note_collection_id;
	ArrayList<Snippet> snippets;

	static String tablePath = "database/notes_table";
	static String[] tableHeaders = {"id", "title", "account_id","note_collection_id"};

	public Note(CSVRecord noteRecord) {
		this.id = noteRecord.getId();
		this.title = noteRecord.getValueAtField("title");
		this.account_id = noteRecord.getValueAtField("account_id");
		this.note_collection_id = noteRecord.getValueAtField(note_collection_id);
		this.snippets = Snippet.getSnippetsByNoteId(id);
	}

	public static void create(NoteCreationRequest request) {
		CSVWriter noteTableWriter = constructWriter();

		// Get other notes belonging to this user with the same title
		// Validate that this table is empty (otherwise they can't use this title).
		CSVReader notesWithSameTitle = noteTableWriter
				.where("account_id").is(request.getAccountId())
				.where("title").isIgnoreCase(request.getTitle());
		if (notesWithSameTitle.getTable().isEmpty()) {
			// 0 means it does not belong to any collections
			String noteCollectionId = "0";
			noteTableWriter.append(request.getTitle(), request.getAccountId(), noteCollectionId);
			noteTableWriter.write();
		}
		else {
			request.addError(ErrorMessages.NOTE_TITLE_TAKEN);
		}

	}
	
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
		}
		else System.out.println("Not logged in.");
		return matchingNotes;
	}
	
	public static Note getNoteByTitle(String title) {
		CSVReader noteTableReader = constructReader();
		CSVReader filteredReader = noteTableReader.where("title").isIgnoreCase(title);
		
		if ( !filteredReader.getTable().isEmpty() ) {
			CSVRecord record = filteredReader.getTable().get(0);
			return new Note(record);
		}
		else return null;
	}

	public static Note getNoteById(String id) {
		CSVReader noteTableReader = constructReader();
		Note note = new Note(noteTableReader.getRecordById(id));
		return note;
	}

	private static CSVWriter constructWriter() {
		return Model.constructWriter(tablePath, tableHeaders);
	}

	private static CSVReader constructReader() {
		return Model.constructReader(tablePath, tableHeaders);
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
	
	public void setNoteCollection_id (String note_collection_id){
		this.note_collection_id = note_collection_id; 
	}

	public String getNoteCollection_id() {
		return note_collection_id; 
	}
	
}




