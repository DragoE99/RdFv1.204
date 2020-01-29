package serverRdF;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import gui.ServerStarter;
import javafx.application.Application;
import util.Admin;
import util.Commands;
import util.Lobby;
import util.Match;
import util.Sentence;
import util.User;


/**
 * Server Listener is our main server class;
 * after starting the server and database, it keeps "listening" and accepting connections
 * and handles them
 * 
 * @author Achille Lambrughi
 * @author Emanuele Drago
 * @author Lorenzo Ottaviani
 * @author Elisabeth Veronika Venturino
 *
 */
public class ServerListener {
	private static final int PORT = 8080;
	private static Map<Integer, ServerThread> clients = new HashMap<Integer, ServerThread>();
	private static HashMap<String, Lobby> lobbies = new HashMap<String, Lobby>();
	private static int counter = 0;
	private static Socket socket; 

	private static User admin = new Admin("admin", "admin");
	private static Set<User> users = new HashSet<User>(); //TODO Verificare che questo sia non totalmente inutile
	private static int id = -1;

	private static DataBaseConnection DB;


	public static void main(String args[]) throws IOException {

		Application.launch(ServerStarter.class, args);

		//TODO login e inizializzazione db, aprire la schermata per i dati di accesso al db


		/* ************ roba per la connessione al DB************
		se non presente il database non eseguire*/

		//DB = new DataBaseConnection();


		try {
			//TODO eliminare
			DB.getAllUsers();
		} catch (SQLException e) {
			e.printStackTrace();
		}





		ServerSocket serverSocket = new ServerSocket(PORT);
		System.out.println("Server Started");


		try {
			while(true) {
				socket = serverSocket.accept();
				ServerThread temp = new ServerThread("Thread " + counter);		//launch a new Thread for every connection
				//clients.put(counter, temp);		TODO										//insert into the set to keep track of them
				counter++;


				//				for(int i = 0; i < clients.size(); i++) {
				//					System.out.println(clients.get(i).getName());
				//				}

			}

		}catch(IOException e) {
			System.out.println(Instant.now().toString());
			System.out.println("qualcosa e' andato storto...");
			e.printStackTrace();

		}finally {
			socket.close();
			serverSocket.close();
		}

	}


	/**
	 * Getter for PORT
	 * @return our PORT number
	 */
	public static int getPort() {
		return PORT;
	}

	/**
	 * Getter for socket
	 * @return the socket we are using
	 */
	public static Socket getSocket() {
		return socket;
	}

	/**
	 * Getter for lobbies
	 * @return the lobbies' HashMap
	 */
	public synchronized static HashMap<String, Lobby> getLobbies() {
		//TODO prenderle dal DB

		return lobbies;
	}

	/**
	 * Put a object of type Lobby into HashMap<Lobby> lobbies
	 * @param lobby The lobby to add 
	 */
	public synchronized static void putLobby(Lobby lobby) {
		//TODO metterla anche nel DB? quando la crei nuova
		lobbies.put(lobby.getMatch().getName(), lobby);

		//print di controllo
		System.out.println("Lobbies sul server:");
		for(Map.Entry<String, Lobby> entry : lobbies.entrySet()) {
			System.out.println("Key = " + entry.getKey() + 
					", Creator = " + entry.getValue().getCreator().getNickname());
		}
	}

	/**
	 * Sends to the clients that joined the specified lobby the changes made in the game window
	 * @param lobby The lobby in which the change was made
	 * @param command The command that identifies what change was made
	 * @param senderId The user id of the client that actually made the change
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public static synchronized void UpdateClientsOfThisMatch(Lobby lobby, Commands command, Integer senderId) throws ClassNotFoundException, IOException {

		ArrayList<Integer> threads = lobby.getThreads();

		System.out.println("inizio a printare");
		for (Integer t : threads) {
			System.out.println(t);
		}
		System.out.println("finito di printare");



		ServerThread sThread = clients.get(senderId);
		System.out.println(sThread);
		String update = sThread.getGameTableUpdate();
		for (int i = 0; i < threads.size(); i++) {

			clients.get(threads.get(i)).sendUpdateToProxy(update, command);

		}
	}


	/**
	 * Add a client to the HashMap<Integer, ServerThread> clients
	 * @param id The user id of the client to add
	 * @param thisThread the ServerThread that identifies the client
	 */
	public static synchronized void addClient(Integer id, ServerThread thisThread) {

		clients.put(id, thisThread);
	}


