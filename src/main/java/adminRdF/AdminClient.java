package adminRdF;

import java.io.IOException;

import gui.Main;
import javafx.application.Application;
import playerRdF.Client;
import playerRdF.Proxy;
import util.Match;
import util.User;

/**
 * Client is our main client class, it starts the proxy to connect with the server, as well as our GUI
 * 
 * @author gruppo aelv
 *
 */
public class AdminClient {

	private static Proxy proxy;
	private static User me;																	//dovrebbe essere final, oppure non modificabile

	public static void main(String[] args) throws IOException {
		proxy = new Proxy();
		proxy.start();
		Client.setProxy(proxy);
		gui.Main.setIsAdmin(true);
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
		Client.setUser(u);
		me = u;
	}



}
