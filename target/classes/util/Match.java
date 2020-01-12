package util;

import serverRdF.RemoteGameObserverInterface;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Match is our game state class, that will be passed back and forth and updated between client and server 
 * 
 *
 *
 */

@SuppressWarnings("serial")
public class Match extends Observable implements Serializable {

	private int idMatch;
	private ArrayList<User> players;	//3
	private String matchName;
	private String state;
	//created after match started
	private ArrayList<Manches> manches;//5
	private ArrayList<Sentence> mancheSentences;

	public Match(ArrayList<User> players, String matchName) {
		this.players = players;
		this.matchName = matchName;
	}

	public Match(int idMatch, ArrayList<User> players, String matchName, String state) {
		this.idMatch = idMatch;
		this.players = players;
		this.matchName = matchName;
		this.state = state;
	}

    public Match() {

    }
	private class WrappedObserver implements Observer, Serializable {

		private static final long serialVersionUID = 1L;

		private RemoteGameObserverInterface ro = null;

		public WrappedObserver(RemoteGameObserverInterface ro) {
			this.ro = ro;
		}

		@Override
		public void update(Observable o, Object arg) {
			try {
				ro.update(o, arg);
			} catch (RemoteException e) {
				System.out
						.println("Remote exception removing observer:" + this);
				o.deleteObserver(this);
			}
		}

	}
	public void addObserver(RemoteGameObserverInterface o) {
		WrappedObserver mo = new WrappedObserver(o);
		addObserver(mo);
		System.out.println("Added observer:" + mo);
	}

    public int getIdMatch() {
		return idMatch;
	}

	public void setIdMatch(int idMatch) {
		this.idMatch = idMatch;
	}

	public ArrayList<User> getPlayers() {
		return players;
	}

	public void addManchePlayer(User player) {
		this.players.add(player);
	}
	public void removePlayer(User player){ this.players.remove(player);}

	public String getMatchName() {
		return matchName;
	}

	public void setMatch(Match match){

		this.players = match.getPlayers();
		this.matchName = match.getMatchName();
		this.state = match.getState();
		this.manches = match.getManches();
		this.mancheSentences = match.getMancheSentences();
		setChanged();
		notifyObservers();
	}

	public void setMatchName(String matchName) {
		this.matchName = matchName;
	}

	public String getState() {
		return state;
	}

	public ArrayList<Manches> getManches() {
		return manches;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCurrentSentence(){
		return manches.get(manches.size()-1).sentence.getSentence();
	}

	public Integer getPlayerTurn(){
		return manches.get(manches.size()-1).getPlayerTurn();
	}
	public void setNextPlayerTurn(){
		manches.get(manches.size()-1).setPlayerTurn((getPlayerTurn()+1)%3);
	}

	public ArrayList<Sentence> getMancheSentences() {
		return mancheSentences;
	}

	public Manches getCurrentManche(){
		return manches.get(manches.size()-1);
	}


	public void setMancheSentences(ArrayList<Sentence> mancheSentences) {
		this.mancheSentences = mancheSentences;
	}
	//private Integer[][] mancheScore; //5 , 3
	//private int currentTurn;
	//private Sentence sentence;
	//private Integer[] totScores; 	//3

	/**
	 * Constructor
	 */
}
