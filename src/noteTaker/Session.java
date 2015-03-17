package noteTaker;

import javafx.stage.Stage;

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
	private static int userId = 0;
	
	public static void reset() {
		// need to set all variables to null
		
	}
	
	// 
	public static void create(int userId) {
		reset();
		Session.sessionCreated = true;
		Session.userId = userId;
	}
	
	public static void destroy() {
		Session.sessionCreated = false;
		Session.userId = 0;
	}
	
	public static boolean isCreated() {
		return sessionCreated;
	}
	
	public static Stage getStage() {
		return Session.stage;
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
