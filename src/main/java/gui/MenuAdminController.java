package gui;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import playerRdF.Client;

/**
 * The controller for menuAdmin window
 * 
 * @author Achille Lambrughi
 * @author Emanuele Drago
 * @author Lorenzo Ottaviani
 * @author Elisabeth Veronika Venturino
 *
 */

public class MenuAdminController {

	/**
	 * Sends you to select lobby screen.
	 * @param e Action on "Spectate" button.
	 * @throws IOException .
	 */
	public void spectate(ActionEvent e) throws IOException {		
		Main.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("SelectLobby.fxml"))));
	}

	/**
	 * Sends you to manage sentence screen.
	 * @param e Action on "Manage sentences" button.
	 * @throws IOException .
	 */
	public void manageSentence(ActionEvent e) throws IOException {		
		Main.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("ManageSentence.fxml"))));
	}

	/**
	 * Sends you to statistics screen.
	 * @param e Action on "Statistics" button.
	 * @throws IOException .
	 */
	public void statistics(ActionEvent e) throws IOException {		
		Main.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("Statistics.fxml"))));
	}

	/**
	 * Sends you settings screen.
	 * @param e Action on "Settings" button.
	 * @throws IOException .
	 */
	public void settings(ActionEvent e) throws IOException {		
		Main.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("Settings.fxml"))));
	}
	
	/**
	 * Does the logout.
	 * @param e Action on "Logout" button.
	 * @throws IOException .
	 */
	public void logout(ActionEvent e) throws IOException {
		Client.getProxy().removeMe();
		Main.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("Login.fxml"))));
	}

}
