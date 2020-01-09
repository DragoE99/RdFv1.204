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
	private Integer[] id_players;	//3
	private String matchName;
	private String state;
	private ArrayList<Manches> manches;//5

	public Match(Integer[] id_players, String matchName) {
		this.id_players = id_players;
		this.matchName = matchName;
	}

	public Match(int idMatch, Integer[] id_players, String matchName, String state) {
		this.idMatch = idMatch;
		this.id_players = id_players;
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

	public Integer[] getId_players() {
		return id_players;
	}

	public void setId_players(Integer[] id_players) {
		this.id_players = id_players;
	}

	public String getMatchName() {
		return matchName;
	}

	public void setMancheName(String matchName) {
		this.matchName = matchName;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCurrentSentence(){
		return manches.get(manches.size()-1).sentence.getSentence();
	}

	public int getTurn(){
		return manches.get(manches.size()-1).getActionTurn();
	}
//private Integer[][] mancheScore; //5 , 3
	//private int currentTurn;
	//private Sentence sentence;
	//private Integer[] totScores; 	//3



	/**
	 * Constructor
	 */



}
