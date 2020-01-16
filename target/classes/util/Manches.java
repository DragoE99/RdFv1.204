package util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Manches implements Serializable {
    Integer id;
    Sentence sentence;
    List<Integer> seenBy;
    Integer[] playerWallet = {0,0,0}; //3 wallet at the end of manche
    ArrayList<Actions> actions;
    Integer playerTurn; //possible value 1 2 3
    private ArrayList<User> players;

    public Manches() {
    }

    public Manches(Sentence sentence, List<Integer> seenBy) {
        this.sentence = sentence;
        this.seenBy = seenBy;
    }

    public Manches(Integer id, Sentence sentence, List<Integer> seenBy, Integer[] playerWallet, ArrayList<Actions> actions) {
        this.id = id;
        this.sentence = sentence;
        this.seenBy = seenBy;
        this.playerWallet = playerWallet;
        this.actions = actions;
    }

    public Manches(Integer id, Sentence sentence) {
        this.id = id;
        this.sentence = sentence;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Sentence getSentence() {
        return sentence;
    }

    public void setSentence(Sentence sentence) {
        this.sentence = sentence;
    }

    public List<Integer> getSeenBy() {
        return seenBy;
    }

    public void setSeenBy(List<Integer> seenBy) {
        this.seenBy = seenBy;
    }

    public Integer[] getPlayerWallet() {
        return playerWallet;
    }

    public void setPlayerWallet(Integer[] playerWallet) {
        this.playerWallet = playerWallet;
    }

    public ArrayList<Actions> getActions() {
        return actions;
    }

    public void setActions(ArrayList<Actions> actions) {
        this.actions = actions;
    }

    public ArrayList<User> getPlayers() {
        return players;
    }

    public void setPlayers(User players) {
        this.players.add(players);
    }

    public Integer getPlayerTurn() {
        return playerTurn;
    }

    public void setPlayerTurn(Integer playerTurn) {
        this.playerTurn = playerTurn;
    }
}
