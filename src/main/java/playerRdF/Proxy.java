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
import javafx.application.Platform;
import javafx.scene.control.Label;
import serverRdF.ServerListener;
import util.*;


/**
 * Proxy is our client-side thread that handles the connection with the server
 * 
 * @author gruppo aelv
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





	//TODO multi client e provare con piu' computer


	/**
	 * Constructor
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

				sleep(1000);


				if (inGameWindow) {
					listen();
				}

			}

		} catch (IOException | ClassNotFoundException | InterruptedException e) {
			System.out.println(Instant.now().toString());
			System.out.println("Errore");
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
	 * 
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
				case QUIT : inGameWindow = false;
				break;
				case PLAY : {

					Platform.runLater(new Runnable() {
						@Override public void run() {System.out.println("qua ci entro" + s);
						((GameController)(Main.getLoader().getController())).play();
						}
					});
				}
				default: System.out.println("Non so perche' ma sono qui");
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
	 * 
	 * @param u
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
	 * 
	 * @param user
	 * @param code
	 * @return
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
	 * 
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Lobby> demandLobbyList() throws IOException, ClassNotFoundException {
		out.writeObject(Commands.NEEDLOBBYLIST);
		HashMap<String, Lobby> map = (HashMap<String, Lobby>) in.readObject();
		
		//print di controllo
		System.out.print("Proxy:");
				for(Map.Entry<String, Lobby> entry : map.entrySet()) {
					System.out.println("Key = " + entry.getKey() + 
		                    ", Creator = " + entry.getValue().getCreator().getNickname());
				}

		return map;

	}

	/**
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * 
	 */
	public Commands signup(User u) throws IOException, ClassNotFoundException {

		out.writeObject(Commands.SIGNUP);

		out.writeObject(u);

		Commands c = (Commands)in.readObject();

		return c;
	}

	/**
	 * 
	 * @param email
	 * @return
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
	 * 
	 * @param name
	 * @return
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
	 * 
	 * @param obj
	 * @param command
	 * @throws IOException
	 */
	public void sendGameUpdate(String s, Commands command) throws IOException {

		out.writeObject(command);

		out.writeObject(Client.getUser().getId());
		System.out.println(Client.getUser().getId());  //TODO CANCELLA
		out.writeObject(s);
	}

	/**
	 * 
	 * @throws IOException
	 */
	public void quit() throws IOException {
		out.writeObject(Commands.QUIT);
		
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isInGameWindow() {
		return inGameWindow;
	}


	/**
	 * 
	 * @param inGameWindow
	 */
	public void setInGameWindow(boolean inGameWindow) {
		this.inGameWindow = inGameWindow;
	}

	public void sendChosenMatch(Commands command, Lobby lobby) throws IOException {

		out.writeObject(command);

		out.writeObject(lobby);

	}

	public void resetStream() {
		try {
			in.reset();
			out.reset();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public void endAction() throws IOException {
		// TODO Auto-generated method stub
		out.writeObject(Commands.ENDACTION);
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

}
