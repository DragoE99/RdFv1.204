package util;

public class Actions {
    Integer id;
    int moveNumber;
    String actionName;
    String letter;
    boolean jolly;
    int actionWallet;
    int playerId;// basta il turno

    public Actions() {
    }

    public Actions(String actionName, boolean jolly, int actionWallet, int playerId) {
        this.actionName = actionName;
        this.jolly = jolly;
        this.actionWallet = actionWallet;
        this.playerId = playerId;
    }

    public Actions(Integer id, int moveNumber, String actionName, boolean jolly, int actionWallet, int playerId) {
        this.id = id;
        this.moveNumber = moveNumber;
        this.actionName = actionName;
        this.jolly = jolly;
        this.actionWallet = actionWallet;
        this.playerId = playerId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getMoveNumber() {
        return moveNumber;
    }

    public void setMoveNumber(int moveNumber) {
        this.moveNumber = moveNumber;
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


    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }
}
