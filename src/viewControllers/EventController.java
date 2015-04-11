package viewControllers;

import java.io.IOException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.web.HTMLEditor;
import models.Note;
import models.Snippet;
import noteTaker.Session;
import controllers.AccountsController;
import controllers.NotesController;
import controllers.SnippetsController;
import exceptions.DeleteNullNoteException;
import exceptions.NoSessionNoteSetException;

public class EventController extends ViewController {

	AccountsController accountsController;

	public EventController() {
		accountsController = new AccountsController();
	}

	@FXML
	protected void tryLogin(ActionEvent e) throws IOException {
		// Get username and password input values to give to controller
		String username = getTextFieldById("username_input").getText();
		String password = (getPasswordFieldById("password_input")).getText();

		// AccountsController tries to login with these credentials
		String[] errors = AccountsController.login(username, password);
		if (errors.length == 0) {
			// Can reference account for info by referencing Session class.
			System.out.println("Welcome " + getSession().getAccount().getUsername() + "!");

			// Change GUI to show logged in user
			getButtonById("logout_button").setVisible(true);
			getLabelById("NOTETAKER_text").setVisible(true);
			getAnchorPaneById("note_edit_pane").setVisible(true);
			getPaneById("note_buttons").setVisible(true);
			getAnchorPaneById("dashboard").setDisable(false);
			getButtonById("note_delete").setVisible(true);
			getButtonById("collection_delete").setVisible(true);
			getButtonById("collection_create").setVisible(true);
			// Hide registration and login buttons
			getLabelById("newaccount").setVisible(false);
			getGridPaneById("login_pane_root").setVisible(false);

			// Show all the user's notes in the side panel
			populateNotesList();

		} else {
			// Present errors to user
			getTextById("login_fail_text").setText(errors[0]);
			getTextById("login_fail_text").setVisible(true);
		}

	}

	// check for verification of username and password
	@FXML
	protected void tryRegistration(Event e) throws IOException {
		String username = getTextFieldById("new_username").getText();
		String password = getPasswordFieldById("new_password").getText();
		String confirmPassword = getPasswordFieldById("confirm_password").getText();

		// change GUI for a logged-in user
		String[] errors = accountsController.register(username, password, confirmPassword);
		if (errors.length == 0) {
			System.out.println("Account Created");
			getAnchorPaneById("create_account_pane").setVisible(false);
		}

		// Show errors for incorrect user information
		else {
			for (String error : errors) {
				System.out.println(error);
				getLabelById("create_account_error_text").setText(error);
			}
		}
	}

	// create a new note

	@FXML
	protected void noteCreationAction(Event e) throws IOException {
		String noteTitle = getTextFieldById("note_title_input").getText();
		String[] errors = NotesController.create(noteTitle);

		if (errors.length == 0) {
			hideDashboardWindows();
			populateNotesList();
		} else {
			System.out.println(errors[0]);
		}
	}

	@FXML
	protected void logoutButtonClicked(MouseEvent e) throws IOException {

		// save the currently selected note from the session in the database
		saveCurrentNote();

		getLabelById("NOTETAKER_text").setVisible(false);
		getButtonById("logout_button").setVisible(false);
		getPaneById("note_buttons").setVisible(false);
		getAnchorPaneById("note_edit_pane").setVisible(false);
		getAnchorPaneById("dashboard").setDisable(true);
		getButtonById("note_delete").setVisible(false);
		getButtonById("collection_delete").setVisible(false);
		getButtonById("collection_create").setVisible(false);
		// Make login and registration buttons visible again
		getGridPaneById("login_pane_root").setVisible(true);
		getLabelById("newaccount").setVisible(true);
		hideDashboardWindows();

		// Remove all results from the results list
		clearResultsList();

		// Resets the interface to original appearance
		resetNoteInterface();

		// log out through accounts controller
		AccountsController.logout();

	}
	
	@FXML protected void confirmDeleteAccount(MouseEvent e) throws IOException{
		// when "delete account" is clicked in settings window
		 	getTextById("confirm_delete_message").setVisible(true); 
			getPasswordFieldById("confirm_delete_account_password").setVisible(true); 
			getTextById("change_password").setVisible(false); 
			
		
	}

