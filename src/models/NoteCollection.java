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
	static String[] tableHeaders = { "id", "title", "account_id" };

	public NoteCollection() {

	}

	public NoteCollection(CSVRecord noteCollectionRecord) {
		this.id = noteCollectionRecord.getId();
		this.title = noteCollectionRecord.getValueAtField("title");
		this.account_id = noteCollectionRecord.getValueAtField("account_id");
	}

	/**
	 * This method creates a new record in the note_collections_table
	 * 
	 * This method handles the creation of a note collection. It does this by
	 * using two CSVWriter objects. The first CSVWriter object contains the
	 * whole note_collections_table and the second CSVWriter object contains
	 * only records that match the NoteCollectionCreationRequest objects title
	 * value with their title field. The second CSVWriter object should be
	 * empty, otherwise it is assumed that a note collection with the same name
	 * already exists for that user and a naming conflict error is thrown.
	 * 
	 * @param request
	 *            The NoteCollectionCreationRequest object that provides the
	 *            data for the note collection to be created
	 */
	public static void create(NoteCollectionCreationRequest request) {
		CSVWriter noteCollectionTableWriter = constructWriter();

		// Get other notes belonging to this user with the same title
		// Validate that this table is empty (otherwise they can't use this
		// title).
		CSVReader notesWithSameTitle =
				noteCollectionTableWriter.where("account_id").is(request.getAccountId()).where("title")
						.isIgnoreCase(request.getTitle());
		if (notesWithSameTitle.getTable().isEmpty()) {
			noteCollectionTableWriter.append(request.getTitle(), request.getAccountId());
			noteCollectionTableWriter.write();
		} else {
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
