package gui;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

public class ModifySentenceController {
	public void quit(ActionEvent e) throws IOException{
		Main.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("ManageSentence.fxml"))));
	}

}
