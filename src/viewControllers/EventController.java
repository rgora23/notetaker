package viewControllers;

import java.io.IOException;
import java.util.ArrayList;

import noteTaker.Session;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.HTMLEditor;
import models.Note;
import models.Snippet;
import controllers.AccountsController;
import controllers.NotesController;
import controllers.SnippetsController;
import controllers.TagsController;
import exceptions.DeleteNullNoteException;
import exceptions.NoSessionNoteSetException;

public class EventController extends ViewController {

	AccountsController accountsController;

	public EventController() {
		accountsController = new AccountsController();
	}

	/**
	 * Event controller method for when the user is trying to log in
	 * 
	 * This event controller is called when the user tries to log in. It pulls
	 * the text entered by the user in the username text box and password text
	 * box and passes it in to the login method provided by the
	 * AccountsController class. The login method returns a string array that
	 * contains any errors that occured while processing the users credentials.
	 * If the array is empty, the program logs the user in and greets the user
	 * with a greeting and makes hidden elements of the UI visible. If the array
	 * is not empty, login failure text is set to visible and displays the error
	 * created by the user.
	 * 
	 * @param e
	 * @throws IOException
	 */
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
			getButtonById("save_note_button").setVisible(true);
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

	/**
	 * Event controller method for when the user is trying to register
	 * 
	 * This event controller is called when the user is trying to create an
	 * account with noteTaker. It first checks if whether or not the user has
	 * properly inputted information in the correct format on the registration
	 * form. It does this by using the register method found in the
	 * accountsController class. If the user leaves the user name, password, or
	 * confirm password field empty, the register method will return a String
	 * array containing the error that occured. If this array is empty, a
	 * message stating account creation was successful is displayed and the
	 * create_account_pane becomes invisible. If the array is not empty, the
	 * method iterates through the array and displays each error.
	 * 
	 * @param e
	 * @throws IOException
	 */
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

	/**
	 * Event controller for note creation
	 * 
	 * This method is called when the user wishes to create a note. It grabs the
	 * String inputted by the user in the note title box and passes it as a
	 * parameter in to the create method provided by the NotesController class.
	 * The create method returns an array containing any errors that are
	 * produced by the title String provided by the user and the returned array
	 * is stored in a local variable. The error that is most likely to occur is
	 * if the user entered a title that matches a note already belonging to the
	 * user. If the errors array is empty, it is assumed the title String is
	 * unique and valid as a note name. If this is true the user note list is
	 * populated with the new note and the dashboard windows are hidden. If this
	 * is not true, an error message is displayed.
	 * 
	 * @param e
	 * @throws IOException
	 */
	@FXML
	protected void noteCreationAction(Event e) throws IOException {
		TextField noteTitleInput = getTextFieldById("note_title_input");
		String noteTitle = noteTitleInput.getText();
		String[] errors = NotesController.create(noteTitle);

		if (errors.length == 0) {
			hideDashboardWindows();
			noteTitleInput.setText("");
			populateNotesList();
		} else {
			System.out.println(errors[0]);
		}
	}

	/**
	 * Event controller for logging out
	 * 
	 * This method is called when the user clicks the log out button. It ensures
	 * the various panes and buttons that are only viewable by a logged in user
	 * become invisible and that the results list and note interface are
	 * cleared/reset. It does this throgh the use of helper methods. It also
	 * ensures the user is logged out by using the logout method found in the
	 * AccountsController class.
	 * 
	 * @param e
	 * @throws IOException
	 */
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
		getButtonById("save_note_button").setVisible(false);
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

	/**
	 * Event controller method for confirming account deletion
	 * 
	 * This method is called after the user has clicked the delete account
	 * button from the account settings page. It asks the user if he/she is okay
	 * with proceeding with account deletion and makes the change password
	 * button text invisible as well. This method is called after the
	 * deleteAccount event controller method is called
	 * 
	 * @param e
	 * @throws IOException
	 */
	@FXML
	protected void confirmDeleteAccount(MouseEvent e) throws IOException {
		// when "delete account" is clicked in settings window
		getTextById("confirm_delete_message").setVisible(true);
		getPasswordFieldById("confirm_delete_account_password").setVisible(true);
		getTextById("change_password").setVisible(false);

	}

	/**
	 * Event controller for note deletion
	 * 
	 * This method is called when the user wishes to delete a note. It checks to
	 * make sure that a note is currently selected and calls the delete static
	 * method found in the NotesController class. If a note is not selected, a
	 * DeleteNullNoteException is raised and an error message is displayed
	 * 
	 * @param e
	 * @throws IOException
	 */
	@FXML
	protected void deleteNote(MouseEvent e) throws IOException {
		try {
			Note currentNote = getSession().getCurrentNote();
			if (currentNote != null) {
				NotesController.delete(currentNote);
				resetNoteInterface();

				// Trigger the search on the input so that the results
				// are updated.
				searchTextChanged();
			} else {
				throw new DeleteNullNoteException("Can't find the note to delete");
			}
		} catch (DeleteNullNoteException exception) {
			exception.printStackTrace();
		}
	}

