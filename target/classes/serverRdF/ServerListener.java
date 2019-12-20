package serverRdF;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import util.Lobby;
import util.Match;
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
	private static HashMap<Match, Lobby> lobbies = new HashMap<Match, Lobby>();
	private static int counter = 0;
	private static Socket socket;
	
	private static User admin = new User("admin", "admin");


	public static void main(String args[]) throws IOException {


		//TODO login e inizializzazione db 

		/* ************ roba per la connessione al DB************
		se non presente il database non eseguire*/
		DataBaseConnection DB = new DataBaseConnection();
		try {
			DB.getAllUsers();/*
			boolean result =DB.getOneUser("i@i.it", "boia");
			if(result){
				System.out.println("successo ritornato correttamente");
			}else {
				System.out.println("fallimento ERRORE");
			}

			if(DB.thereIsAdmin()){
				System.out.println("admin presente ");
			}else {
				System.out.println("admin assente");
			}*/

			int i =DB.modifyName("kujo");
			System.out.println("colonne modificate: "+i);

			if(DB.checkMail("mail2@.it")){
				System.out.println("mail presente ");
			}else {
				System.out.println("mail assente");
			}


			DB.insertMatch();

		} catch (SQLException e) {
			e.printStackTrace();
		}// */


		ServerSocket serverSocket = new ServerSocket(PORT);
		System.out.println("Server Started");

		//return;

		populateLobbies();																		//TODO
		
		
		try {
			while(true) {
				socket = serverSocket.accept();
				ServerThread temp = new ServerThread("Thread " + counter);		//launch a new Thread for every connection
				clients.put(counter, temp);												//insert into the set to keep track of them
				counter++;


				for(int i = 0; i < clients.size(); i++) {
					System.out.println(clients.get(i).getName());
				}

			}

		}catch (Exception e) {
			System.out.println("qualcosa e' andato storto...");

		}finally {
			socket.close();
			serverSocket.close();
		}

	}
	
	
	public static void populateLobbies() {
		lobbies.put(new Match(), new Lobby("G", 2, 81, true, admin));
		lobbies.put(new Match(), new Lobby("Gi", 1, 84, true, admin));
		lobbies.put(new Match(), new Lobby("Gio", 3, 38, true, admin));
		lobbies.put(new Match(), new Lobby("Gior", 2, 86, true, admin));
		lobbies.put(new Match(), new Lobby("Giorg", 1, 18, true, admin));
		lobbies.put(new Match(), new Lobby("Giorgi", 3, 58, true, admin));
		lobbies.put(new Match(), new Lobby("Giorgio", 3, 84, true, admin));
		lobbies.put(new Match(), new Lobby("Giorgion", 2, 68, true, admin));
		lobbies.put(new Match(), new Lobby("Giorgione", 1, 78, true, admin));
		lobbies.put(new Match(), new Lobby("Giorgiones", 3, 82, true, admin));
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
	public synchronized static HashMap<Match, Lobby> getLobbies() {
		return lobbies;
	}
	
	/**
	 * 
	 * @param newLobby
	 */
	public synchronized static void addLobby(Lobby newLobby) {
		lobbies.put(new Match(), newLobby);
	}
}
