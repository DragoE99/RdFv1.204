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

import util.Admin;
import util.Commands;
import util.Lobby;
import util.Match;
import util.Player;
import util.User;


/**
 * Server Listener is our main server class;
 * after starting the server and database, it keeps "listening" and accepting connections
 * and handles them
 * 
 * @author gruppo aelv
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


		//TODO login e inizializzazione db, aprire la schermata per i dati di accesso al db
		/* ************ roba per la connessione al DB************
		se non presente il database non eseguire*/
		DB = new DataBaseConnection();
		try {
			//TODO eliminare
			DB.getAllUsers();
		} catch (SQLException e) {
			e.printStackTrace();
		}



		ServerSocket serverSocket = new ServerSocket(PORT);
		System.out.println("Server Started");
		
		
		populateLobbies();																		//TODO 
		populateUsers();
		
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
	
	static Match match = new Match();
	
	/**
	 * Populate the HashMap<Lobby> with data taken from DB
	 */
	private static void populateLobbies() {
		match.setName("match"); 
		match.setCurrentPlayingUser(new Player("wewe", "surname", "a", "uaibnvebuiveonv ewiuv", "b", 5)); //TODO ANDRA' TUTTO PRESO DA DB
		/*lobbies.put(new Match(), new Lobby("G", 2, 81, true, admin, new Match()));
		lobbies.put(new Match(), new Lobby("Gi", 1, 84, true, admin, new Match()));
		lobbies.put(new Match(), new Lobby("Gio", 3, 38, true, admin, new Match()));
		lobbies.put(new Match(), new Lobby("Gior", 2, 86, true, admin, new Match()));
		lobbies.put(new Match(), new Lobby("Giorg", 1, 18, true, admin, new Match()));
		lobbies.put(new Match(), new Lobby("Giorgi", 3, 58, true, admin, new Match()));
		lobbies.put(new Match(), new Lobby("Giorgio", 3, 84, true, admin, new Match()));
		lobbies.put(new Match(), new Lobby("Giorgion", 2, 68, true, admin, new Match()));
		lobbies.put(new Match(), new Lobby("Giorgione", 1, 78, true, admin, new Match()));*/
		lobbies.put(match.getName(), new Lobby("Giorgiones", 1, 82, true, admin, match));
	}
	
	/**
	 * Populate the HashSet<User> with data taken from DB
	 */
	private static void populateUsers() {  //TODO Aggancio a DB
		users.add(new Player("Achille", "boh"));
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
		return lobbies;
	}
	
	/**
	 * 
	 * @param lobby
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
	 * 
	 * @param id
	 * @param thisThread
	 */
	public static synchronized void addClient(Integer id, ServerThread thisThread) {
	
		clients.put(id, thisThread);
	}
	
	
	/**
	 * 
	 * @param oldKey
	 * @param newKey
	 * @return
	 */
	public static synchronized boolean replaceClient(Integer oldKey, Integer newKey) {
		//sostituisce l'id del thread con quello del suo user e restituisce true se l'azione va a buon fine(se non e' gia' presente lo user per la nuova chiave)
		ServerThread temp = clients.get(oldKey);
		clients.remove(oldKey);
		
		return clients.putIfAbsent(newKey, temp) == null;
		
	}

	public static Set<User> getUsers() {

		return users;
	}

	/**
	 * 
	 * @return The current temporary available id for the serverThread
	 */
	public static Integer getTempId() {
		return id --;
	}

	public static synchronized void setLobbies(HashMap<String, Lobby> lobbyList) {
		lobbies = lobbyList;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public static DataBaseConnection getDB() {
		return DB;
	}

	public static void updateCurrentPlayerOfMatch(Match match) {
		// TODO Auto-generated method stub
		ArrayList<User> players = match.getPlayers();
		
		lobbies.get(match.getName()).getMatch().setCurrentPlayingUser(players.get(match.getNextPlayer()));;
		
		
		sendNotificationToNextPlayer(match);
	}

	private static void sendNotificationToNextPlayer(Match match) {
		ArrayList<User> players = match.getPlayers();
		for (User user : players) {
			if (user.getId() == match.getCurrentPlayingUser().getId()) {
				clients.get(user.getId()).sendPlayToClient();
			}
		}
		
	}
	
	//TEST TEST TEST AGGIUNGO UN COMMENTO PER VEDERE SE FUNZIONA GIT. LO
}
