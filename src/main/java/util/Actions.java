package util;

public class Actions {
    Integer id;
    int turn;
    String actionName;
    boolean jolly;
    int actionWallet;
    int playerNumber; // indica se e' il g1 g2 o g3
    int playerId;

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
}
