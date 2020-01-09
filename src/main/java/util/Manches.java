package util;

import java.util.ArrayList;
import java.util.List;

public class Manches {
    Integer id;
    Sentence sentence;
    List<Integer> seenBy;
    Integer[] playerWallet = {0,0,0}; //3 wallet at the end of manche
    ArrayList<Actions> actions;

    public Manches() {
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

    public int getActionTurn(){
        return actions.get(actions.size()-1).getTurn();
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
}