	@FXML
	protected void deleteCollection(MouseEvent e) throws IOException {
		// when "delete collection" icon is clicked on dashboard
	}

	/**
	 * This method handles account deletion
	 * @param e
	 * @throws IOException
	 */
	@FXML
	protected void deleteAccount(Event e) throws IOException {
		// code to delete account goes here
		// if password matches, remove account and do:
		// Logout()
		// else:
		getTextById("confirm_delete_message").setText("password does not match");
	}

	@FXML
	protected void confirmChangePassword(MouseEvent e) throws IOException {

	}

	@FXML
	protected void createCollection(MouseEvent e) throws IOException {
		// when the create collection button is clicked
		hideDashboardWindows();
		Node settings = getNodeById("create_collection_pane");
		settings.toFront();
		settings.setVisible(true);

	}

	/**
	 * This method handles the events that occur after the note collection
	 * creation button is clicked.
	 * 
	 * @param e
	 * @throws IOException
	 */
	@FXML
	protected void collectionCreationAction(Event e) throws IOException {
		// when the title is entered into the collection form and hit save

	}

	/**
	 * This method handles the events that occur when a user is viewing the note
	 * collection creation form and hits the cancel button. It hides the note
	 * collection creation form.
	 * 
	 * @param e
	 *            The mouse event that is created after the cancel button is
	 *            clicked
	 * @throws IOException
	 */
	@FXML
	protected void collectionCancelAction(Event e) throws IOException {
		getAnchorPaneById("create_collection_pane").setVisible(false);
	}

	/**
	 * Event controller that is called when the user clicks on the new note button.
	 * This method makes the note form visible and brings it to the front of the GUI.
	 * @param e
	 * @throws IOException
	 */
	@FXML
	protected void createNoteButtonClicked(MouseEvent e) throws IOException {
		hideDashboardWindows();
		AnchorPane noteForm = getAnchorPaneById("create_note_pane");
		noteForm.toFront();
		noteForm.setVisible(true);
	}
	
	/** 
	 * Event controller that is called when the note button is clicked
	 * @param e
	 * @throws IOException
	 */
	@FXML
	protected void noteButtonClicked(MouseEvent e) throws IOException {
		getLabelById("noteTaker_text").setText("YES");
	}

	/**
	 * Event controller that is called when the user clicks on the title button
	 * @param e
	 * @throws IOException
	 */
	@FXML
	protected void titleButtonClicked(MouseEvent e) throws IOException {
		getLabelById("noteTaker_text").setText("NO");
	}

	@FXML
	protected void modifiedButtonClicked(MouseEvent e) throws IOException {
		getLabelById("noteTaker_text").setText("MAYBE");
	}

	/**
	 * Event controller that is called when a user clicks on the date button
	 * @param e
	 * @throws IOException
	 */
	@FXML
	protected void dateButtonClicked(MouseEvent e) throws IOException {
		getLabelById("noteTaker_text").setText("..YES");
	}
	
	/**
	 * Event controller that is called when a user clicks on the save note button.
	 */
	@FXML
	protected void saveNoteButtonClicked(MouseEvent e) throws IOException {
		saveCurrentNote();
	}
	
	/**
	 * Event controller called when the user clicks on the tags button
	 * @param e
	 * @throws IOException
	 */
	@FXML
	protected void tagsButtonClicked(MouseEvent e) throws IOException {
		hideDashboardWindows();
		Label text = getLabelById("noteTaker_text");
		text.setVisible(true);
		text.toFront();
		text.setText("searchn' tags!");
	}

	/**
	 * This method handles the event that occurs after the settings button is
	 * clicked. When the settings button is clicked, it hides the dashboard
	 * windows and displays the form for changing settings
	 * 
	 * @param e
	 *            The mouse event that is created when the settings button is
	 *            clicked
	 * @throws IOException
	 */
	@FXML
	protected void settingsButtonClicked(MouseEvent e) throws IOException {
		hideDashboardWindows();
		Node settings = getNodeById("account_settings_pane");
		settings.toFront();
		settings.setVisible(true);
	}

	/**
	 * This method handles the event that occurs after the create account button
	 * is clicked. It pulls up the account creation form.
	 * 
	 * @param e
	 *            The mouse event that is created when the create account button
	 *            is clicked
	 * @throws IOException
	 */
	@FXML
	protected void createAccountClicked(MouseEvent e) throws IOException {
		getAnchorPaneById("create_account_pane").setVisible(true);
	}

