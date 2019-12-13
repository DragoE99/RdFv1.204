package serverRdF;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import util.Commands;
import util.Lobby;
import util.Match;
import util.User;


/**
 * ServerThread is our thread that handles any single connection with a client
 * 
 * @author gruppo aelv
 *
 */
public class ServerThread extends Thread {

	ServerSocket serverSocket;
	//Socket s;
	ObjectInputStream in;
	ObjectOutputStream out;

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

	@Override
	public void run() {
		try {
			
			out.writeObject("Ciao, sono il server");													//Provvisorio
			//String user = (String)in.readObject();
			//String pwd = (String)in.readObject();
			//System.out.println(user + "\n" + pwd);
			
//magari serve uno switch case per tutti gli input diversi che possono arrivare? Non so bene come fare
			Commands c;
			
			while(true) {
				c = (Commands) in.readObject();
				
				switch (c) {
				case LOGIN : loginHandler();
					break;
				case SIGNUP : //metodo controlla e registra utente	
					break;
				case RESET : //metodo manda nuova mail	
				case NO:
					break;
				case OK:
					break;
				case NEEDLOBBYLIST: this.giveLobbyList();
					break;
				default:
					break;
				}
			}

			//out.writeObject("Done");
			
			//this.listen();

		} catch (IOException | ClassNotFoundException e) {
			System.out.println("erroreServerThread");
			e.printStackTrace();
		}


	}
	
	
	/**
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * 
	 */
	private void loginHandler() throws ClassNotFoundException, IOException {
		User u = (User)in.readObject();
		
		User a = new User("A", "B");		
		
		if(u.equals(a)) {																		//provvisorio, bisogna controllare sul db
			out.writeObject(Commands.OK);
		} else {
			out.writeObject(Commands.NO);
		}
	}

	private void listen() throws ClassNotFoundException, IOException {
		if (in.readObject().equals("Need lobby list"))
			this.giveLobbyList();
	}

	private void giveLobbyList() throws IOException {
		out.writeObject(ServerListener.getLobbies()); 
		
	}

}
