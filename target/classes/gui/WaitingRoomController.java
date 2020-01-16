package gui;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import playerRdF.ClientRMI;
import serverRdF.RemoteGameObserverInterface;
import util.Match;
import util.StringManager;

public class WaitingRoomController implements RemoteGameObserverInterface, Serializable {
	@FXML
	public Label numberOfPlayer;


	private static final long serialVersionUID = 1L;

	public void initialize(){

		//numberOfPlayer.setText("Number of player: "+ ClientRMI.getInstance().getMatch().getPlayers().size());
		ClientRMI.getInstance().getMatch().addObserver(this);

		//quitLabel.setOnMouseClicked(this::handleMouseClick);
	}

	private void handleMouseClick(MouseEvent mouseEvent) {
		try {
			Quit();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void begin() throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/adminRDF/GameGui.fxml"));
		root.getStylesheets().add("/resources/PrimaryTheme.css");
		Main.getStage().setScene(new Scene(root));
	}

	public void Quit() throws IOException {
		ClientRMI.getInstance().removeObserver();
		Parent root = FXMLLoader.load(getClass().getResource("/adminRDF/HomePage.fxml"));
		root.getStylesheets().add("/resources/PrimaryTheme.css");
		Main.getStage().setScene(new Scene(root));
	}


	private void setLabelText(){
		numberOfPlayer.setText("Number of player: "+ ClientRMI.getInstance().getMatch().getPlayers().size());

	}
	@Override
	public void update(Object observable, Object updateMsg) throws RemoteException {
		System.out.println("ricevuto updadte in waitingroom");
		Platform.runLater(
				() -> {
					// Update UI here.
					setLabelText();
					if(ClientRMI.getInstance().getMatch().getState().equals(
							StringManager.getString("match_state_running_convention"))) {
						try {
							begin();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
		);


	}
}
