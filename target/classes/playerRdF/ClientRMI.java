package playerRdF;

import gui.Main;
import javafx.application.Application;
import serverRdF.ServerInterface;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClientRMI {
    private Registry registry;
    private ServerInterface stub;

    public ClientRMI() {

        try {
            Registry registry = LocateRegistry.getRegistry(3333);
            stub = (ServerInterface) registry.lookup("ruota");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public int modifyName(String newUserName) {

        int x=0;
        try {
            x = stub.modifyName("pippo");
            System.out.println("ottimo ritornato "+x );
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return x;
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
}
