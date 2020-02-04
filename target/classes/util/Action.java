package util;

/**
 * 
 * @author Achille Lambrughi
 * @author Emanuele Drago
 * @author Lorenzo Ottaviani
 * @author Elisabeth Veronika Venturino
 * Class that the action made by user. Helpful to inform the DB on what is happening in the game
 */
public class Action {

	private Integer id;
	
	private Integer turn;
	
	private Integer mancheNumber;
	
	private Integer matchId;
	
	private Integer playerId;
	
	private String actionName;
	
	private Boolean jolly;
	
	private Integer actionWallet;
	
	private Integer playerNumber;
	
	private String letterCalled;
	
	
	public Action(Integer turn, Integer playerId, String actionName, boolean jolly, Integer actionWallet, Integer playerNumber,
            		String letterCalled, Integer matchId, Integer mancheNumber) {
		
		setTurn(turn);
		setPlayerId(playerId);
		setActionName(actionName);
		setJolly(jolly);
		setActionWallet(actionWallet);
		setPlayerNumber(playerNumber);
		setLetterCalled(letterCalled);
		setMatchId(matchId);
		setMancheNumber(mancheNumber);
		
	}

	/**
	 * 
	 * @return
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * 
	 * @param id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * 
	 * @return
	 */
	public Integer getTurn() {
		return turn;
	}

	/**
	 * 
	 * @param turn
	 */
	public void setTurn(Integer turn) {
		this.turn = turn;
	}

	/**
	 * 
	 * @return
	 */
	public Integer getMancheNumber() {
		return mancheNumber;
	}

	/**
	 * 
	 * @param mancheNumber
	 */
	public void setMancheNumber(Integer mancheNumber) {
		this.mancheNumber = mancheNumber;
	}

	/**
	 * 
	 * @return
	 */
	public Integer getMatchId() {
		return matchId;
	}

	/**
	 * Setter
	 * @param matchId
	 */
	public void setMatchId(Integer matchId) {
		this.matchId = matchId;
	}


	/**
	 * Getter
	 * @return
	 */
	public Integer getPlayerId() {
		return playerId;
	}


	/**
	 * Setter
	 * @param playerId
	 */
	public void setPlayerId(Integer playerId) {
		this.playerId = playerId;
	}


	/**
	 * Getter
	 * @return
	 */
	public String getActionName() {
		return actionName;
	}

	/**
	 * Setter
	 * @param actionName
	 */
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	/**
	 * Getter
	 * @return
	 */
	public Boolean getJolly() {
		return jolly;
	}

	/**
	 * Setter
	 * @param jolly
	 */
	public void setJolly(Boolean jolly) {
		this.jolly = jolly;
	}

	/**
	 * Getter
	 * @return
	 */
	public Integer getActionWallet() {
		return actionWallet;
	}

	/**
	 * Setter
	 * @param actionWallet
	 */
	public void setActionWallet(Integer actionWallet) {
		this.actionWallet = actionWallet;
	}

	/**
	 * Getter
	 * @return
	 */
	public Integer getPlayerNumber() {
		return playerNumber;
	}

	/**
	 * Setter
	 * @param playerNumber
	 */
	public void setPlayerNumber(Integer playerNumber) {
		this.playerNumber = playerNumber;
	}

	/**
	 * Getter
	 * @return
	 */
	public String getLetterCalled() {
		return letterCalled;
	}

	/**
	 * Setter
	 * @param letterCalled
	 */
	public void setLetterCalled(String letterCalled) {
		this.letterCalled = letterCalled;
	}
	
}
