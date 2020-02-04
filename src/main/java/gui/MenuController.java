package gui;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import playerRdF.Client;

/**
 * The controller for menu window
 * 
 * @author Achille Lambrughi
 * @author Emanuele Drago
 * @author Lorenzo Ottaviani
 * @author Elisabeth Veronika Venturino
 *
 */
public class MenuController {

	@FXML private Node pane;

	/**
	 * Sends you to selectLobby.
	 * @param e Action on "Play" button.
	 * @throws IOException .
	 */
	public void play(ActionEvent e) throws IOException {
		Main.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("SelectLobby.fxml"))));
	}
	
	/**
	 * Sends you to statistics.
	 * @param e Action on "Statistics" button.
	 * @throws IOException .
	 */
	public void statistics(ActionEvent e) throws IOException {
		Main.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("Statistics.fxml"))));
	}
	
	/**
	 * Sends you to settings.
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
