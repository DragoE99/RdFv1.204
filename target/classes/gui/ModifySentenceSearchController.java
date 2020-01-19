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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * The controller for sentenceSearch window
 * 
 * @author Achille Lambrughi
 * @author Emanuele Drago
 * @author Lorenzo Ottaviani
 * @author Elisabeth Veronika Venturino
 *
 */
public class ModifySentenceSearchController implements Initializable{

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
	 * Sends to modify sentence window and memorizes the sentence searched.
	 * @param e Action on "search" button.
	 * @throws IOException .
	 */
	public void search(ActionEvent e) throws IOException {
		//TODO deve cercare e salvare la frase da modificare da qualche parte
		Main.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("ModifySentence.fxml"))));
	}

}
