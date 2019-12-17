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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import playerRdF.Client;
import util.Commands;
import util.Lobby;

public class CreateLobbyController implements Initializable {

	@FXML private Node pane;
	
	@FXML private TextField name;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		Stage stage = Main.getStage();
		stage.setTitle("Create lobby");
		
	}
	
	/**
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * 
	 */
	public void createLobby() throws ClassNotFoundException, IOException {
		
		Commands reply = Client.getProxy().createLobby(new Lobby(name.getText(), false, Client.getUser()));
		
		if(reply == Commands.OK) {
			
			//manda alla waiting room
			
		} else if(reply == Commands.NO){
			System.err.println("Nome gi� esistente");
		}
		
	}
	
	public void create(ActionEvent e) throws IOException {

		Main.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("WaitingRoom.fxml"))));
	}

	public void back(ActionEvent e) throws IOException {

		Main.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("SelectLobby.fxml"))));
	}
	
	
	

}