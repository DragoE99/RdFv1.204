package util;

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
	
	private Character letterCalled;
	
	
	public Action() {}

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
	 * 
	 * @param matchId
	 */
	public void setMatchId(Integer matchId) {
		this.matchId = matchId;
	}


	public Integer getPlayerId() {
		return playerId;
	}


	public void setPlayerId(Integer playerId) {
		this.playerId = playerId;
	}


	public String getActionName() {
		return actionName;
	}


	public void setActionName(String actionName) {
		this.actionName = actionName;
	}


	public Boolean getJolly() {
		return jolly;
	}


	public void setJolly(Boolean jolly) {
		this.jolly = jolly;
	}


	public Integer getActionWallet() {
		return actionWallet;
	}


	public void setActionWallet(Integer actionWallet) {
		this.actionWallet = actionWallet;
	}


	public Integer getPlayerNumber() {
		return playerNumber;
	}


	public void setPlayerNumber(Integer playerNumber) {
		this.playerNumber = playerNumber;
	}


	public Character getLetterCalled() {
		return letterCalled;
	}


	public void setLetterCalled(Character letterCalled) {
		this.letterCalled = letterCalled;
	}
	
}
