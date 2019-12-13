package playerRdF;

import java.io.IOException;
import java.util.Set;

import gui.Main;
import javafx.application.Application;
import util.Match;

/**
 * Client is our main client class, it starts the proxy to connect with the server, as well as our GUI
 * 
 * @author gruppo aelv
 *
 */
public class Client {

	private static Proxy proxy;

	public static void main(String[] args) throws IOException {
		proxy = new Proxy();							
		proxy.start();																		//launching the proxy thread

		Application.launch(Main.class, args);												//launching the GUI
		
	}

	/**
	 * Getter for the proxy thread
	 * @return the thread handling server connection
	 */
	public static Proxy getProxy() {
		return proxy;
	}

	public static Match getMatch() {
		return new Match();
	}




}
