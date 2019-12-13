package playerRdF;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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

	//TODO multi client e provare con più computer


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
	public Commands sendLoginData(String usr, String psw) throws IOException {
		//out.writeObject(usr);
		//out.writeObject(psw);
		
		out.writeObject(Commands.LOGIN);
		
		User u = new User(usr, psw);
		out.writeObject(u);
		
		Commands reply = null;
		try {
			reply = (Commands) in.readObject();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return reply;
	}
	
	
	@SuppressWarnings("unchecked")
	public HashMap<Match, Lobby> demandLobbyList() throws IOException, ClassNotFoundException {
		out.writeObject(Commands.NEEDLOBBYLIST);
		HashMap<Match, Lobby> map = (HashMap<Match, Lobby>) in.readObject(); 
		return map;
		
	}

}
