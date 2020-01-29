package gui;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import playerRdF.Client;
import util.Sentence;

/**
 * The controller for CSVfile window
 * 
 * @author Achille Lambrughi
 * @author Emanuele Drago
 * @author Lorenzo Ottaviani
 * @author Elisabeth Veronika Venturino
 *
 */
public class CSVfileController implements Initializable {

	@FXML private ImageView back;
	@FXML private Label title;

	/**
	 * Sets the label window title focus traversable. It's used to set the first prompt text field visible.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		title.setFocusTraversable(true);
	}

	/**
	 * Goes to the previous window.
	 * @param e Action on back icon.
	 * @throws IOException .
	 */
	public void back(MouseEvent e) throws IOException {

		if (12 < e.getX() && e.getX()< 76 && 15 < e.getY() && e.getY()< 64) {
			Main.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("ManageSentence.fxml"))));
		}
	}

	/**
	 * Utility method that imports a CSV file.
	 * @throws IOException .
	 */
	private void importCSV() throws IOException {
		Main.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("ManageSentence.fxml"))));
	}

	/**
	 * Imports CSV file and returns to manage sentence screen.
	 * @param e Action on "Import".
	 * @throws IOException .
	 */
	//TODO inviare CSV file
	public void importCSV(ActionEvent e) throws IOException {
		importCSV();
	}

	/**
	 * Opens file explorer and filtrate csv files, when a file has been chosen memorizes its path.
	 * @param e Action on "Search File".
	 * @throws IOException .
	 */
	public void fileChoose(ActionEvent e) throws IOException {
		FileChooser file = new FileChooser();
		Stage primaryStage = null;
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
		file.getExtensionFilters().add(extFilter);
		File selectedFile = file.showOpenDialog(primaryStage); //TODO questo dovrebbe essere il csv con frasi, inviare a server
		cvsSentenceReader(selectedFile.getAbsolutePath());

		Main.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("ManageSentence.fxml"))));
	}

	/**
	 * Allows to press confirm button by pressing ENTER.
	 * @param e the pressed key.
	 * @throws IOException .
	 */
	public void buttonPressed(KeyEvent e) throws IOException {
		if(e.getCode().toString().equals("ENTER")) {
			importCSV();
		}
	}

	/**
	 * Adds sentences in the csv file to database.
	 * @param filePath Path of the csv file.
	 */
	public void cvsSentenceReader(String filePath) {
		try {
			//TODO COLLEGARE CON CSVFILECONTROLLER
			CSVReader reader = new CSVReader(new FileReader(filePath));
			String[] nextLine = new String[2];
			List<Sentence> sentences = new ArrayList<Sentence>();
			while ((nextLine = reader.readNext()) != null) {
				Sentence sentence = new Sentence(nextLine[0].toUpperCase(), nextLine[1].toUpperCase());
				if(sentence.getSentence().length()<60 && sentence.getHint().length()<60){
					sentences.add(sentence);
				}
			}
			Client.getProxy().insertSentence(sentences,Client.getUser());


		} catch (IOException | CsvValidationException e) {
			e.printStackTrace();
		}
	}
}
