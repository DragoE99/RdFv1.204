package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import playerRdF.Client;
import util.Sentence;

/**
 * The controller for modifySentence window
 * 
 * @author Achille Lambrughi
 * @author Emanuele Drago
 * @author Lorenzo Ottaviani
 * @author Elisabeth Veronika Venturino
 *
 */
public class ModifySentenceController implements Initializable{

	@FXML private ImageView back;
	@FXML private Label title;
	
	@FXML private TextField frase;
	@FXML private TextField hint;
	
	private static Sentence sentence;

	/**
	 * Sets the label window title focus traversable. It's used to set the first prompt text field visible.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		title.setFocusTraversable(true);
		frase.setText(sentence.getSentence());
		hint.setText(sentence.getHint());
		System.out.println("Frase da modificare: " + sentence.getSentence());
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
	 * Utility method that modifies a sentence.
	 * @throws IOException .
	 */
	private void modify() throws IOException {
		//TODO modificare la frase, devo aggiungere sia hint che sentence? mettere caso solo un texfield compilato
		sentence.setSentence(frase.getText());
		sentence.setHint(hint.getText());
		Client.getProxy().modifySentence(sentence);
		Main.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("ManageSentence.fxml"))));
	}
	/**
	 * Modifies sentence and returns to manage sentence.
	 * @param e Action on "Modify" button.
	 * @throws IOException .
	 */
	public void modify(ActionEvent e) throws IOException{

		modify();
	}

	/**
	 * Allows to press confirm button by pressing ENTER.
	 * @param e the pressed key.
	 * @throws IOException .
	 */
	public void buttonPressed(KeyEvent e) throws IOException {
		if(e.getCode().toString().equals("ENTER")) {
			modify();
		}
	}
	
	/**
	 * Setter
	 * @param s
	 */
	public static void setSentence(Sentence s) {
		sentence = s;
	}

}
