package noteTaker;

import java.util.ArrayList;

import javafx.stage.Stage;
import models.Account;
import models.Note;
import models.Snippet;

/**
 * The Session class is a singleton class that serves to hold variables for 
 * both account sessions and the application session.
 * 
 * Account session: 
 * It will contain variables corresponding to the currently logged in account
 * and all dynamic content that follows from that.
 * 
 * Application session:
 * Application-wide variables that are set at runtime and used until the application is exited.
 * This includes a Stage object created by the application start method that can be referenced
 * by all other classes. All application session variables can only be set once using
 * setter methods.
 * 
 */

public class Session {

	private static Session instance = null;

	private Stage stage;
	private boolean sessionCreated;
	private Account account;
	private boolean editingNote;
	private Note currentNote;
	private ArrayList<Snippet> matchingSnippets;

	private Session() {
		// Application session variables. These variables can only be set once.
		stage = null;

		// Account session variables
		sessionCreated = false;
		account = null;
		editingNote = false;
		currentNote = null;
		matchingSnippets = null;
	}

	public void create(Account account) {
		this.destroy();
		this.sessionCreated = true;
		this.account = account;
	}

	public void destroy() {
		this.sessionCreated = false;
		this.account = null;
		this.currentNote = null;
		this.editingNote = false;
		this.matchingSnippets = null;
	}

	public boolean isCreated() {
		return this.sessionCreated;
	}

	public Stage getStage() {
		return this.stage;
	}

	public Account getAccount() {
		return this.account;
	}

	// This method can only be called once. Once a non-null
	// Stage object is passed in, further calls to setStage
	// return false.
	public boolean setStage(Stage stage) {
		boolean stageUnset = (this.stage == null);
		if (stageUnset){
			this.stage = stage;			
		}
		return stageUnset;
	}

	public static Session getInstance() {
		if (instance == null) {
			instance = new Session();
		}
		return instance;
	}

	public boolean isSessionCreated() {
		return sessionCreated;
	}

	public void setSessionCreated(boolean sessionCreated) {
		this.sessionCreated = sessionCreated;
	}

	public boolean isEditingNote() {
		return editingNote;
	}

	public void setEditingNote(boolean editingNote) {
		this.editingNote = editingNote;
	}

	public Note getCurrentNote() {
		return currentNote;
	}

	public void setCurrentNote(Note currentNote) {
		this.currentNote = currentNote;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public ArrayList<Snippet> getMatchingSnippets() {
		return matchingSnippets;
	}

	public void setMatchingSnippets(ArrayList<Snippet> matchingSnippets) {
		this.matchingSnippets = matchingSnippets;
	}
	
	

}




