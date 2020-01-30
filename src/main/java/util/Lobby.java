package util;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Lobby is used to keep track of the active and "waiting" games, the server
 * possesses a collection of lobbies
 * 
 * @author Achille Lambrughi
 * @author Emanuele Drago
 * @author Lorenzo Ottaviani
 * @author Elisabeth Veronika Venturino
 */
@SuppressWarnings("serial")
public class Lobby implements Serializable {

	private ArrayList<Integer> threads = new ArrayList<Integer>();
	private Match match;
	private final String lobbyName;
	private Integer nPlayers;
	private Integer nSpectators;
	private Boolean isActive;
	private final User creator;
	
	/**
	 * Constructor
	 * @param lobbyName
	 * @param nPlayers
	 * @param nSpectators
	 * @param isActive
	 * @param creator
	 */
	public Lobby(String lobbyName, int nPlayers, int nSpectators, boolean isActive, User creator, Match match) {
		this.lobbyName = lobbyName;
		this.nPlayers = nPlayers;
		this.nSpectators = nSpectators;
		this.isActive = isActive;
		this.creator = creator;
		this.match = match;

	}

	/**
	 * Constructor
	 * @param lobbyName
	 * @param isActive
	 * @param creator
	 */
	public Lobby(String lobbyName, boolean isActive, User creator) {
		this.lobbyName = lobbyName;
		this.nPlayers = null;
		this.nSpectators = null;
		this.isActive = isActive;
		this.creator = creator;
	}
	
	/**
	 * default constructor
	 */
	protected Lobby() {
		this.lobbyName = null;
		this.nPlayers = null;
		this.nSpectators = null;
		this.isActive = null;
		this.creator = null;
	}

	/**
	 * Getter for the Match field
	 * @return match
	 */
	public Match getMatch() {
		return match;
	}

	/**
	 * Setter for the Match field
	 * @param match
	 */
	public void setMatch(Match match) {
		this.match = match;
	}

	/**
	 * Getter
	 * @return
	 */
	public String getLobbyName() {
		return lobbyName;
	}

	/*	*//**
			 * 
			 * @param lobbyName
			 *//*
				 * public void setLobbyName(String lobbyName) { this.lobbyName = lobbyName; }
				 */

	/**
	 * Getter
	 * @return
	 */
	public Integer getNPlayers() {
		return nPlayers;
	}

	/**
	 * Setter
	 * @param nPlayers
	 */
	public void setNPlayers(int nPlayers) {
		this.nPlayers = nPlayers;
	}

	/**
	 * Getter
	 * @return
	 */
	public Integer getNSpectators() {
		return nSpectators;
	}
	
	/**
	 * Setter
	 * @param nSpecs
	 */
	public void setNSpectators(int nSpecs) {
		this.nSpectators = nSpecs;
	}
	

	/**
	 * Getter
	 * @return
	 */
	public Boolean isActive() {
		return isActive;
	}

	/**
	 * 
	 * @param status
	 */
	public void setStatus(boolean status) {
		this.isActive = status;
	}
	
	/**
	 * Getter
	 * @return
	 */
	public User getCreator() {
		return this.creator;
	}
	
	/**
	 * Adds a thread to the list of threads in this lobby, represented by their ID
	 * @param serverThread
	 */
	public void addThread(Integer id) {

		threads.add(id); 
		
		
	}

	/**
	 * Getter
	 * @return
	 */
	public ArrayList<Integer> getThreads() {

		return threads;
	}
}
