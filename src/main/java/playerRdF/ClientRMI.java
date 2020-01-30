package playerRdF;

import adminRDF.GameController;
import gui.Main;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import serverRdF.RemoteGameObserverInterface;
import serverRdF.ServerInterface;
import util.Match;
import util.User;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;

public class ClientRMI extends UnicastRemoteObject implements RemoteGameObserverInterface , Serializable {
    private Registry registry;
    private ServerInterface stub;
    private static Match match= new Match();
    private HashMap<String, Match> activeMatch;
    private static final long serialVersionUID = 1L;
    User user;

    private static ClientRMI instance = null;

    public static ClientRMI getInstance() {
        if (instance == null) {
            try {
                instance = new ClientRMI();
            } catch (RemoteException e) {
                e.printStackTrace();
                return null;
            }
            System.out.println("nuova istanza creata");
        }
        return instance;

    }

    private ClientRMI() throws RemoteException {
        super();
        try {
            registry = LocateRegistry.getRegistry(3333);
            stub = (ServerInterface) registry.lookup("ruota");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public User getUser() {
        return user;
    }

    @Override
    public void update(Object observable, Object updateMsg) throws RemoteException {
        if(ClientRMI.match!=null){
            System.out.println("match NOT null ClientRMI OVRR Update");
        getMatchFromHash(match.getMatchName());
        //match.notifyObserver();
        }
        System.out.println("updated object");

    }

    public boolean loginCheck(String email, String password) {
        try {
            if( stub.logInCheck(email, password)){
                getOneUser(email,password);
                return true;
            }else return false;
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }
    public void getOneUser(String email, String password){
        try {
            this.user = stub.getOneUser(email, password);
            //if(user!=null)return user;
           // else return null;
        } catch (RemoteException e) {
            e.printStackTrace();
            //return null;
        }
    }

    public boolean createMatch(Match match) {
        try {

            ClientRMI.match = stub.createMatch(match);
            if (ClientRMI.match != null) {
                stub.addObserver(match, this, user);
                return true;
            } else return false;

        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Match updateMatch(Match match) {
        try {
            stub.updateMatch(match);
            return ClientRMI.match;
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void updateActiveMatch(Match match) {
        try {
            ClientRMI.match.setMatch(match);
            stub.updateActiveMatch(match);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void addObserver(Match match) {
        try {
            ClientRMI.match.setMatch(match);
            stub.addObserver(match, this, user);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public Match getMatchFromHash(String matchName) {
        try {
            Match temp = stub.getMatchFromHash(matchName);
            if(temp!=null){
                match.setMatch(temp);
                return temp;}
            else return null;
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Match getMatch() {
        return this.match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public boolean addPlayer(Match match) {
        try {
            match = stub.addPlayer(match,user);
            if (match != null) {
                stub.addObserver(match, this, user);
                ClientRMI.match.setMatch(match);
                return true;
            } else return false;
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }

    }
    public void removeObserver(){
        try {
            stub.removeObserver(match.getMatchName(),this);
        } catch (RemoteException e) {
            e.printStackTrace();

        }
    }
    public void removePlayer(){
        try {
            stub.removePlayer(user,match.getMatchName(),this);
        } catch (RemoteException e) {
            e.printStackTrace();

        }
    }

    public HashMap<String, Match> getLocalActiveMatch(){
        return activeMatch;
    }
    public HashMap<String, Match> getActiveMatch() {
        try {
            this.activeMatch = stub.getActiveMatch();
            return activeMatch;

        } catch (RemoteException e) {
            e.printStackTrace();

        }
        return activeMatch;

    }

}
