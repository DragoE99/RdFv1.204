package util;

public class Actions {
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
}
