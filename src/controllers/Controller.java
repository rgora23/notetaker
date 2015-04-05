package controllers;

import java.util.ArrayList;

import noteTaker.Session;

// These are the controllers that correspond to the MVC architecture.
// They handle the logic between the view controllers and the models.
public abstract class Controller {
	public Controller() { }
	

	

	protected static String[] ArrayListToStringArray(ArrayList<String> arrayList) {
		return arrayList.toArray(new String[arrayList.size()]);
	}
	
	protected static Session getSession() {
		return Session.getInstance();
	}


	
}




