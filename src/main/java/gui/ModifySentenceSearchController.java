package gui;

import java.io.IOException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import playerRdF.Client;
import util.Sentence;

/**
 * The controller for sentenceSearch window
 * 
 * @author Achille Lambrughi
 * @author Emanuele Drago
 * @author Lorenzo Ottaviani
 * @author Elisabeth Veronika Venturino
 *
 */
public class ModifySentenceSearchController {

	@FXML private ImageView back;
	@FXML private Label title;
	
	@FXML private ListView<String> list;
	
	private ArrayList<Sentence> frasi;

	/**
	 * Sets the label window title focus traversable. It's used to set the first prompt text field visible.
	 */
	public void initialize() {
		title.setFocusTraversable(true);
		
		ArrayList<String> sentences = new ArrayList<>();
		
		try {
			frasi = Client.getProxy().getAllSentences();
			
			for (Sentence x : frasi) {
				sentences.add(x.getSentence());
			}
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		list.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		
		ObservableList<String> ob = FXCollections.observableArrayList(sentences);
		
		list.setItems(ob);
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
	 * 
	 * @throws IOException
	 */
	private void search() throws IOException {
		//TODO deve cercare e salvare la frase da modificare da qualche parte
		
		String selected = list.getSelectionModel().getSelectedItem();
		Sentence choice = null;
		for (Sentence sentence : frasi) {
			if(sentence.getSentence().equals(selected))
				choice = sentence;
		}
		
		if(choice != null) {
			ModifySentenceController.setSentence(choice);
			
			Main.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("ModifySentence.fxml"))));
		}
		
	}
	/**
	 * Sends to modify sentence window and memorizes the sentence searched.
	 * @param e Action on "search" button.
	 * @throws IOException .
	 */
	public void search(ActionEvent e) throws IOException {
		search();
	}
	/**
	 * Allows to press confirm button by pressing ENTER.
	 * @param e the pressed key.
	 * @throws IOException .
	 */
	public void buttonPressed(KeyEvent e) throws IOException {
		if(e.getCode().toString().equals("ENTER")) {
			search();
		}
	}

}
