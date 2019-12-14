package gui;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import playerRdF.Client;
import util.InsertableLobby;
import util.Lobby;
import util.Match;

public class SelectLobbyController implements Initializable{

	@FXML private Node pane;
	private HashMap<Match, Lobby> lobbyList;
	

	@FXML
	TableView<InsertableLobby> tab;

	@FXML
	private TableColumn<InsertableLobby, String> lobbyName;

	@FXML
	private TableColumn<InsertableLobby, Integer>  nPlayers;

	@FXML
	private TableColumn<InsertableLobby, Integer> nSpectators;

	@FXML
	private TableColumn<InsertableLobby, Boolean> status;


	public ObservableList<InsertableLobby> data = FXCollections.observableArrayList();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		//HashMap<Match, Lobby> lobbyList;
		
		
		try {
			//lobbyList = new HashMap<Match, Lobby>(Client.getProxy().demandLobbyList());
			refreshLobbyList();
			
			
			for (Match key : lobbyList.keySet()) { 
				lobbyList.put(key, new InsertableLobby(lobbyList.get(key)));
//				for(Match k : lobbyList.keySet()) {
//					System.out.println(lobbyList.get(k).getLobbyName() + 
//							lobbyList.get(k).getNPlayers() +
//							lobbyList.get(k).getNSpectators() + 
//							lobbyList.get(k).getStatus()
//							);
//				}
			}
			
			lobbyName.setCellValueFactory(new PropertyValueFactory<InsertableLobby, String>("lobbyName"));

			nPlayers.setCellValueFactory(new PropertyValueFactory<InsertableLobby, Integer>("nPlayers"));

			nSpectators.setCellValueFactory(new PropertyValueFactory<InsertableLobby, Integer>("nSpectators"));

			status.setCellValueFactory(new PropertyValueFactory<InsertableLobby, Boolean>("status"));

			for (Match key : lobbyList.keySet()) { 
				data.add((InsertableLobby) lobbyList.get(key)); 
			}
			
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
				
		//inserimenti a caso per verificare il funzionamento del popolamento della  
		//tabella da hashmap
//		lobbyList.put(new Match(), new Lobby("G", 2, 81, true));
//		lobbyList.put(new Match(), new Lobby("Gi", 1, 84, true));
//		lobbyList.put(new Match(), new Lobby("Gio", 3, 38, true));
//		lobbyList.put(new Match(), new Lobby("Gior", 2, 86, true));
//		lobbyList.put(new Match(), new Lobby("Giorg", 1, 18, true));
//		lobbyList.put(new Match(), new Lobby("Giorgi", 3, 58, true));
//		lobbyList.put(new Match(), new Lobby("Giorgio", 3, 84, true));
//		lobbyList.put(new Match(), new Lobby("Giorgion", 2, 68, true));
//		lobbyList.put(new Match(), new Lobby("Giorgione", 1, 78, true));
//		lobbyList.put(new Match(), new Lobby("Giorgiones", 3, 82, true));







		//		tab = new TableView<Lobby>();



			
			
		tab.setItems(data);

		
	}


	public void refresh(ActionEvent e) throws IOException, ClassNotFoundException {
		this.refreshLobbyList();
	}
	public void back(ActionEvent e) throws IOException {
		
		Main.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("Menu.fxml"))));
		
	}
	public void create(ActionEvent e) throws IOException {
		Main.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("CreateLobby.fxml"))));
		
	}
	public void play(ActionEvent e) throws IOException {
		Main.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("WaitingRoom.fxml")))); 
	}


	public void refreshLobbyList() throws ClassNotFoundException, IOException {
		lobbyList = new HashMap<Match, Lobby>(Client.getProxy().demandLobbyList());
		
	}
	
	


}
