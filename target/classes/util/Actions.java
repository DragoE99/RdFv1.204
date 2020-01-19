package util;

import java.io.Serializable;

public class Actions implements Serializable {
    private Integer id;
    private int moveNumber;
    private String actionName;
    private String letter;
    private boolean jolly;
    private int actionWallet;
    private int currentPlayerTurn;// basta il turno

    public Actions() {
    }

    public Actions(String actionName, boolean jolly, int actionWallet, int currentPlayerTurn) {
        this.actionName = actionName;
        this.jolly = jolly;
        this.actionWallet = actionWallet;
        this.currentPlayerTurn = currentPlayerTurn;
    }

    public Actions(Integer id, int moveNumber, String actionName, boolean jolly, int actionWallet, int currentPlayerTurn) {
        this.id = id;
        this.moveNumber = moveNumber;
        this.actionName = actionName;
        this.jolly = jolly;
        this.actionWallet = actionWallet;
        this.currentPlayerTurn = currentPlayerTurn;
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


    public int getCurrentPlayerTurn() {
        return currentPlayerTurn;
    }

    public void setCurrentPlayerTurn(int currentPlayerTurn) {
        this.currentPlayerTurn = currentPlayerTurn;
    }
}
