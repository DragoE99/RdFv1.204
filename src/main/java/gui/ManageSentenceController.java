package gui;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * The controller for manageSentence window
 * 
 * @author Achille Lambrughi
 * @author Emanuele Drago
 * @author Lorenzo Ottaviani
 * @author Elisabeth Veronika Venturino
 *
 */
public class ManageSentenceController {

	@FXML private ImageView back;

	/**
	 * Goes to the previous window.
	 * @param e Action on back icon.
	 * @throws IOException .
	 */
	public void back(MouseEvent e) throws IOException {

		if (12 < e.getX() && e.getX()< 76 && 15 < e.getY() && e.getY()< 64) {
			Main.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("MenuAdmin.fxml"))));
		}
	}

	/**
	 * Sends in createSentence screen.
	 * @param e Action on "Create" button.
	 * @throws IOException .
	 */
	public void create(ActionEvent e) throws IOException {
		Main.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("CreateSentence.fxml"))));
	}

	/**
	 * Sends in modifySentence.
	 * @param e Action on "Modify" button.
	 * @throws IOException .
	 */
	public void modify(ActionEvent e) throws IOException {
		Main.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("ModifySentenceSearch.fxml"))));
	}

	/**
	 * Sends in CSVfile.
	 * @param e Action on "Import CSV" button.
	 * @throws IOException .
	 */
	public void importCSV(ActionEvent e) throws IOException {
		Main.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("CSVfile.fxml"))));
	}
}
