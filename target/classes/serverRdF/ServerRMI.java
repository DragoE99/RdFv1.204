package serverRdF;

import util.Match;
import util.Sentence;
import util.User;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class ServerRMI extends Thread  implements ServerInterface {

    public ServerRMI(){}
    ServerRMI obj;
    ServerInterface stub;
    Registry registry;


    public static void main(String[] args) {
        ServerRMI test = new ServerRMI();
        test.start();
    }
    @Override
    public void run() {
        System.out.println(" stampa di prova");
        //System.setProperty("java.rmi.server.hostname","18.5.28.53");
        try {

            obj = new ServerRMI();
            stub = (ServerInterface) UnicastRemoteObject.exportObject(obj, 0);
            registry = LocateRegistry.createRegistry(3333);
            registry.rebind("diocane", stub);

            System.out.println(" ** Server pronto");
        } catch (Exception e) {
            System.err.println(" ***** Server Error");
            e.printStackTrace();
            System.exit(0);
        }
    }
    public int modifyName(String newUserName) {
        DataBaseConnection DB = new DataBaseConnection();
       // return DB.modifyUser(newUserName);
        return 0;
    }

    @Override
    public int deleteUser(User user) throws RemoteException {
        return 0;
    }

    @Override
    public boolean verificationCodeCheck(String verificationCode) throws RemoteException {
        return false;
    }

    @Override
    public boolean verificationMailCheck(String mail) throws RemoteException {
        return false;
    }

    @Override
    public boolean matchNameCheck(String name) throws RemoteException {
        return false;
    }

    @Override
    public Match createMatch(Match match) throws RemoteException {
        return null;
    }

    @Override
    public Match updateMatch(Match match) throws RemoteException {
        return null;
    }

    @Override
    public Match addPlayer(Match match) throws RemoteException {
        return null;
    }

    @Override
    public HashMap<String, Match> getActiveMatch() throws RemoteException {
        return null;
    }


    @Override
    public void updateActiveMatch(Match match) throws RemoteException {

    }

    @Override
    public Match getMatchFromHash(String matchName) throws RemoteException {
        return null;
    }

    @Override
    public void addObserver(Match match, RemoteGameObserverInterface o) throws RemoteException {

    }

    @Override
    public boolean logInCheck(String mail, String password) throws RemoteException {
        return false;
    }

    @Override
    public boolean checkMailExistence(String mail) throws RemoteException {
        return false;
    }

    @Override
    public boolean checkAdminExistence() throws RemoteException {
        return false;
    }

    @Override
    public void insertSentences(List<Sentence> sentences, User user) throws RemoteException {

    }

    @Override
    public int insertUser(User newUser) throws RemoteException {
        return 0;
    }

    @Override
    public int modifyUser(User newUser) throws RemoteException {
        return 0;
    }

    @Override
    public User getOneUser(String email, String password) throws RemoteException {
        return null;
    }
}
