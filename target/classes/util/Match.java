package util;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Match is our game state class, that will be passed back and forth and updated between client and server 
 * 
 * @author gruppo aelv
 *
 */

@SuppressWarnings("serial")
public class Match implements Serializable {
	
	private String name;



	private Integer id = null; //TODO 
	private ArrayList<User> players = new ArrayList<User>();	//3
	private HashMap<Integer, Integer> totScores; 	// <id user, punteggio totale della sua partita>
	private Integer manche;
	private HashMap<Integer, ArrayList<Integer>> mancheScore; //3 , 5  <Id user, lista dei punteggi di ciascuna delle 5 manche>
	private User currentPlayingUser; // app. a players 
	private HashMap<Integer, Sentence> sentences; //<manche, frase per quella manche>
	private int nextPlayer = 0;

	
	/**
	 * Constructor
	 */
	public Match() {

	}
	
	/**
	 * constructor
	 * @param name
	 */
	public Match(String name) {
		this.name = name;
	}
	
	public synchronized void addPlayer(User u) {
		if(players.size() < 3)
		players.add(u);
		else {/*TODO Gestisci l'errore*/}
	}

	

	/**
	 * Getter
	 * @return
	 */
	public ArrayList<User> getPlayers() {
		return players;
	}

	/**
	 * Getter
	 * @return
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Setter
	 * @return
	 */
	@Deprecated
	public void setPlayers(ArrayList<User> players) {
		this.players = players;
	}

	/**
	 * Setter
	 * @return
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	
	/**
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 
	 * @return
	 */
	public User getCurrentPlayingUser() {
		return currentPlayingUser;
	}

	/**
	 * 
	 * @param user
	 */
	public void setCurrentPlayingUser(User user) {
		this.currentPlayingUser = user;
	}

	public int getNextPlayer() {
		if(nextPlayer < 2) {
			return nextPlayer++; 
		} else 
			return 0;
	}


}
