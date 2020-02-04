package util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import org.omg.CORBA.INITIALIZE;

/**
 * Match is our game state class, that will be passed back and forth and updated between client and server 
 * 
 * @author Achille Lambrughi
 * @author Emanuele Drago
 * @author Lorenzo Ottaviani
 * @author Elisabeth Veronika Venturino
 *
 */

@SuppressWarnings("serial")
public class Match implements Serializable {
	
	private String name;



	private Integer id = null; //TODO 
	private ArrayList<User> players = new ArrayList<User>();	//3
	private ArrayList<User> spectators = new ArrayList<User>();
	private HashMap<Integer, Integer> totScores = new HashMap<Integer, Integer>(); 	// <id user, punteggio totale della sua partita>
	private Integer manche;
	private HashMap<Integer, ArrayList<Integer>> mancheScore; //3 , 5  <Id user, lista dei punteggi di ciascuna delle 5 manche>
	private User currentPlayingUser; // app. a players 
	private HashMap<Integer, Sentence> sentences = new HashMap<Integer, Sentence>(); //<manche, frase per quella manche>
	private static int nextPlayer = 0;

	
	/**
	 * Default Constructor
	 */
	public Match() {
		initializeScores();
	}
	

	/**
	 * constructor
	 * @param name
	 */
	public Match(String name) {
		this.name = name;
	}
	
	/**
	 * Constructor
	 * @param name
	 * @param id
	 * @param players
	 */
	public Match(String name, Integer id, Integer[] players) {
		this.name = name;
		this.id = id;
		for (int i=0; i<players.length; i++)  {
			this.players.add(new Player(players[i]));
		}
	}
	
	/**
	 * 
	 * @param matchId Id of this match
	 * @param matchName Name of this match
	 */
	public Match(Integer matchId, String matchName) {

		id = matchId;
		
		name = matchName;
		
	}


	/**
	 * Method that adds an user to the list of players, if not already full
	 * @param u the user to be added
	 */
	public synchronized void addPlayer(User u) {
		if(players.size() < 3) {
		players.add(u);
		if(players.size() == 3) {
			initializeScores();
		}
		}else {/*TODO Gestisci l'errore*/}
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
	public void setId(Integer id) {
		this.id = id;
	}
	
	
	/**
	 * Gettter
	 * @return
	 */
	public String getName() {
		return name;
	}

	
	/**
	 * Setter
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Getter
	 * @return
	 */
	public User getCurrentPlayingUser() {
		return currentPlayingUser;
	}

	/**
	 * Setter
	 * @param currentTurn
	 */
	public void setCurrentPlayingUser(User user) {
		this.currentPlayingUser = user;
	}

	/**
	 * Returns the next player in the list, or the first one if we are at the end of it
	 * @return
	 */
	public int getNextPlayer() {
		if(nextPlayer >= players.size() - 1) {
			nextPlayer = 0;
		} else {
			nextPlayer++;
		}
		
		return nextPlayer;
	}

	/**
	 * Getter
	 */
	public ArrayList<User> getSpectators() {
		return spectators;
	}
	
	
	/**
	 * Setter
	 * @param spectators
	 */
	public void setSpectators(ArrayList<User> spectators) {
		this.spectators = spectators;
	}
	
	/**
	 * Adds a spectator to the list
	 * @param spectator
	 */
	public void addSpectator(User spectator) {
		spectators.add(spectator);
	}

	/**
	 * 
	 * @return
	 */
	public ArrayList<Integer> getPlayersId() {  //ritorna gli id dei player
		ArrayList<Integer> IDs = new ArrayList<Integer>();
		for (User user : players) {
			IDs.add(user.getId());
		}
		return IDs;
	}

	/**
	 * 
	 * @return
	 */
	public HashMap<Integer, Sentence> getSentences() {
		return sentences;
	}

	/**
	 * 
	 * @param sentences
	 */
	public void setSentences(ArrayList<Sentence> sentences) {
		
		for (int i = 0; i < 5; i++) {
			Sentence s =sentences.get(i);
			Integer manche = i+1;
			this.sentences.put(manche, s);
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public Integer getManche() {
		return manche;
	}

	/**
	 * 
	 * @param manche
	 */
	public void setManche(Integer manche) {
		this.manche = manche;
	}

	/**
	 * @return the totScores
	 */
	public HashMap<Integer, Integer> getTotScores() {
		return totScores;
	}

	/**
	 * @param totScores the totScores to set
	 */
	public void setTotScores(HashMap<Integer, Integer> totScores) {
		this.totScores = totScores;
	}


	private void initializeScores() {
		
		for (User key : players) {
			totScores.put(key.getId(), 0);
		}
		
	}


	public void setScore(Integer playerId, Integer amount) {

		totScores.put(playerId, amount);
		
	}

}
