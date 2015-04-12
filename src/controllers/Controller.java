package controllers;

import java.util.ArrayList;

import noteTaker.Session;

/**
 * This is the abstract class many of the controller classes found in the
 * controller package inherit from. The controllers in the program handle
 * the logic between the views and models found in the MVC architecture.
 */
// These are the controllers that correspond to the MVC architecture.
// They handle the logic between the view controllers and the models.
public abstract class Controller {
	public Controller() {
	}

	protected static String[] ArrayListToStringArray(ArrayList<String> arrayList) {
		return arrayList.toArray(new String[arrayList.size()]);
	}

	protected static Session getSession() {
		return Session.getInstance();
	}

}




