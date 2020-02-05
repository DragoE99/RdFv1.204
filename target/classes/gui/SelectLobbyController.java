package gui;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import playerRdF.Client;
import util.Commands;
import util.InsertableLobby;
import util.Lobby;

/**
 * The controller for select lobby window
 * 
 * @author Achille Lambrughi
 * @author Emanuele Drago
 * @author Lorenzo Ottaviani
 * @author Elisabeth Veronika Venturino
 *
 */
public class SelectLobbyController implements Initializable{

	@FXML private ImageView back;
	@FXML private TableView<InsertableLobby> tab;
	@FXML private TableColumn<InsertableLobby, String> lobbyName;
	@FXML private TableColumn<InsertableLobby, Integer>  nPlayers;
	@FXML private TableColumn<InsertableLobby, Integer> nSpectators;
	@FXML private TableColumn<InsertableLobby, Boolean> status;

	private HashMap<String, Lobby> lobbyList;
	public ObservableList<InsertableLobby> data = FXCollections.observableArrayList();
	
	@FXML private Button playButton;
	@FXML private Button createButton;
	@FXML private Button spectateButton;
	
	/**
	 * Initializes the lobby table and populate it.
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		playButton.setDisable(true);
		
		spectateButton.setDisable(true);
		
		spectateButton.disableProperty().bind(Bindings.isEmpty(tab.getSelectionModel().getSelectedItems()));
		
		tab.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

		try {

			lobbyName.setCellValueFactory(new PropertyValueFactory<InsertableLobby, String>("lobbyName"));

			nPlayers.setCellValueFactory(new PropertyValueFactory<InsertableLobby, Integer>("nPlayers"));

			nSpectators.setCellValueFactory(new PropertyValueFactory<InsertableLobby, Integer>("nSpectators"));

			status.setCellValueFactory(new PropertyValueFactory<InsertableLobby, Boolean>("status"));

			refreshLobbyList();

		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}

		tab.setItems(data);
		
		if(Main.getIsAdmin()) {
			playButton.setVisible(false);
			createButton.setVisible(false);
			
		} else {
			playButton.setVisible(true);
			createButton.setVisible(true);
		}

	}

	/**
	 * Refreshes the lobby table.
	 * @param e Action on "Refresh" button.
	 * @throws IOException .
	 * @throws ClassNotFoundException .
	 */
	public void refresh(ActionEvent e) throws IOException, ClassNotFoundException {
		this.refreshLobbyList();
	}

	/**
	 * Goes to the previous window.
	 * @param e Action on back icon.
	 * @throws IOException .
	 */
	public void back(MouseEvent e) throws IOException {
		
		if (12 < e.getX() && e.getX()< 76 && 15 < e.getY() && e.getY()< 64) {
			if(Main.getIsAdmin()) {
				Main.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("MenuAdmin.fxml"))));
			} else {
				Main.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("Menu.fxml"))));				
			}
		}
	}

	/**
	 * Sends to create lobby screen.
	 * @param e Action on "Create lobby" button.
	 * @throws IOException .
	 */
	public void create(ActionEvent e) throws IOException {
		Main.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("CreateLobby.fxml"))));

	}

	/**
	 * Sends to waiting room of the selected lobby.
	 * @param e Action on "Play" button.
	 * @throws IOException .
	 * @throws ClassNotFoundException .
	 */
	public void play(ActionEvent e) throws IOException, ClassNotFoundException {

		InsertableLobby l = tab.getSelectionModel().getSelectedItem();
		HashMap<String, Lobby> lobbies = Client.getProxy().demandLobbyList();
		Lobby lobby = lobbies.get(l.getMatch().getName());
		lobby.setNPlayers(lobby.getNPlayers() + 1);
		Client.getProxy().sendChosenMatch(Commands.CHOSENMATCH, lobby);
		Client.getProxy().setInWaitingRoom(true); //TODO pensare meglio a come gestire questa cosa
		FXMLLoader newLoader = new FXMLLoader(getClass().getResource("WaitingRoom.fxml"));
		Main.getStage().setScene(new Scene(newLoader.load()));
		Main.setLoader(newLoader);
	}
	
	
	public void spectate(ActionEvent e) throws IOException, ClassNotFoundException {

		
		Client.setSpectator(true);
		InsertableLobby l = tab.getSelectionModel().getSelectedItem();
		HashMap<String, Lobby> lobbies = Client.getProxy().demandLobbyList();
		Lobby lobby = lobbies.get(l.getMatch().getName());
		lobby.setNSpectators(lobby.getNSpectators() + 1);
		
		Client.getProxy().sendChosenMatch(Commands.SPECTATE, lobby);
		
		Client.getProxy().setInWaitingRoom(true); //TODO pensare meglio a come gestire questa cosa
		
		FXMLLoader newLoader;
		if(lobby.isActive()) {
			Client.setMatch(lobby.getMatch());
			newLoader = new FXMLLoader(getClass().getResource("Game.fxml"));
		} else {
			newLoader = new FXMLLoader(getClass().getResource("WaitingRoom.fxml"));
		}
		Main.getStage().setScene(new Scene(newLoader.load()));
		Main.setLoader(newLoader);
	}
	
	
	/**
	 * Refreshes lobby list.
	 * @throws ClassNotFoundException .
	 * @throws IOException .
	 */
	public void refreshLobbyList() throws ClassNotFoundException, IOException {
		data.removeAll(data);
		lobbyList = new HashMap<String, Lobby>(Client.getProxy().demandLobbyList());
		//System.out.println(lobbyList.get("match").getNPlayers());
		
		for (String key : lobbyList.keySet()) { 
			lobbyList.put(key, new InsertableLobby(lobbyList.get(key)));
		}
	
		for (String key : lobbyList.keySet()) { 
			data.add((InsertableLobby) lobbyList.get(key)); 
		}
	}
	
	public void checkIfIsPlayable() {
		
		
		try {
			if(tab.getSelectionModel().getSelectedItem().getNPlayers() < 3) {
				playButton.setDisable(false);
			}
		} catch (Exception e) {
		}
		
		
		
	}

}
