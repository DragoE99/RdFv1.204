package playerRdF;

import java.io.IOException;

import gui.Main;
import javafx.application.Application;
import util.Match;
import util.Player;
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
	private static Match myMatch;

	public static void main(String[] args) throws IOException, InterruptedException {
		
		proxy = new Proxy();							
		proxy.start();																		//launching the proxy thread
		gui.Main.setUserType("v");
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
		return myMatch;
	}
	
	/**
	 * 
	 */
	public static void setMatch(Match match) {
		myMatch = match;		
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
