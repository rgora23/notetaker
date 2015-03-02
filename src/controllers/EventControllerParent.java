package controllers;

import views.Main;

// This is the parent controller class that will be extended by all controllers.
// It holds common methods and variables that all controllers in this application
// will need access to.
public abstract class EventControllerParent {
	private Main main;
	
	public EventControllerParent() { }

	public Main getMain() {
		return main;
	}
	public void setMain(Main main) {
		this.main = main;
	}

}