	@FXML
	protected void deleteNote(MouseEvent e) throws IOException {
		try {
			Note currentNote = getSession().getCurrentNote();
			if (currentNote != null) {
				NotesController.delete(getSession().getCurrentNote());
				resetNoteInterface();

				// Trigger the search on the input so that the results
				// are updates.
				searchTextChanged();
			} else {
				throw new DeleteNullNoteException("Can't find the note to delete");
			}
		} catch (DeleteNullNoteException exception) {
			exception.printStackTrace();
		}
	}
	
	@FXML protected void deleteCollection(MouseEvent e) throws IOException{
		// when "delete collection" icon is clicked on dashboard
	}

	@FXML
	protected void deleteAccount(Event e) throws IOException {
		// code to delete account goes here
		//  if password matches, remove account and do:
		    //Logout()
		//else: 
		getTextById("confirm_delete_message").setText("password does not match");
	}

	@FXML protected void confirmChangePassword(MouseEvent e) throws IOException {
		
		
	}
	
	@FXML protected void createCollection(MouseEvent e) throws IOException{
		 //when the create collection button is clicked
		hideDashboardWindows();
		Node settings = getNodeById("create_collection_pane");
		settings.toFront();
		settings.setVisible(true);

	}

	@FXML
	protected void collectionCreationAction(Event e) throws IOException {
		// when the title is entered into the collection form and hit save

	}

	@FXML
	protected void collectionCancelAction(Event e) throws IOException {
		getAnchorPaneById("create_collection_pane").setVisible(false);
	}

	@FXML
	protected void createNoteButtonClicked(MouseEvent e) throws IOException {
		hideDashboardWindows();
		AnchorPane noteForm = getAnchorPaneById("create_note_pane");
		noteForm.toFront();
		noteForm.setVisible(true);
	}

	@FXML
	protected void noteButtonClicked(MouseEvent e) throws IOException {
		getLabelById("noteTaker_text").setText("YES");
	}

	@FXML
	protected void titleButtonClicked(MouseEvent e) throws IOException {
		getLabelById("noteTaker_text").setText("NO");
	}

	@FXML
	protected void modifiedButtonClicked(MouseEvent e) throws IOException {
		getLabelById("noteTaker_text").setText("MAYBE");
	}

	@FXML
	protected void dateButtonClicked(MouseEvent e) throws IOException {
		getLabelById("noteTaker_text").setText("..YES");
	}

	@FXML
	protected void tagsButtonClicked(MouseEvent e) throws IOException {
		hideDashboardWindows();
		Label text = getLabelById("noteTaker_text");
		text.setVisible(true);
		text.toFront();
		text.setText("searchn' tags!");
	}

	@FXML
	protected void settingsButtonClicked(MouseEvent e) throws IOException {
		hideDashboardWindows();
		Node settings = getNodeById("account_settings_pane");
		settings.toFront();
		settings.setVisible(true);
	}

	@FXML
	protected void createAccountClicked(MouseEvent e) throws IOException {
		getAnchorPaneById("create_account_pane").setVisible(true);
	}

	@FXML
	protected void tryAccount(MouseEvent e) throws IOException {
		// What does this method do?
	}

	@FXML
	protected void cancelAccountButtonClicked(MouseEvent e) throws IOException {
		hideDashboardWindows();
		getAnchorPaneById("create_account_pane").setVisible(false);
	}

	@FXML
	protected void saveSettingsButtonClicked(MouseEvent e) throws IOException {
		hideDashboardWindows();
	}

	@FXML
	protected void noteCancelAction(MouseEvent e) throws IOException {
		hideDashboardWindows();
	}

	@FXML
	protected void searchTextChanged() {
		TextField searchInput = getTextFieldById("search_box");
		String query = searchInput.getText();

		if (getSession().getSearchMode() == Session.TITLE_SEARCH_MODE) {
			NotesController.search(query);
			ArrayList<Note> notes = NotesController.search(query);
			populateNotesList(notes);
		} else if (getSession().getSearchMode() == Session.TAG_SEARCH_MODE) {

		}

	}

	// //////////////////
	// Helper Methods //
	// //////////////////

	private void hideDashboardWindows() {
		getNodeById("account_settings_pane").setVisible(false);
		getAnchorPaneById("create_note_pane").setVisible(false);
		getLabelById("noteTaker_text").setVisible(false);
		getAnchorPaneById("create_collection_pane").setVisible(false);

	}

	// With no argument populate results with all notes on account
	private void populateNotesList() {
		ArrayList<Note> notes = getSession().getAccount().getNotes();
		populateNotesList(notes);
	}

