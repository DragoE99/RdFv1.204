package gui;

import java.io.File;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class CSVfileController {
	public void importCSV(ActionEvent e) throws IOException {
		Main.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("ManageSentence.fxml"))));
	}
	
	public void fileChoose(ActionEvent e) throws IOException {
		FileChooser file = new FileChooser();
		Stage primaryStage = null;
		File selectedFile = file.showOpenDialog(primaryStage); //TODO questo dovrebbe essere il csv con frasi, inviare a server
		Main.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("ManageSentence.fxml"))));
	}
}
