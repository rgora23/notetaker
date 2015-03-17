package controllers;

import java.util.ArrayList;

// These are the controllers that correspond to the MVC architecture.
// They handle the logic between the view controllers and the models.
public abstract class Controller {
	public Controller() { }
	

	

	public static String[] ArrayListToStringArray(ArrayList<String> arrayList) {
		return arrayList.toArray(new String[arrayList.size()]);
	}


	
}
