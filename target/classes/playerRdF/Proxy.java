package playerRdF;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import gui.GameController;
import gui.Main;
import gui.WaitingRoomController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import util.*;


/**
 * Proxy is our client-side thread that handles the connection with the server
 * 
 * @author Achille Lambrughi
 * @author Emanuele Drago
 * @author Lorenzo Ottaviani
 * @author Elisabeth Veronika Venturino
 *
 */
public class Proxy extends Thread {									//cambiare nome?

	Socket s;
	ObjectInputStream in;
	ObjectOutputStream out;
	InetAddress addr;
	private int id = 0;

	/**
	 * general logger for the client
	 */
	private static final Logger LOGGER = Logger.getLogger("playerRdF");


	private boolean isRunning;
	
	private boolean inGameWindow = false;
	
	private boolean inWaitingRoom = false;





	//TODO multi client e provare con piu' computer


	/**
	 * Create a new instance of Proxy, which is a thread that manage the connection with the server on the Client side
	 * @throws IOException 
	 */
	public Proxy() throws IOException {
		super();

		addr = InetAddress.getByName(null);
		s = new Socket(addr, 8080);

		out = new ObjectOutputStream(s.getOutputStream());
		in = new ObjectInputStream(s.getInputStream());

	}

	
	@Override
	public void run() {

		System.out.println("Proxy Partito");

		try {

			System.out.println((String)in.readObject()); //provvisorio
			//System.out.println((String)in.readObject());
			isRunning = true;

			while (isRunning) {
				
				
				if(inWaitingRoom) {
					startGame();
				}
				
				if (inGameWindow) {
					System.out.println("ingamewindow " + Instant.now());
					listen();
				}
				
				sleep(100);

			}

		} catch (IOException | ClassNotFoundException | InterruptedException e) {
			System.out.println(Instant.now().toString());
			System.out.println("Errore");
			e.printStackTrace();
		}



	}
	
