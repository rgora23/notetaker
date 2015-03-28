package models;

import java.util.ArrayList;

import noteTaker.ErrorMessages;
import noteTaker.Session;
import requestHelpers.NoteCreationRequest;
import csv.CSVReader;
import csv.CSVRecord;
import csv.CSVWriter;


public class Note {

	String title;
	String id;
	String account_id;
	static String tablePath = "database/notes_table";
	static String[] tableHeaders = {"id", "title", "account_id"};

	public Note(CSVRecord noteRecord) {
		this.id = noteRecord.getId();
		this.title = noteRecord.getValueAtField("title");
		this.account_id = noteRecord.getValueAtField("account_id");
	}

	public static void create(NoteCreationRequest request) {
		CSVWriter noteTableWriter = constructWriter();

		// Get other notes belonging to this user with the same title
		// Validate that this table is empty (otherwise they can't use this title).
		CSVReader notesWithSameTitle = noteTableWriter
				.where("account_id").is(request.getAccountId())
				.where("title").isIgnoreCase(request.getTitle());
		if (notesWithSameTitle.getTable().isEmpty()) {
			noteTableWriter.append(request.getTitle(), request.getAccountId());
			noteTableWriter.write();
		}
		else {
			request.addError(ErrorMessages.NOTE_TITLE_TAKEN);
		}

	}
	
	public ArrayList<Note> getAccountNotes() {
		ArrayList<Note> matchingNotes = new ArrayList<Note>();
		
		// If user is logged in to an account, get all matching notes
		// and construct them using the ORM. Populate ArrayList of Note objects
		// to return to controller.
		if (Session.getAccount() != null) {
			CSVReader noteTableReader = constructReader();
			CSVReader accountNotes = noteTableReader.where("account_id").is(Session.getAccount().getID());
			for (CSVRecord record : accountNotes.getTable()) {
				matchingNotes.add(new Note(record));
			}
		}
		else System.out.println("Not logged in.");
		return matchingNotes;
	}


	private static CSVWriter constructWriter() {
		return Model.constructWriter(tablePath, tableHeaders);
	}

	private static CSVReader constructReader() {
		return Model.constructReader(tablePath, tableHeaders);
	}

}
