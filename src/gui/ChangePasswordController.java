package gui;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

public class ChangePasswordController {
	public void confirm(ActionEvent e) throws IOException {	
		Main.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("Login.fxml"))));
	}
	
	public void back(ActionEvent e) throws IOException {	
		Main.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("Settings.fxml"))));
	}

}
