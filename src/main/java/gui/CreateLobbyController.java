package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import playerRdF.Client;
import util.Commands;
import util.Lobby;

/**
 * The controller for createLobby window
 * 
 * @author Achille Lambrughi
 * @author Emanuele Drago
 * @author Lorenzo Ottaviani
 * @author Elisabeth Veronika Venturino
 *
 */
public class CreateLobbyController implements Initializable{

	@FXML private Node pane;
	@FXML private TextField name;
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
	 * Sends to the waiting room screen if the name doesn't already exist.
	 * @param e Action on "Confirm" button.
	 * @throws ClassNotFoundException 
	 * @throws IOException .
	 */
	public void create(ActionEvent e) throws IOException, ClassNotFoundException {

		Commands reply = Client.getProxy().createLobby(new Lobby(name.getText(), false, Client.getUser()));

		if(reply == Commands.OK) {

			//manda alla waiting room
			Main.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("WaitingRoom.fxml"))));

		} else if(reply == Commands.NO){
			//TODO messaggio di errore su GUI
			System.err.println("Nome gia' esistente");
		}
	}

	/**
	 * Goes to the previous window.
	 * @param e Action on back icon.
	 * @throws IOException .
	 */
	public void back(MouseEvent e) throws IOException {

		if (12 < e.getX() && e.getX()< 76 && 15 < e.getY() && e.getY()< 64) {
			Main.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("SelectLobby.fxml"))));
		}
	}




}
