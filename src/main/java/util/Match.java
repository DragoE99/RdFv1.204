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

	private int id ;
	private Integer[] id_players;	//3
	private Integer[] totScores; 	//3
	private String name;
	private Integer[][] mancheScore; //5 , 3
	private int currentTurn;
	private Manche[] manches;//5


	public Match(int id, Integer[] id_players, String name, Integer[][] mancheScore, int currentTurn, Sentence sentence) {
		this.id = id;
		this.id_players = id_players;
		this.name = name;
		this.mancheScore = mancheScore;
		this.currentTurn = currentTurn;
		this.sentence = sentence;
	}

	public Integer[] getTotScores() {
		return totScores;
	}

	public void setTotScores(Integer[] totScores) {
		this.totScores = totScores;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer[][] getMancheScore() {
		return mancheScore;
	}

	public void setMancheScore(Integer[][] mancheScore) {
		this.mancheScore = mancheScore;
	}

	public int getCurrentTurn() {
		return currentTurn;
	}

	public void setCurrentTurn(int currentTurn) {
		this.currentTurn = currentTurn;
	}

	public Sentence getSentence() {
		return sentence;
	}

	public void setSentence(Sentence sentence) {
		this.sentence = sentence;
	}

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
	public int getId() {
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
	public void setId(int id) {
		this.id = id;
	}


}
