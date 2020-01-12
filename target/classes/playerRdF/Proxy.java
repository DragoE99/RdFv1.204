package playerRdF;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;

import serverRdF.ServerListener;
import util.Commands;
import util.Lobby;
import util.Match;
import util.User;


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


	/**
	 * Constructor
	 * @throws IOException 
	 */
	public Proxy() throws IOException {
		super();
		
		addr = InetAddress.getByName(null);
		s = new Socket(addr, ServerListener.getPort());

		out = new ObjectOutputStream(s.getOutputStream());
		in = new ObjectInputStream(s.getInputStream());

	}

	@Override
	public void run() {

		System.out.println("Proxy Partito");

		try {
									//provvisorio

			System.out.println((String)in.readObject());
			//System.out.println((String)in.readObject());

		} catch (IOException | ClassNotFoundException e) {
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
	public Commands sendLoginData(User u) throws IOException {
		//out.writeObject(usr);
		//out.writeObject(psw);
		
		out.writeObject(Commands.LOGIN);
		
		out.writeObject(u);
		
		Commands reply = null;
		try {
			reply = (Commands) in.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return reply;
	}
	
	/**
	 * 
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("unchecked")
	public HashMap<Match, Lobby> demandLobbyList() throws IOException, ClassNotFoundException {
		out.writeObject(Commands.NEEDLOBBYLIST);
		HashMap<Match, Lobby> map = (HashMap<Match, Lobby>) in.readObject(); 
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
		
		
		
		return (Commands)in.readObject();
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
		
		return (Commands)in.readObject();
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
		
		return (Commands)in.readObject();
	}

}