	private void populateNotesList(ArrayList<Note> notes) {
		AnchorPane parent = getAnchorPaneById("dashboard");

		ListView<String> oldListView = getListViewById("results_list");

		if (oldListView != null)
			parent.getChildren().remove(oldListView);

		// Populate ObservableList object with the titles for the account's
		// notes
		ObservableList<String> noteTitles = FXCollections.observableArrayList();
		for (Note note : notes) {
			noteTitles.add(note.getTitle());
		}

		final ListView<String> resultsList = new ListView<String>(noteTitles);
		parent.getChildren().add(resultsList);
		addStyleToResultsList(resultsList);
		addEventListenersToResultsList(resultsList, noteTitles);
	}

	private void clearResultsList() {
		AnchorPane parent = getAnchorPaneById("dashboard");
		ListView<String> oldListView = getListViewById("results_list");
		if (oldListView != null)
			parent.getChildren().remove(oldListView);

		ObservableList<String> emptyArrayList = FXCollections.observableArrayList();
		ListView<String> resultsList = new ListView<String>(emptyArrayList);
		parent.getChildren().add(resultsList);
		addStyleToResultsList(resultsList);
	}

	private void addStyleToResultsList(ListView<String> resultsList) {
		resultsList.setLayoutX(74.0);
		resultsList.setLayoutY(44.0);
		resultsList.setPrefWidth(330.0);
		resultsList.setPrefHeight(545.0);
		resultsList.setId("results_list");
		AnchorPane.setTopAnchor(resultsList, 84.0);
		AnchorPane.setRightAnchor(resultsList, 0.0);
		AnchorPane.setBottomAnchor(resultsList, 0.0);
	}

	private void addEventListenersToResultsList(final ListView<String> resultsList, ObservableList<String> results) {
		resultsList.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				handleResultSelectionEvent(resultsList);
			}
		});

		resultsList.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				handleResultSelectionEvent(resultsList);
			}
		});
	}

	private void handleResultSelectionEvent(ListView<String> resultsList) {
		String item = resultsList.getSelectionModel().getSelectedItem();
		Note selectedNote = Note.getNoteByTitle(item);
		Note currentNote = getSession().getCurrentNote();

		// Only run if the clicked note is not currently open
		if (currentNote == null || !currentNote.getId().equals(selectedNote.getId())) {
			if (getSession().isEditingNote())
				saveCurrentNote();
			getSession().setCurrentNote(selectedNote);
			initializeNoteInterface(selectedNote);
		}
	}

	private void initializeNoteInterface(Note note) {
		resetNoteInterface();
		getSession().setEditingNote(true);

		getButtonById("note_delete").setDisable(false);
		HTMLEditor parentHTMLEditor = getHTMLEditorById("note_HTMLEditor");
		parentHTMLEditor.setDisable(false);
		parentHTMLEditor.setOpacity(1);

		populateNoteInterface(parentHTMLEditor);
	}

	private void saveCurrentNote() {
		Note currentNote = getSession().getCurrentNote();
		if (currentNote != null) {
			HTMLEditor parentHTMLEditor = getHTMLEditorById("note_HTMLEditor");
			String content = parentHTMLEditor.getHtmlText();
			SnippetsController.saveNote(currentNote, content);
		}
	}

	private void resetNoteInterface() {
		HTMLEditor parentHTMLEditor = getHTMLEditorById("note_HTMLEditor");
		parentHTMLEditor.setHtmlText("");
		parentHTMLEditor.setDisable(true);
		parentHTMLEditor.setOpacity(0.8);
		// disable the delete note button
		getButtonById("note_delete").setDisable(true);
	}

	private void populateNoteInterface(HTMLEditor parentHTMLEditor) {
		try {
			if (getSession().getCurrentNote() != null) {

				String totalContent = "";
				int counter = 0;
				ArrayList<Snippet> snippets = getSession().getCurrentNote().getSnippets();
				for (Snippet s : snippets) {
					counter++;
					totalContent += s.getContent();
					if (counter < snippets.size()) {
						totalContent += SnippetsController.snippetSeparator;
					}
				}
				parentHTMLEditor.setHtmlText(totalContent);

			} else {
				throw new NoSessionNoteSetException(
						"Trying to populate content, but no note has been set in the session.");
			}
		} catch (NoSessionNoteSetException e) {
			e.printStackTrace();
		}
	}

}
