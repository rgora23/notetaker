package noteTaker;

import javafx.stage.Stage;
import models.Account;

/*
 * The Session class serves to hold variables for both account sessions and application sessions.
 * 
 * Account session: 
 * It will contain static variables corresponding to the currently logged in account
 * and all dynamic content that follows from that.
 * 
 * Application session:
 * Application-wide variables that are set a runtime and used until the application is exited.
 * This includes a Stage object created by the application start method that can be referenced
 * by all other classes. All application session variables can only be set once using static
 * setter methods.
 * 
 */


public class Session {
	
	// Application session variables. These variables can only be set once.
	private static Stage stage = null;
	
	// Account session variables
	private static boolean sessionCreated = false;
	private static Account account = null;
	
	
	public static void create(Account account) {
		Session.destroy();
		Session.sessionCreated = true;
		Session.account = account;
	}
	
	public static void destroy() {
		Session.sessionCreated = false;
		Session.account = null;
	}
	
	public static boolean isCreated() {
		return Session.sessionCreated;
	}
	
	public static Stage getStage() {
		return Session.stage;
	}
	
	public static Account getAccount() {
		return Session.account;
	}
	
	// This method can only be called once. Once a non-null
	// Stage object is passed in, further calls to setStage
	// return false.
	public static boolean setStage(Stage stage) {
		boolean stageSet = Session.stage == null; 
		if (stageSet) Session.stage = stage;
		return stageSet;
	}
	
}
