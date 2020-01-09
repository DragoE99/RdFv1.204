package util;

public class Actions {
    Integer id;
    int turn;
    String actionName;
    String letter;
    boolean jolly;
    int actionWallet;
    int playerNumber; // indica se e' il g1 g2 o g3
    int playerId;// basta il turno

    public Actions() {
    }

    public Actions(String actionName, boolean jolly, int actionWallet, int playerNumber, int playerId) {
        this.actionName = actionName;
        this.jolly = jolly;
        this.actionWallet = actionWallet;
        this.playerNumber = playerNumber;
        this.playerId = playerId;
    }

    public Actions(Integer id, int turn, String actionName, boolean jolly, int actionWallet, int playerNumber, int playerId) {
        this.id = id;
        this.turn = turn;
        this.actionName = actionName;
        this.jolly = jolly;
        this.actionWallet = actionWallet;
        this.playerNumber = playerNumber;
        this.playerId = playerId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public boolean isJolly() {
        return jolly;
    }

    public void setJolly(boolean jolly) {
        this.jolly = jolly;
    }

    public int getActionWallet() {
        return actionWallet;
    }

    public void setActionWallet(int actionWallet) {
        this.actionWallet = actionWallet;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }
}
