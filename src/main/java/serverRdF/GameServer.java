package serverRdF;

import playerRdF.GameRmi;
import util.Actions;
import util.Manches;
import util.Match;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class GameServer {
    private Match match;
    private Manches manches;
    private ArrayList<Actions> actions;
    private int turn;

    public GameServer(Match match) {
        this.match = match;
        //start();
    }

    public void run() {

    }
}
