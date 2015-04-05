package models;

import java.util.ArrayList;

import noteTaker.ErrorMessages;
import requestHelpers.NoteCollectionCreationRequest;
import csv.CSVReader;
import csv.CSVRecord;
import csv.CSVWriter;

public class NoteCollection {
	ArrayList<Note> notesCollection;
	String id;
	String title;
	String account_id;
	static String tablePath = "database/note_collections_table";
	static String[] tableHeaders = {"id", "title", "account_id"};
	
	public NoteCollection() {
		
	}
	
	public NoteCollection(CSVRecord noteCollectionRecord) {
		this.id = noteCollectionRecord.getId();
		this.title = noteCollectionRecord.getValueAtField("title");
		this.account_id = noteCollectionRecord.getValueAtField("account_id");
		}
	
	public static void create(NoteCollectionCreationRequest request){
		CSVWriter noteCollectionTableWriter = constructWriter();

		// Get other notes belonging to this user with the same title
		// Validate that this table is empty (otherwise they can't use this title).
		CSVReader notesWithSameTitle = noteCollectionTableWriter
		.where("account_id").is(request.getAccountId())
		.where("title").isIgnoreCase(request.getTitle());
		if (notesWithSameTitle.getTable().isEmpty()) {
		noteCollectionTableWriter.append(request.getTitle(), request.getAccountId());
		noteCollectionTableWriter.write();
		}
		else {
		request.addError(ErrorMessages.NOTE_COLLECTION_TITLE_TAKEN);
		}
	
	}
	
	private static CSVWriter constructWriter() {
		return Model.constructWriter(tablePath, tableHeaders);
	}
	
	private static CSVReader constructReader() {
		return Model.constructReader(tablePath, tableHeaders);
	}
}
