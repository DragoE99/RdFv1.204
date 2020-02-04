package gui;

import java.io.IOException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import playerRdF.Client;
import util.Sentence;

public class DeleteSentenceController {
	private ArrayList<Sentence> frasi;
	@FXML private ListView<String> list;

	/**
	 * Displays list of sentences.
	 */
	public void initialize() {

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
	 * Deletes the selected sentence.
	 * @throws IOException .
	 */
	private void delete() throws IOException {
		//TODO cancella la frase da DB
		
		Sentence s = frasi.get(list.getSelectionModel().getSelectedIndex());
		
		Client.getProxy().deleteSentence(s);
		
		Main.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("ManageSentence.fxml"))));
	}

	/**
	 * Sends to modify sentence window and memorizes the sentence searched.
	 * @param e Action on "delete" button.
	 * @throws IOException .
	 */
	public void delete(ActionEvent e) throws IOException {
		delete();
	}
	/**
	 * Allows to press confirm button by pressing ENTER.
	 * @param e the pressed key.
	 * @throws IOException .
	 */
	public void buttonPressed(KeyEvent e) throws IOException {
		if(e.getCode().toString().equals("ENTER")) {
			delete();
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
