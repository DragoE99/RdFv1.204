package util;

import java.io.Serializable;

/**
 * Match is our game state class, that will be passed back and forth and updated between client and server 
 * 
 * @author gruppo aelv
 *
 */

@SuppressWarnings("serial")
public class Match implements Serializable {

	private String id = null;
	private Integer[] id_players;	//3
	private Integer[] totScores; 	//3
	private int manche;
	private Integer[][] mancheScore; //5 , 3
	private int currentTurn;
	private Sentence sentence;



	/**
	 * Constructor
	 */
	public Match() {

	}

	/**
	 * Getter
	 * @return
	 */
	public Integer[] getId_players() {
		return id_players;
	}

	/**
	 * Getter
	 * @return
	 */
	public String getId() {
		return id;
	}

	/**
	 * Setter
	 * @return
	 */
	public void setId_players(Integer[] id_players) {
		this.id_players = id_players;
	}

	/**
	 * Setter
	 * @return
	 */
	public void setId(String id) {
		this.id = id;
	}


}
