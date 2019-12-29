package gui;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import serverRdF.DataBaseConnection;
import util.Sentence;

public class CSVfileController {
	public void importCSV(ActionEvent e) throws IOException {
		Main.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("ManageSentence.fxml"))));
	}
	
	public void fileChoose(ActionEvent e) throws IOException {
		FileChooser file = new FileChooser();
		Stage primaryStage = null;
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
		file.getExtensionFilters().add(extFilter);
		File selectedFile = file.showOpenDialog(primaryStage); //TODO questo dovrebbe essere il csv con frasi, inviare a server
		System.out.println("il percorso è: "+ selectedFile.getAbsolutePath());
		cvsSentenceReader(selectedFile.getAbsolutePath());
	}
	public void cvsSentenceReader(String filePath) {
		try {
			//TODO COLLEGARE CON CSVFILECONTROLLER
			CSVReader reader = new CSVReader(new FileReader(filePath));
			String[] nextLine = new String[2];
			List<Sentence> sentences = new ArrayList<Sentence>();
			while ((nextLine = reader.readNext()) != null) {
				Sentence sentence = new Sentence(nextLine[0], nextLine[1]);
				if(sentence.getSentence().length()<60 && sentence.getHint().length()<60){
					sentences.add(sentence);
				}
			}
			//TODO PASSARE LA LISTA A SERVER THREAD E POI A DATABASECONNECTION
			DataBaseConnection query =new DataBaseConnection();
			try {

				query.insertSentences(sentences, query.getOneUser("i@i.it", "boia"));
			} catch (SQLException e) {
				e.printStackTrace();
			}

		} catch (IOException | CsvValidationException e) {
			e.printStackTrace();
		}
	}

}
