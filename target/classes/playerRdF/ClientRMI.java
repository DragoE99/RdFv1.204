package playerRdF;

import gui.Main;
import javafx.application.Application;
import serverRdF.RemoteGameObserverInterface;
import serverRdF.ServerInterface;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ClientRMI extends UnicastRemoteObject implements RemoteGameObserverInterface {
    private Registry registry;
    private ServerInterface stub;

    public ClientRMI() throws RemoteException {
        super();
        try {
            Registry registry = LocateRegistry.getRegistry(3333);
            stub = (ServerInterface) registry.lookup("ruota");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    @Override
    public void update(Object observable, Object updateMsg) throws RemoteException {

    }

    public boolean loginCheck(String email, String password){
        try {
            stub.logInCheck(email, password);
            return true;
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }
    public boolean matchNameCheck(String name){
        try {
            stub.matchNameCheck(name);
            return true;
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }


}