	/**
	 * Replace the client in clients
	 * @param oldKey The old key for client 
	 * @param newKey The new key for client
	 * @return true if the client is not already online
	 */
	public static synchronized boolean replaceClient(Integer oldKey, Integer newKey) {
		//sostituisce l'id del thread con quello del suo user e restituisce true se l'azione va a buon fine(se non e' gia' presente lo user per la nuova chiave)
		ServerThread temp = clients.get(oldKey);
		clients.remove(oldKey);

		return clients.putIfAbsent(newKey, temp) == null;

	}


	/**
	 * 
	 * @return The current temporary available id for the serverThread
	 */
	public static Integer getTempId() {
		return id --;
	}

	/**
	 * Setter for lobbies
	 * @param lobbyList 
	 */
	public static synchronized void setLobbies(HashMap<String, Lobby> lobbyList) {
		lobbies = lobbyList;
	}


	/**
	 * Getter for DB
	 * @return The current instance of the DB
	 */
	public static DataBaseConnection getDB() {
		return DB;
	}

	/**
	 * Update the current player of the specified match
	 * @param match The match 
	 */
	public static synchronized void updateCurrentPlayerOfMatch(Match match) { 		//aggiorna il valore del current player per il match passato come argoemto
		// TODO Auto-generated method stub
		ArrayList<User> players = match.getPlayers();

		Lobby lobby  = lobbies.get(match.getName()); //FIXME

		match.setCurrentPlayingUser(players.get(match.getNextPlayer()));

		System.out.println("stampo l'invocazione di 3 chiamate al metodo getNextPlayer di match(sono in updateCurrentPlayerOfMatch): " + match.getNextPlayer()
		+ match.getNextPlayer() + match.getNextPlayer()	);

		lobby.setMatch(match);

		putLobby(lobby);


		sendNotificationToNextPlayer(match);
	}

	/**
	 * Sends a notification to the next player of the specified match
	 * @param match
	 */
	private static synchronized void sendNotificationToNextPlayer(Match match) {	//MANDA la notifica solo al prossimo giocatore
		ArrayList<User> players = match.getPlayers();								//(che teoricamente il server ha gia' aggiornato)

		System.out.println("giocatore corrente " + match.getCurrentPlayingUser().getNickname());

		for (User user : players) {
			if (user.getId() == match.getCurrentPlayingUser().getId()) {
				System.out.println("mando la play al giocatore " + user.getNickname());
				System.out.println(Instant.now());
				clients.get(user.getId()).sendPlayToClient();
			}
		}
	}

	/**
	 * Inform every ServerThread(so its relative client) connected to the specified lobby that the match has started
	 * @param lobby The specified lobby
	 */
	public static synchronized void startGame(Lobby lobby) { //invia a tutti i thread collegati alla lobby passata come argomento la notifica di inizio gioco

		ArrayList<Sentence> sentences = DB.getMatchSentence(lobby.getMatch().getPlayersId());
		if (sentences.size() < 5) {

		} else {
			Match match = lobby.getMatch();

			match.setSentences(sentences);

			match.setManche(1);

			lobby.setMatch(match);

			lobbies.put(match.getName(), lobby);

			for (Sentence sentence : sentences) {
				System.out.println(sentence.getSentence());
			}


			for (Integer th : lobby.getThreads()) {
				clients.get(th).sendStartNotification();
			}

		}



	}

	/**
	 * Inform every ServerThread(so its relative client) connected to the specified lobby on the value of the current player (so that is visible on screen)
	 * @param lobby
	 */
	public static synchronized void updateCurrentPlayerLabel(Lobby lobby) {

		for (Integer th : lobby.getThreads()) {
			clients.get(th).sendCurrentPlayerLabel();
		}

	}

	/**
	 * Setter
	 * @param db
	 */
	public static void setDB(DataBaseConnection db) {
		DB = db;
	}

	/**
	 * 
	 * @return true if an admin is already registered in the database
	 */
	public static boolean AdminPresent() {
		return DB.checkAdminExistence();
	}
}
