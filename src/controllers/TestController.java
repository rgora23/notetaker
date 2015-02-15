package controllers;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class TestController extends EventControllerParent {

	@FXML private Label test_text;
	
	@FXML protected void testMethod(MouseEvent e) throws IOException {
		System.out.println("Now this worked!");
	}

}