	@FXML
	protected void tryAccount(MouseEvent e) throws IOException {

	}

	/**
	 * This method handles the event that occurs after the cancel button is
	 * clicked when the user is trying to create a new account. It hides the
	 * dashboard windows and hides the account creation form.
	 * 
	 * @param e
	 *            The mouse event that is created after the cancel button is
	 *            clicked
	 * @throws IOException
	 */
	@FXML
	protected void cancelAccountButtonClicked(MouseEvent e) throws IOException {
		hideDashboardWindows();
		getAnchorPaneById("create_account_pane").setVisible(false);
	}

	@FXML
	protected void cancelSettingsButtonClicked(MouseEvent e) throws IOException {
		hideDashboardWindows();
	}

	@FXML
	protected void noteCancelAction(MouseEvent e) throws IOException {
		hideDashboardWindows();
	}

	@FXML
	protected void changePassword(Event e) throws IOException {
		hideDashboardWindows();
	}

	@FXML
	protected void ChangePasswordClicked(MouseEvent e) throws IOException {
		getPasswordFieldById("current_password").setVisible(true);
		getPasswordFieldById("new_password_settings").setVisible(true);
		getPasswordFieldById("confirm_new_password").setVisible(true);
	}

	@FXML
	protected void searchTextChanged() {
		TextField searchInput = getTextFieldById("search_box");
		String query = searchInput.getText();

		RadioButton titleSearchButton = getRadioButtonById("title_search_button");
		RadioButton tagSearchButton = getRadioButtonById("tag_search_button");

		if ( query.isEmpty() || titleSearchButton.isSelected() ) {
			NotesController.search(query);
			ArrayList<Note> notes = NotesController.search(query);
			populateNotesList(notes);
		} 
		else if ( tagSearchButton.isSelected() ) {
			TagsController.search(query);
			ArrayList<Snippet> snippets = getSession().getMatchingSnippets();
			populateSnippetsList(snippets);
		}

	}

	////////////////////
	// Helper Methods //
	////////////////////

	/**
	 * This method hides all panes associated with the dashboard. This includes
	 * the account settings pane, note creation pane, text body, and collection
	 * creation pane
	 */
	private void hideDashboardWindows() {
		getNodeById("account_settings_pane").setVisible(false);
		getAnchorPaneById("create_note_pane").setVisible(false);
		getLabelById("noteTaker_text").setVisible(false);
		getAnchorPaneById("create_collection_pane").setVisible(false);

	}

	/**
	 * View controller method that displays notes
	 * 
	 * This view controller takes no argument and by default displays all notes
	 * belonging to an account on the side pane
	 */
	// With no argument populate results with all notes on account
	private void populateNotesList() {
		ArrayList<Note> notes = getSession().getAccount().getNotes();
		populateNotesList(notes);
	}

