package util;

import java.io.Serializable;

/**
 * Match is our game state class, that will be passed back and forth and updated between client and server 
 * 
 *
 *
 */

@SuppressWarnings("serial")
public class Match implements Serializable {

	private int idMatch;
	private Integer[] id_players;	//3

	private String matchName;
	private String state;

	public Match(Integer[] id_players, String matchName) {
		this.id_players = id_players;
		this.matchName = matchName;
	}

	public Match(int idMatch, Integer[] id_players, String matchName, String state) {
		this.idMatch = idMatch;
		this.id_players = id_players;
		this.matchName = matchName;
		this.state = state;
	}

    public Match() {

    }

    public int getIdMatch() {
		return idMatch;
	}

	public void setIdMatch(int idMatch) {
		this.idMatch = idMatch;
	}

	public Integer[] getId_players() {
		return id_players;
	}

	public void setId_players(Integer[] id_players) {
		this.id_players = id_players;
	}

	public String getMatchName() {
		return matchName;
	}

	public void setMancheName(String matchName) {
		this.matchName = matchName;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
//private Integer[][] mancheScore; //5 , 3
	//private int currentTurn;
	//private Sentence sentence;
	//private Integer[] totScores; 	//3



	/**
	 * Constructor
	 */



}
