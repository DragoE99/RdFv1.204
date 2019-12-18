package serverRdF;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import util.Commands;
import util.Lobby;
import util.User;


/**
 * ServerThread is our thread that handles any single connection with a client
 * 
 * @author gruppo aelv
 *
 */
public class ServerThread extends Thread{

	//private ServerSocket serverSocket;
	//Socket s;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private User test = new User("A", "B");

	/**
	 * Constructor
	 * 
	 * @param name, String representing the name we want to assign to this thread
	 * @throws IOException 
	 */

	public ServerThread(String name) throws IOException {
		super(name);
		//this.s = socket;
		in = new ObjectInputStream(ServerListener.getSocket().getInputStream());
		out = new ObjectOutputStream(ServerListener.getSocket().getOutputStream());
		start();
	}

	/* ********** testing*************************/

	@Override
	public void run() {
		try {

			
			out.writeObject("Ciao, sono il server");													//Provvisorio
			
			listen();

			//loop

			//out.writeObject("Done");
			
			//this.listen();



		} catch (IOException | ClassNotFoundException e) {
			System.out.println("erroreServerThread");
			e.printStackTrace();
		}


	}
	
	
	/**
	 * 
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	private void listen() throws ClassNotFoundException, IOException {
		Commands c;
		
		while(true) {
			c = (Commands) in.readObject();
			
			switch (c) {
			case LOGIN : loginHandler();
				break;
			case SIGNUP : signup();
				break;
			case RESET : resetPwd();	
			case NO:
				break;
			case OK:
				break;
			case NEEDLOBBYLIST: giveLobbyList();
				break;
			case CREATELOBBY : createLobby();
				break;
			default:
				break;
			}
		}											
	}
	
	
	/**
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * 
	 */
	private void loginHandler() throws ClassNotFoundException, IOException {
		User u = (User)in.readObject();		
		
		if(u.equals(test)) {																		//TODO provvisorio, bisogna controllare sul db
			out.writeObject(Commands.OK);
		} else {
			out.writeObject(Commands.NO);
		}
	}

	

	/**
	 * 
	 * @throws IOException
	 */
	private void giveLobbyList() throws IOException {
		out.writeObject(ServerListener.getLobbies()); 
		
	}
	
	/**
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * 
	 */
	private void signup() throws ClassNotFoundException, IOException {
		User u = (User)in.readObject();
		
		if(u.equals(test)) {																		//TODO, provvisorio, bisogna controllare sul db
			out.writeObject(Commands.NO);
		} else {
			out.writeObject(Commands.OK);
			
			
			//TODO inserirlo sul db
		}
	}
	
	/**
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * 
	 */
	private void resetPwd() throws ClassNotFoundException, IOException {
		String email = (String)in.readObject();
		
		if(!email.equals(test.getEmail())) {						//TODO controlla sul db se esiste l'utente
			
			out.writeObject(Commands.NO);
		} else {
			out.writeObject(Commands.OK);
			
			//manda la mail
		}
	}
	
	private void createLobby() throws IOException, ClassNotFoundException {
		Lobby newLobby = (Lobby)in.readObject();
		
		if(newLobby.getLobbyName().equals("nome esistente")) {						//TODO controlla sul db se esiste il nome della lobby
			out.writeObject(Commands.NO);
		} else {
			out.writeObject(Commands.OK);
			
			System.out.println(newLobby.getCreator().getEmail());
			
			ServerListener.addLobby(newLobby);
		}
	}

}
