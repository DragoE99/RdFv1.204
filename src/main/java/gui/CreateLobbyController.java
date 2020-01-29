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
import javafx.scene.input.KeyEvent;
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

	@FXML private TextField name;
	@FXML private ImageView back;
	@FXML private Label title;
	@FXML private Label errMsg;

	/**
	 * Sets the label window title focus traversable. It's used to set the first prompt text field visible.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		title.setFocusTraversable(true);
	}

	/**
	 * Utility method that creates a lobby.
	 * @throws IOException .
	 * @throws ClassNotFoundException .
	 */
	private void create() throws ClassNotFoundException, IOException {
		
		Lobby newLobby = new Lobby(name.getText(), false, Client.getUser());
		Commands reply = Client.getProxy().createLobby(newLobby);

		if(reply == Commands.OK) {

			Client.getProxy().sendChosenMatch(Commands.CHOSENMATCH, newLobby);
			Client.getProxy().setInWaitingRoom(true);
			//manda alla waiting room
			FXMLLoader newLoader = new FXMLLoader(getClass().getResource("WaitingRoom.fxml"));
			Main.getStage().setScene(new Scene(newLoader.load()));
			Main.setLoader(newLoader);

		} else if(reply == Commands.NO){
			errMsg.setText("Nome gia' esistente");
		}
	}
	/**
	 * Sends to the waiting room screen if the name doesn't already exist.
	 * @param e Action on "Confirm" button.
	 * @throws ClassNotFoundException .
	 * @throws IOException .
	 */
	public void create(ActionEvent e) throws IOException, ClassNotFoundException {
		create();
	}

	/**
	 * Allows to press confirm button by pressing ENTER.
	 * @param e the pressed key.
	 * @throws ClassNotFoundException . 
	 * @throws IOException .
	 */
	public void buttonPressed(KeyEvent e) throws IOException, ClassNotFoundException {
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
			Main.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("SelectLobby.fxml"))));
		}
	}




}
