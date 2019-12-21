package gui;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.FileChooser;

public class CreateSentenceController {
	public void create(ActionEvent e) throws IOException {
		Main.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("ManageSentence.fxml"))));
	}
}
