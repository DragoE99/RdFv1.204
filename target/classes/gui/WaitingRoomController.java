package gui;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import playerRdF.Client;

/**
 * The controller for waiting room window
 * 
 * @author Achille Lambrughi
 * @author Emanuele Drago
 * @author Lorenzo Ottaviani
 * @author Elisabeth Veronika Venturino
 *
 */
public class WaitingRoomController {

	@FXML private ImageView back;

	/**
	 * Goes to the previous window.
	 * @param e Action on back icon.
	 * @throws IOException .
	 */
	public void back(MouseEvent e) throws IOException {

		if (12 < e.getX() && e.getX()< 76 && 15 < e.getY() && e.getY()< 64) {
			Client.getProxy().setInWaitingRoom(false);
			Main.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("SelectLobby.fxml"))));
		}
	}
	
	/**
	 * If there are enough players, sends to game screen.
	 * @throws IOException .
	 */
	public void begin() throws IOException {	
		FXMLLoader newLoader = new FXMLLoader(getClass().getResource("Game.fxml"));
		Main.getStage().setScene(new Scene(newLoader.load()));
		Main.setLoader(newLoader);	
	}

}
