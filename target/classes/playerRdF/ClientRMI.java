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
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;

public class ClientRMI extends UnicastRemoteObject implements RemoteGameObserverInterface {
    private Registry registry;
    private ServerInterface stub;
    private Match match;
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
        getMatchFromHash(match.getMatchName());
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

    //TODO METODO DA ELIMINARE
    public boolean matchNameCheck(String name) {
        try {
            return stub.matchNameCheck(name);

        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean createMatch(Match match) {
        try {
            match = stub.createMatch(match);
            if (match != null) {
                stub.addObserver(match, this);
                return true;
            } else return false;

        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Match updateMatch(Match match) {
        try {
            match = stub.updateMatch(match);
            return match;
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void updateActiveMatch(Match match) {
        try {
            this.match.setMatch(match);
            stub.updateActiveMatch(match);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void addObserver(Match match) {
        try {
            stub.addObserver(match, this);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public Match getMatchFromHash(String matchName) {
        try {
            this.match = stub.getMatchFromHash(matchName);
            return this.match;
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
            match = stub.addPlayer(match);
            if (match != null) {
                stub.addObserver(match, this);
                return true;
            } else return false;
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }

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