	/**
	 * Check if the server send the start notification and then opens the Game window
	 */
	private void startGame() {
		 try {
			if((Commands)in.readObject() == Commands.START) {
				
				inWaitingRoom = false;
				
				Client.setMatch((Match) in.readObject());
				
				Platform.runLater(new Runnable() {
					@Override public void run() {System.out.println("inizio partita");
					try {
						((WaitingRoomController)(Main.getLoader().getController())).begin();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					}
				});
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			
		}

	/**
	 * Method that sends user data to be checked by the server at the moment of authentication
	 * @param usr String, the username/mail
	 * @param psw String, password
	 * @return 
	 * @throws IOException
	 * @throws ClassNotFoundException 
	 */
	public synchronized Commands sendLoginData(User u) throws IOException {
		//out.writeObject(usr);
		//out.writeObject(psw);
		
		out.writeObject(Commands.LOGIN);
		
		out.writeObject(u);
		
		Commands reply = null;
		try {
			reply = (Commands) in.readObject();
			
			if(reply == Commands.OK) {
				//setto l'utente della sessione uguale all'utente sul DB, serve per l'ID
				Client.setUser((User)in.readObject());
				
				//se l'utente non e' verificato mi torna nullo
				if(Client.getUser() == null) {
					reply = Commands.NOTVERIFIED;
				}
			}
			
			
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return reply;
	}



	/**
	 * Loop that listen commands and choose the right method for a specific command
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	private synchronized void listen() throws ClassNotFoundException, IOException {
		Commands c; 
		int numberOfErrors = 0;
		while(inGameWindow) {
			try {

				c = (Commands) in.readObject();
				System.out.println("while");
				switch (c) {
				case UPDATEGAMETABLE: {
					String s = (String)in.readObject();

					Platform.runLater(new Runnable() {
						@Override public void run() {System.out.println("qua ci entro" + s);
						((GameController)(Main.getLoader().getController())).setLabels(s);
						}
					});
				}
				break;

				case UPDATEGAMETEXT: {
					String s = (String)in.readObject();

					Platform.runLater(new Runnable() {
						@Override public void run() {System.out.println("qua ci entro" + s);
						((GameController)(Main.getLoader().getController())).setInsertConsonant(s);
						}
					});
				}
				break;

				case UPDATEGAMEWHEEL: {
					String s = (String)in.readObject();

					Platform.runLater(new Runnable() {
						@Override public void run() {System.out.println("qua ci entro" + s);
						((GameController)(Main.getLoader().getController())).setRandVal(s);
						}
					});
				}
				break;
				case QUIT : {inGameWindow = false; inWaitingRoom = false;}
				break;
				case PLAY : {

					Platform.runLater(new Runnable() {
						@Override public void run() {System.out.println("proxy riceve comando play");
						((GameController)(Main.getLoader().getController())).play();
						}
					});
				}
				break;
				case CURRENTPLAYERLABEL : {
					String s = (String)in.readObject();

					Platform.runLater(new Runnable() {
						@Override public void run() {System.out.println("proxy riceve comando CURRENTPLAYERLABEL");
						((GameController)(Main.getLoader().getController())).setCurrentPlayer(s);
						}
					});
				}
				break;
				case MANCHEWON: {
					String s = (String)in.readObject();
					Platform.runLater(new Runnable() {
						@Override public void run() {System.out.println("qua ci entro" + s);
						((GameController)(Main.getLoader().getController())).setHint(s);
						((GameController)(Main.getLoader().getController())).setSentenceVisible();
						
						}
					});
				}
				break;
				case TIMER : {
					String s = (String)in.readObject();

					Platform.runLater(new Runnable() {
						@Override public void run() {System.out.println("proxy riceve comando timer");
						((GameController)(Main.getLoader().getController())).setTimer(s);
						}
					});
				}
				break;
				case TIMEOUT : {
					Platform.runLater(new Runnable() {
						@Override public void run() {System.out.println("proxy riceve comando timeout");
						((GameController)(Main.getLoader().getController())).disable();
						}
					});
				}
				break;
				case NEWMANCHE : {
					int newManche = (int)in.readObject();
					Client.getMatch().setManche(newManche);
					Platform.runLater(new Runnable() {	
						@Override public void run() {System.out.println("proxy riceve comando NEWMANCHE");
						((GameController)(Main.getLoader().getController())).resetScreen();
						}
					});
				}
				break;
				case ENDMATCH : {
					String s = (String)in.readObject();
					Platform.runLater(new Runnable() {	
						@Override public void run() {System.out.println("proxy riceve comando endmatch");
						((GameController)(Main.getLoader().getController())).setHint("Partita vinta da " + s + "!");
						}
					});
				}
				break;
				case EXITMATCH : {
					setInGameWindow(false);
					Platform.runLater(new Runnable() {	
						@Override public void run() {System.out.println("proxy riceve comando EXITmatch");
						((GameController)(Main.getLoader().getController())).exitMatch();
						}
					});
				}
				break;
				default: 
				break;
				}
			} catch (Exception e) {
				//if we can't receive for a few times we stop the thread
				numberOfErrors++;
				LOGGER.log(Level.WARNING, "Error while receiving", e);
				if(numberOfErrors>3)
					isRunning = false;
			}
		}											
	}

	
	
	/**
	 * Sends updates of user's data to the server
	 * @param u The actual value for the user to modify
	 * @throws IOException
	 */
	public void updateUser(User u) throws IOException {
		
		out.writeObject(Commands.MODIFYUSER);
		
		out.reset();
		out.writeObject(u);
		
		//non so se serve una conferma
		
		System.out.println("Proxy: " + u.getEmail() + u.getName() + u.getId());
	}

	
	
	/**
	 * Sends the code to the server to check if is right
	 * @param user The user that has inserted the code
	 * @param code The String inserted by user
	 * @return The commands returned by the server (OK if the code is right, NO if the code is wrong)
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public Commands activationCode(User user, String code) throws IOException, ClassNotFoundException {
		
		out.writeObject(Commands.ACTIVATIONCODE);
		
		out.reset();
		out.writeObject(user);
		out.writeObject(code);
		
		return (Commands)in.readObject();
	}
 
	
	/**
	 * Ask to the server the updated lobby hashmap 
	 * @return The lobby hashmap
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Lobby> demandLobbyList() throws IOException, ClassNotFoundException {
		out.writeObject(Commands.NEEDLOBBYLIST);
		Object obj = in.readObject();
		HashMap<String, Lobby> map = new HashMap<String, Lobby>();
		try {
		map = (HashMap<String, Lobby>) obj;
		} catch(ClassCastException e) {
			System.out.println(obj.toString());
		}
		
		//print di controllo
		System.out.print("Proxy:");
				for(Map.Entry<String, Lobby> entry : map.entrySet()) {
					System.out.println("Key = " + entry.getKey() + 
		                    ", Creator = " + entry.getValue().getCreator().getNickname());
				}

		return map;

	}

	/**
	 * Sends a sign up request to the server
	 * @param u The user that needs to be signed up
	 * @return The reply from the server
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public Commands signup(User u) throws IOException, ClassNotFoundException {

		out.writeObject(Commands.SIGNUP);

		out.writeObject(u);

		Commands c = (Commands)in.readObject();

		return c;
	}

	/**
	 * Sends to the server a request of password reset
	 * @param email The String that represent the email address  
	 * @return The reply from the server
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public Commands resetPwd(String email) throws IOException, ClassNotFoundException {

		out.writeObject(Commands.RESET);

		out.writeObject(email);

		Commands c = (Commands)in.readObject();

		return c;
	}

	/**
	 * Sends to the server the request to create a new lobby
	 * @param newLobby The lobby that is going to be created
 	 * @return The reply from the server
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public Commands createLobby(Lobby newLobby) throws IOException, ClassNotFoundException {

		out.writeObject(Commands.CREATELOBBY);

		out.writeObject(newLobby);

		Commands c = (Commands)in.readObject();

		return c;
	}


	/**
	 * Send to the server the change that was made in this client window
	 * @param s The actual change as string
	 * @param command The command that identifies the element of the game window that has changed
	 * @throws IOException
	 */
	public void sendGameUpdate(String s, Commands command) throws IOException {

		out.writeObject(command);

		out.writeObject(Client.getUser().getId());
		System.out.println(Client.getUser().getId());  //TODO CANCELLA
		out.writeObject(s);
	}

	/**
	 * Sends to the server a request to exit from the current match
	 * @throws IOException
	 */
	public void quit() throws IOException {
		
		out.reset();
		
		out.writeObject(Commands.QUIT);
		
	}
	
	/**
	 * Request to the server the statistics for the user specified
	 * @param id The user id 
	 * @return The statistics for the specified user id
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public ArrayList<String> userStats(Integer id) throws IOException, ClassNotFoundException {
		//TODO 
		out.writeObject(Commands.USTATS);
		out.writeObject(id);
		
		ArrayList<String> stats = (ArrayList<String>) in.readObject();
		return stats;
	}
	
	
	/**
	 * Request to the server the global statistics
	 * @return The global statistics
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public ArrayList<String> globalStats() throws IOException, ClassNotFoundException {
		//TODO 
		out.writeObject(Commands.GSTATS);
		
		ArrayList<String> stats = (ArrayList<String>) in.readObject();
		return stats;
	}
	
	/**
	 * Getter for inGameWindow
	 * @return if the client is in game window or not
	 */
	public boolean isInGameWindow() {
		return inGameWindow;
	}


	/**
	 * Setter for inGameWindow
	 * @param inGameWindow The new value for inGameWindow
	 */
	public void setInGameWindow(boolean inGameWindow) {
		this.inGameWindow = inGameWindow;
	}

	
	/**
	 * Sends to the server the match chosen by the client
	 * @param command The command to inform the server
	 * @param lobby The chosen lobby
	 * @throws IOException
	 */
	public void sendChosenMatch(Commands command, Lobby lobby) throws IOException {

		out.writeObject(command);

		out.writeObject(lobby);

	}

	
	/**
	 * Inform the server that the client has finished his action
	 * @throws IOException
	 */
	public void endTurn() throws IOException {
		// TODO Auto-generated method stub
		out.writeObject(Commands.ENDTURN);
	}
	
	/**
	 * Getter for isRunning
	 * @return The value of isRunning
	 */
	public boolean isRunning() {
		return isRunning;
	}

	/**
	 * Setter for isRunning
	 * @param isRunning the new value for isRunning
	 */
	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

	
	/**
	 * Getter for inWaitingRoom
	 * @return The value of inWaitingRoom
	 */
	public boolean isInWaitingRoom() {
		return inWaitingRoom;
	}

	/**
	 * Setter for inWaitingRoom
	 * @param inWaitingRoom the new value for inWaitingRoom
	 */
	public void setInWaitingRoom(boolean inWaitingRoom) {
		this.inWaitingRoom = inWaitingRoom;
	}

	public void insertSentence(List<Sentence> sentences, User u) throws IOException {
		out.writeObject(Commands.INSERTSENTENCES);
		out.writeObject(sentences);
		out.writeObject(u);

	}
	public ArrayList<Sentence> getAllSentences() throws IOException, ClassNotFoundException {
		out.writeObject(Commands.GETALLSENTENCES);
		return (ArrayList<Sentence>)in.readObject();
	}

	public void modifySentence(Sentence sentence){
		try {
			out.writeObject(Commands.MODIFYSENTENCE);
			out.writeObject(sentence);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void deleteSentence(Sentence sentence){
		try {
			out.writeObject(Commands.DELETESENTENCE);
			out.writeObject(sentence);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public void removeMe() {

		try {
			out.writeObject(Commands.REMOVEME);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public void buttonPressedNotification(Commands command) {

		try {
			out.writeObject(command);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
			try {
				out.reset();
				in.reset();
			} catch (IOException e) {
				System.out.println("Non e' stato possibile fare il reset dello stream, Proxy:599");
			}
			
		
		
	}


	public void activateJolly() {
		
		try {
			out.writeObject(Commands.ACTIVATEJOLLY);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}


	public void sendDeposit(int deposit) {
		
		try {
			out.writeObject(Commands.DEPOSIT);
			out.writeObject(deposit);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
