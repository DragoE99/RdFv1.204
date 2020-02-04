package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import playerRdF.Client;
import util.Sentence;

/**
 * The controller for createSentence window
 * 
 * @author Achille Lambrughi
 * @author Emanuele Drago
 * @author Lorenzo Ottaviani
 * @author Elisabeth Veronika Venturino
 *
 */
public class CreateSentenceController implements Initializable{

	@FXML private Label title;
	@FXML private TextField sentence;
	@FXML private TextField hint;
	/**
	 * Sets the label window title focus traversable. It's used to set the first prompt text field visible.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		title.setFocusTraversable(true);
	}

	/**
	 * Utility method that creates a sentence.
	 * @throws IOException .
	 */
	private void create() throws IOException {
		Main.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("ManageSentence.fxml"))));
	}
	/**
	 * Creates a new sentence with correlated hint, than returns to manage sentence screen.
	 * @param e Action on "Create" button.
	 * @throws IOException .
	 */
	public void create(ActionEvent e) throws IOException {
		Sentence newSentence = new Sentence(sentence.getText().toUpperCase(), hint.getText().toUpperCase());
		if (newSentence.getSentence().length() < 60 && newSentence.getHint().length() < 60) {
			List<Sentence> sentences = new ArrayList<Sentence>();
			sentences.add(newSentence);
			Client.getProxy().insertSentence(sentences,Client.getUser());
			create();
		} else {
			System.err.println("Frase o suggerimento troppo lunghi");
		}
		
	}

	/**
	 * Allows to press confirm button by pressing ENTER.
	 * @param e the pressed key.
	 * @throws IOException .
	 */
	public void buttonPressed(KeyEvent e) throws IOException {
		if(e.getCode().toString().equals("ENTER")) {
			create();
		}
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
}
