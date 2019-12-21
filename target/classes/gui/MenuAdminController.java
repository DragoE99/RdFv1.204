package gui;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

public class MenuAdminController {
	public void spectate(ActionEvent e) throws IOException {		
		Main.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("SelectLobby.fxml"))));
	}

	public void manageSentence(ActionEvent e) throws IOException {		
		Main.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("ManageSentence.fxml"))));
	}

	public void statistics(ActionEvent e) throws IOException {		
		Main.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("Statistics.fxml"))));
	}

	public void settings(ActionEvent e) throws IOException {		
		Main.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("Settings.fxml"))));
	}

}
