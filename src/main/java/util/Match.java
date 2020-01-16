package util;

import serverRdF.DataBaseConnection;
import serverRdF.RemoteGameObserverInterface;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.*;

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
	private ArrayList<Manches> manches=new ArrayList<>();//5
	private ArrayList<Sentence> mancheSentences =new ArrayList<>();
	private HashMap<RemoteGameObserverInterface, WrappedObserver> observeMap = new HashMap<>();

	public Match(ArrayList<User> players, String matchName) {
		this.players = players;
		this.matchName = matchName;
		this.state = StringManager.getString("match_state_created_convention");
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
				System.out.println("Remote exception removing observer:" + this);
				o.deleteObserver(this);
			}
		}

	}
	public void notifyObserver(){
		setChanged();
		notifyObservers();
	}
	public void addObserver(RemoteGameObserverInterface o) {
		WrappedObserver mo = new WrappedObserver(o);
		addObserver(mo);
		System.out.println("Added observer:" + mo);
		observeMap.put(o,mo);
		setChanged();
		notifyObservers();
	}

	public void removeObserver(RemoteGameObserverInterface o){

		System.out.println("removed observer:" + observeMap.get(o));
		System.out.println("observer Rimasti:" + countObservers());
		deleteObserver(observeMap.get(o));
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
	public ArrayList<Integer> getIdsPlayer(){
		ArrayList<Integer> ids=new ArrayList<>();
		for (User u: players) {
			ids.add(u.getId());
		}
		return ids;
	}

	public synchronized void addPlayer(User player) {
		this.players.add(player);
		setChanged();
		notifyObservers();
	}
	public void removePlayer(User player){
		User toRemove = null;
		for (User u: players
			 ) {
			if(u.getId()==player.getId())toRemove=u;
		}
		if (toRemove!=null)
		players.remove(toRemove);
	}

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

	public void startMatch(ArrayList<Sentence> sentences, ArrayList<User> observer){
		List<Integer> observerIds = new ArrayList<>();
		if(observerIds!=null&&observer!=null)
		{for (User u: observer) {
			observerIds.add(u.getId());
		}}
		this.manches.add(new Manches(sentences.get(0),observerIds));
		setState(StringManager.getString("match_state_running_convention")); //set state running
		setMancheSentences(sentences);
		//create manche from DB

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
		setChanged();
		notifyObservers();
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

	public void addManche(Manches manche){
		if(this.manches==null)this.manches=new ArrayList<>();
		this.manches.add(manche);
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