	/**
	 * View controller that displays selected notes
	 * 
	 * This view controller method allows an array list populated with notes to
	 * be passed and displays those notes on the side panel. It checks to see if
	 * the current list view is null or not. If the current list view is null,
	 * it populates it with the notes provided in the passed parameter array
	 * list. If the current list view is not null and contains notes, it deletes
	 * the current list view and populates the list view with the new notes.
	 * 
	 * @param notes
	 */
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
		addEventListenersToTitlesList(resultsList, noteTitles);
	}
	
	private void populateSnippetsList(ArrayList<Snippet> snippets) {
		AnchorPane parent = getAnchorPaneById("dashboard");

		ListView<String> oldListView = getListViewById("results_list");
		if (oldListView != null)
			parent.getChildren().remove(oldListView);
		
		// Populate ObservableList object with the contents of the snippets
		ObservableList<String> snippetContents = FXCollections.observableArrayList();
		for (Snippet snippet : snippets) {
			String content = snippet.getPlainText();
			snippetContents.add(content);
		}

		final ListView<String> resultsList = new ListView<String>(snippetContents);
		parent.getChildren().add(resultsList);
		addStyleToResultsList(resultsList);
		addEventListenersToSnippetsList(resultsList, snippetContents);
	}

	/**
	 * This method clears the search list view
	 */
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

	/**
	 * This method styles new ListView objects in the scene that get created
	 * when the user performs a search and when the scene is initially loaded
	 * 
	 * @param resultsList
	 */
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

	/**
	 * This method handles the selection method when clicking in the results
	 * list
	 * 
	 * @param resultsList
	 * @param results
	 */
	private void addEventListenersToTitlesList(final ListView<String> resultsList, ObservableList<String> results) {
		resultsList.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				handleTitleSelectionEvent(resultsList);
			}
		});

		resultsList.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				handleTitleSelectionEvent(resultsList);
			}
		});
	}
	
	private void addEventListenersToSnippetsList(final ListView<String> resultsList, ObservableList<String> results) {
		resultsList.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				handleSnippetSelectionEvent(resultsList);
			}
		});

		resultsList.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				handleSnippetSelectionEvent(resultsList);
			}
		});
	}

	/**
	 * This method handles selecting an item after searching for it in the
	 * results list
	 * 
	 * This method displays a note if it is not currently open and being edited.
	 * It saves the note that is currently open and initializes a new note
	 * interface with content from the newly selected note.
	 * 
	 * @param resultsList
	 *            the ListView object the user has selected from
	 */
	private void handleTitleSelectionEvent(ListView<String> resultsList) {
		String item = resultsList.getSelectionModel().getSelectedItem();
		Note selectedNote = Note.getNoteByTitle(item);
		Note currentNote = getSession().getCurrentNote();

		// Only run if the clicked note is not currently open
		if (currentNote == null || !currentNote.getId().equals(selectedNote.getId())) {
			if (getSession().isEditingNote()) {
				saveCurrentNote();				
			}
			getSession().setCurrentNote(selectedNote);
			initializeNoteInterface(selectedNote);
		}
	}
	
	private void handleSnippetSelectionEvent(ListView<String> resultsList) {
		int index = resultsList.getSelectionModel().getSelectedIndex();
		Snippet selectedSnippet = getSession().getMatchingSnippets().get(index);
		Note selectedNote = selectedSnippet.getAssociatedNote();
		Note currentNote = getSession().getCurrentNote();
		// Only run if the clicked note is not currently open
		if (currentNote == null || !currentNote.getId().equals(selectedNote.getId())) {
			if (getSession().isEditingNote()){
				saveCurrentNote();				
			}
			getSession().setCurrentNote(selectedNote);
			initializeNoteInterface(selectedNote);
		}
	}

	/**
	 * This method starts the interface for users of the program to type notes
	 * 
	 * When this method is called, any note interfaces opened before are reset
	 * and all text on screen is deleted. The delete button for a note is also
	 * disabled and a new HTMLEditor object is created.
	 * 
	 * @param note
	 */
	private void initializeNoteInterface(Note note) {
		resetNoteInterface();
		getSession().setEditingNote(true);
		String title = getSession().getCurrentNote().getTitle();
		getLabelById("NOTETAKER_text").setText(title);
		getButtonById("note_delete").setDisable(false);
		getButtonById("save_note_button").setDisable(false);
		HTMLEditor parentHTMLEditor = getHTMLEditorById("note_HTMLEditor");
		parentHTMLEditor.setDisable(false);
		parentHTMLEditor.setOpacity(1);
		

		populateNoteInterface(parentHTMLEditor);
	}

	/**
	 * This controller method saves the current note being edited if it contains
	 * content
	 */
	private void saveCurrentNote() {
		Note currentNote = getSession().getCurrentNote();
		if (currentNote != null) {
			HTMLEditor parentHTMLEditor = getHTMLEditorById("note_HTMLEditor");
			String content = parentHTMLEditor.getHtmlText();
			SnippetsController.saveNote(currentNote, content);
		}
		// Update the results list by triggering the searchTextChange event
		searchTextChanged();
	}

	/**
	 * This method resets a note interface and deletes all text currently on
	 * screen. It also disables the note deletion button.
	 */
	private void resetNoteInterface() {
		HTMLEditor parentHTMLEditor = getHTMLEditorById("note_HTMLEditor");
		parentHTMLEditor.setHtmlText("");
		parentHTMLEditor.setDisable(true);
		parentHTMLEditor.setOpacity(0.8);
		
		getLabelById("NOTETAKER_text").setText("#NoteTaker");
		// disable the delete note button
		getButtonById("note_delete").setDisable(true);
		getButtonById("save_note_button").setDisable(true);
	}

	/**
	 * This method populates a note editing interface with a note
	 * 
	 * This method populates a note interface by using the snippets found in a
	 * note. It first intitializes an empty String variable. Then, it creates an
	 * array list that holds all of the snippets associated with a notes. Once
	 * this is done, it iterates through each snippet found in the array list
	 * and adds the content of each snippet to the empty string that was
	 * initialized earlier on in the method. Once the method is done iterating
	 * through all the snippets, and HTMLEditor object method is used to
	 * populate the interface with the snippet content.
	 * 
	 * @param parentHTMLEditor
	 *            the HTMLEditor object that is used to edit text in the note
	 */
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




