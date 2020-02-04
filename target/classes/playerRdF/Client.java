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
 * @author Achille Lambrughi
 * @author Emanuele Drago
 * @author Lorenzo Ottaviani
 * @author Elisabeth Veronika Venturino
 *
 */
public class Client {

	private static Proxy proxy;
	private static User me;																	//dovrebbe essere final, oppure non modificabile
	private static Match myMatch;
	private static boolean spectator = false;

	/**
	 * 
	 * @param args
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws IOException, InterruptedException {
		
		proxy = new Proxy();							
		proxy.start();																		//launching the proxy thread
		gui.Main.setIsAdmin(false);
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
	 * Getter for the match associated to this client
	 * @return
	 */
	public static Match getMatch() {
		return myMatch;
	}
	
	/**
	 * Setter for the match associated to this client
	 * @param match 
	 */
	public static void setMatch(Match match) {
		myMatch = match;		
	}
	
	/**
	 * Getter for the user associated to this client
	 * @return
	 */
	public static User getUser() {
		return me;
	}
	
	/**
	 * Setter for the user associated to this client
	 * @param u
	 */
	public static void setUser(User u) {
		me = u;
	}

	/**
	 * Setter for the spectator field, indicating if a this user is spectating
	 * @param b
	 */
	public static void setSpectator(boolean b) {
		
		spectator  = b;
		
	}

	
	/**
	 * Getter for the spectator field, indicating if a this user is spectating
	 * @return
	 */
	public static boolean isSpectator() {

		return spectator;
	}

	public static void setProxy(Proxy newProxy){
		proxy=newProxy;
	}



}
