package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import playerRdF.Client;
import playerRdF.ClientRMI;
import util.Commands;
import util.Lobby;
import util.Match;
import util.User;

public class CreateMatchController implements Initializable {

	@FXML private Node pane;
	
	@FXML private TextField name;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		Stage stage = Main.getStage();
//		stage.setTitle("Create lobby");
		
	}
	
	/**
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * 
	 */
	public void createMatch(ActionEvent e) throws ClassNotFoundException, IOException {
		ClientRMI clientRMI = ClientRMI.getInstance();
		ArrayList<User> me = new ArrayList<>();
		me.add(clientRMI.getUser());
		Match myMatch = new Match(me, name.getText());
		if(!clientRMI.createMatch(myMatch)){
			System.out.println("nome Esistente");
			//TODO l'utente dev essere loggato
		}else {
			System.out.println("match creato");
			Node  source = (Node)  e.getSource();
			Stage stage  = (Stage) source.getScene().getWindow();
			stage.close();
			create();
		}
	}
	
	public void create() throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/gui/WaitingRoom.fxml"));
		root.getStylesheets().add("/resources/PrimaryTheme.css");
		Main.getStage().setScene(new Scene(root));

	}

	public void back(ActionEvent e) throws IOException {
		Node  source = (Node)  e.getSource();
		Stage stage  = (Stage) source.getScene().getWindow();
		stage.close();
		//Main.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("adminRDF/HomePage.fxml"))));
	}
	
	
	

}
