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

public class GameServer extends Thread implements GameInterface {
    GameServer obj;
    GameInterface stub;
    Registry registry;
    private Match match;
    private Manches manches;
    private ArrayList<Actions> actions;
    private int turn;

    public GameServer(Match match) {
        this.match = match;
        //start();
    }

    public void run() {
        System.out.println(" stampa di prova");
        //System.setProperty("java.rmi.server.hostname","18.5.28.53");
        try {

            obj = new GameServer(this.match);
            stub = (GameInterface) UnicastRemoteObject.exportObject( obj, 2);
            registry = LocateRegistry.createRegistry(4444);
            registry.rebind(match.getMatchName(), stub);

            System.out.println(" ** Server pronto");
        } catch (Exception e) {
            System.err.println(" ***** Server Error");
            e.printStackTrace();
            System.exit(0);
        }
    }

    @Override
    public Integer[] getplayerId() throws RemoteException {
        return match.getId_players();
    }
}
