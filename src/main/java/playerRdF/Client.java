package playerRdF;

import java.io.IOException;

import gui.Main;
import javafx.application.Application;
import util.Match;
import util.User;

/**
 * Client is our main client class, it starts the proxy to connect with the server, as well as our GUI
 * 
 * @author gruppo aelv
 *
 */
public class Client {

	private static Proxy proxy;
	private static User me;																	//dovrebbe essere final, oppure non modificabile

	public static void main(String[] args) throws IOException {
		ClientRMI test =ClientRMI.getInstance();
		/*proxy = new Proxy();
		proxy.start();	*/																	//launching the proxy thread

		Application.launch(Main.class, args);												//launching the GUI
		
	}

	/**
	 * Getter for the proxy thread
	 * @return the thread handling server connection
	 */
	public static Proxy getProxy() {
		return proxy;
	}

	/**
	 * 
	 * @return
	 */
	public static Match getMatch() {
		return new Match();
	}
	
	/**
	 * 
	 * @return
	 */
	public static User getUser() {
		return me;
	}
	
	/**
	 * 
	 * @param u
	 */
	public static void setUser(User u) {
		me = u;
	}



}
