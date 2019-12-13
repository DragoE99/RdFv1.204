package util;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import serverRdF.ServerThread;

/**
 * Lobby is used to keep track of the active and "waiting" games, the server
 * possesses a collection of lobbies
 * 
 * @author gruppo aelv
 *
 */

@SuppressWarnings("serial")
public class Lobby implements Serializable {

	private Set<ServerThread> threadList = new HashSet<ServerThread>();
	private Match match;
	private final String lobbyName;
	private final Integer nPlayers;
	private final Integer nSpectators;
	private final Boolean status;

	public Lobby(String lobbyName, int nPlayers, int nSpectators, boolean status) {
		this.lobbyName = lobbyName;
		this.nPlayers = nPlayers;
		this.nSpectators = nSpectators;
		this.status = status;

	}

	public Lobby() {
		this.lobbyName = null;
		this.nPlayers = null;
		this.nSpectators = null;
		this.status = null;
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

//	/**
//	 * 
//	 * @param nPlayers
//	 */
//	public void setnPlayers(int nPlayers) {
//		this.nPlayers = nPlayers;
//	}

	/**
	 * 
	 * @return
	 */
	public Integer getNSpectators() {
		return nSpectators;
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
	public Boolean getStatus() {
		return status;
	}

//	/**
//	 * 
//	 * @param status
//	 */
//	public void setStatus(boolean status) {
//		this.status = status;
//	}
}
