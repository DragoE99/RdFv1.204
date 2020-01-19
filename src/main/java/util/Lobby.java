package util;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Lobby is used to keep track of the active and "waiting" games, the server
 * possesses a collection of lobbies
 * 
 * @author gruppo aelv
 *
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
	 * 
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
	 * 
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
	
	protected Lobby() {
		this.lobbyName = null;
		this.nPlayers = null;
		this.nSpectators = null;
		this.isActive = null;
		this.creator = null;
	}

	/**
	 * 
	 * @return match
	 */
	public Match getMatch() {
		return match;
	}

	/**
	 * 
	 * @param match
	 */
	public void setMatch(Match match) {
		this.match = match;
	}

	/**
	 * 
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
	 * 
	 * @return
	 */
	public Integer getNPlayers() {
		return nPlayers;
	}

	/**
	 * 
	 * @param nPlayers
	 */
	public void setNPlayers(int nPlayers) {
		this.nPlayers = nPlayers;
	}

	/**
	 * 
	 * @return
	 */
	public Integer getNSpectators() {
		return nSpectators;
	}
	
	public void setNSpectators(int nSpecs) {
		this.nSpectators = nSpecs;
	}

//	/**
//	 * 
//	 * @param nSpectators
//	 */
//	public void setnSpectators(int nSpectators) {
//		this.nSpectators = nSpectators;
//	}

	/**
	 * 
	 * @return
	 */
	public Boolean isActive() {
		return isActive;
	}

//	/**
//	 * 
//	 * @param status
//	 */
//	public void setStatus(boolean status) {
//		this.status = status;
//	}
	
	/**
	 * 
	 * @return
	 */
	public User getCreator() {
		return this.creator;
	}
	
	/**
	 * 
	 * @param serverThread
	 */
	public void addThread(Integer id) {

		threads.add(id); 
		
		
	}

	public ArrayList<Integer> getThreads() {

		return threads;
	}
}
